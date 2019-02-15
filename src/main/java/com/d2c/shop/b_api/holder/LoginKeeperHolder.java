package com.d2c.shop.b_api.holder;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cai
 */
@Component
public class LoginKeeperHolder {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ShopkeeperService shopkeeperService;

    public ShopkeeperDO getLoginKeeper() {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) throw new ApiException(ErrorCode.LOGIN_EXPIRED);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String account = claims.getSubject();
            ShopkeeperDO keeper = shopkeeperService.findByAccount(account);
            Asserts.notNull(ErrorCode.LOGIN_EXPIRED, keeper);
            return keeper;
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrorCode.LOGIN_EXPIRED);
        }
    }

}
