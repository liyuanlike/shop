package com.d2c.shop.c_api.holder;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.service.MemberService;
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
public class LoginMemberHolder {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;

    public MemberDO getLoginMember() {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) throw new ApiException(ResultCode.LOGIN_EXPIRED);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String account = claims.getSubject();
            MemberDO member = memberService.findByAccount(account);
            Asserts.notNull(ResultCode.LOGIN_EXPIRED, member);
            Asserts.eq(DigestUtil.md5Hex(accessToken), member.getAccessToken(), ResultCode.LOGIN_EXPIRED);
            return member;
        } catch (JwtException e) {
            throw new ApiException(ResultCode.LOGIN_EXPIRED);
        }
    }

    public Long getLoginId() {
        return this.getLoginMember().getId();
    }

    public String getLoginAccount() {
        return this.getLoginMember().getAccount();
    }

}
