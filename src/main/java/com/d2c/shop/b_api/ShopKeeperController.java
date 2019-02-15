package com.d2c.shop.b_api;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
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
@Api(description = "店员业务")
@RestController
@RequestMapping("/b_api/shopkeeper")
public class ShopKeeperController extends BaseController {

    @Autowired
    private ShopkeeperService shopkeeperService;

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/clerk/register", method = RequestMethod.POST)
    public R register(String account, String password, Long shopId) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        Asserts.notNull("店铺ID不能为空", shopId);
        if (!Validator.isMobile(account)) {
            Response.failed(ErrorCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        ShopkeeperDO keeper = ShopkeeperDO.builder()
                .account(account)
                .password(new BCryptPasswordEncoder().encode(password))
                .shopId(shopId)
                .role(ShopkeeperDO.RoleEnum.CLERK.name())
                .status(0)
                .build();
        shopkeeperService.save(keeper);
        return Response.restResult(keeper, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/clerk/login", method = RequestMethod.POST)
    public R login(String account, String password) {
        Asserts.notNull("账号和密码不能为空", account, password);
        ShopkeeperDO keeper = shopkeeperService.findByAccount(account);
        Asserts.notNull("账号不存在或密码错误", keeper);
        Asserts.gt(keeper.getStatus(), 0, "账号未激活，请联系管理员");
        password = new BCryptPasswordEncoder().encode(password);
        Asserts.eq(keeper.getPassword(), password, "账号不存在或密码错误");
        String accessToken = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(account)
                .claim(SecurityConstant.AUTHORITIES, keeper.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 15 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        shopkeeperService.doLogin(account, accessToken);
        return Response.restResult(accessToken, ErrorCode.SUCCESS);
    }

}
