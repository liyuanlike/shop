package com.d2c.shop.c_api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Cai
 */
@Api(description = "会员业务")
@RestController
@RequestMapping("/c_api/member")
public class C_MemberController extends C_BaseController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "登录信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R<MemberDO> info() {
        return Response.restResult(loginMemberHolder.getLoginMember(), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R<String> login(String account, String password) {
        Asserts.notNull("账号和密码不能为空", account, password);
        MemberDO member = memberService.findByAccount(account);
        Asserts.notNull("账号不存在或密码错误", member);
        Asserts.gt(member.getStatus(), 0, "账号被封禁，请联系管理员");
        password = new BCryptPasswordEncoder().encode(password);
        Asserts.eq(member.getPassword(), password, "账号不存在或密码错误");
        Date accessExpired = DateUtil.offsetDay(new Date(), 30);
        String accessToken = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(account)
                .claim(SecurityConstant.AUTHORITIES, "")
                .setExpiration(accessExpired)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        memberService.doLogin(account, getRequestIp(), accessToken, accessExpired);
        return Response.restResult(accessToken, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public R logout() {
        memberService.doLogout(loginMemberHolder.getLoginAccount());
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public R<String> register(String account, String password, String code) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        if (!Validator.isMobile(account)) {
            Response.failed(ResultCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        MemberDO member = MemberDO.builder()
                .account(account)
                .password(new BCryptPasswordEncoder().encode(password))
                .registerIp(getRequestIp())
                .status(1)
                .build();
        memberService.save(member);
        return this.login(account, password);
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public R password(String account, String password, String code) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        password = new BCryptPasswordEncoder().encode(password);
        memberService.updatePassword(account, password);
        memberService.doLogout(account);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "个人信息修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<MemberDO> update(String nickname, String avatar, String sex) {
        MemberDO member = loginMemberHolder.getLoginMember();
        MemberDO entity = MemberDO.builder()
                .nickname(nickname)
                .avatar(avatar)
                .sex(sex)
                .build();
        entity.setId(member.getId());
        memberService.updateById(entity);
        return Response.restResult(entity, ResultCode.SUCCESS);
    }

}
