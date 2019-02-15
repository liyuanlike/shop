package com.d2c.shop.config.security.handler;

import cn.hutool.json.JSONUtil;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.config.security.constant.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author BaiCai
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails loginUser = (UserDetails) authentication.getPrincipal();
        String username = loginUser.getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
        List<String> list = new ArrayList<>();
        authorities.forEach(item -> list.add(item.getAuthority()));
        // JWT登录成功生成token
        String token = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstant.AUTHORITIES, JSONUtil.parse(list))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        Response.out(response, Response.restResult(token, ResultCode.SUCCESS));
    }

}
