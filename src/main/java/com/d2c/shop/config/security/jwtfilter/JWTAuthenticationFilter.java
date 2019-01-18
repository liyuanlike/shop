package com.d2c.shop.config.security.jwtfilter;

import cn.hutool.core.util.StrUtil;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.utils.SpringUtil;
import com.d2c.shop.config.security.authentication.SecurityUserDetails;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.security.model.UserDO;
import com.d2c.shop.modules.security.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BaiCai
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isNotBlank(accessToken)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(accessToken, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String accessToken, HttpServletResponse response) {
        try {
            // JWT解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String username = claims.getSubject();
            if (StrUtil.isNotBlank(username)) {
                UserDO user = SpringUtil.getBean(UserServiceImpl.class).findByUsername(username);
                SecurityUserDetails securityUserDetail = new SecurityUserDetails(user);
                User principal = new User(username, "", securityUserDetail.getAuthorities());
                return new UsernamePasswordAuthenticationToken(principal, null, securityUserDetail.getAuthorities());
            }
        } catch (ExpiredJwtException e) {
            Response.failed(ErrorCode.LOGIN_EXPIRED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Response.failed(ErrorCode.SERVER_EXCEPTION, "accessToken解析错误");
        }
        return null;
    }

}
