package com.d2c.shop.config.security.context;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.SpringUtil;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

    public ShopkeeperDO getLoginKeeper() {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) throw new ApiException(ResultCode.LOGIN_EXPIRED);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String account = claims.getSubject();
            ShopkeeperDO keeper = SpringUtil.getBean(ShopkeeperService.class).findByAccount(account);
            Asserts.notNull(ResultCode.LOGIN_EXPIRED, keeper);
            Asserts.eq(DigestUtil.md5Hex(accessToken), keeper.getAccessToken(), ResultCode.LOGIN_EXPIRED);
            return keeper;
        } catch (JwtException e) {
            throw new ApiException(ResultCode.LOGIN_EXPIRED);
        }
    }

    public Long getLoginId() {
        return this.getLoginKeeper().getId();
    }

    public String getLoginAccount() {
        return this.getLoginKeeper().getAccount();
    }

}
