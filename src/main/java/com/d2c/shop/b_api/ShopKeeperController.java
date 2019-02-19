package com.d2c.shop.b_api;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.b_api.base.BaseControllerB;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.query.ShopkeeperQuery;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ShopKeeperController extends BaseControllerB {

    @Autowired
    private ShopkeeperService shopkeeperService;

    @ApiOperation(value = "登录信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info() {
        return Response.restResult(loginKeeperHolder.getLoginKeeper(), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
        return Response.restResult(accessToken, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public R logout() {
        shopkeeperService.doLogout(loginKeeperHolder.getLoginAccount());
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "店主注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public R register(String account, String password) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        if (!Validator.isMobile(account)) {
            Response.failed(ResultCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        ShopkeeperDO keeper = ShopkeeperDO.builder()
                .account(account)
                .password(new BCryptPasswordEncoder().encode(password))
                .role(ShopkeeperDO.RoleEnum.BOSS.name())
                .status(1)
                .build();
        shopkeeperService.save(keeper);
        return Response.restResult(keeper, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public R password(String account, String password) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        password = new BCryptPasswordEncoder().encode(password);
        shopkeeperService.updatePassword(account, password);
        shopkeeperService.doLogout(account);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "店员分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(PageModel page) {
        ShopkeeperQuery query = new ShopkeeperQuery();
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<ShopkeeperDO> pager = (Page<ShopkeeperDO>) shopkeeperService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R select(@PathVariable Long id) {
        ShopkeeperDO keeper = shopkeeperService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, keeper);
        Asserts.eq(keeper.getId(), loginKeeperHolder.getLoginKeeper().getShopId(), "您不是本店店员");
        return Response.restResult(keeper, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "店员新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(ShopkeeperDO keeper) {
        Asserts.notNull("账号和密码不能为空", keeper.getAccount(), keeper.getPassword());
        if (!Validator.isMobile(keeper.getAccount())) {
            Response.failed(ResultCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        ShopkeeperDO entity = ShopkeeperDO.builder()
                .account(keeper.getAccount())
                .password(new BCryptPasswordEncoder().encode(keeper.getPassword()))
                .shopId(loginKeeperHolder.getLoginKeeper().getShopId())
                .role(ShopkeeperDO.RoleEnum.CLERK.name())
                .status(keeper.getStatus())
                .remark(keeper.getRemark())
                .build();
        shopkeeperService.save(entity);
        return Response.restResult(entity, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "个人信息更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(ShopkeeperDO keeper) {
        ShopkeeperDO loginKeeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(keeper.getId(), loginKeeper.getId(), "您不是本人");
        shopkeeperService.updateById(keeper);
        return Response.restResult(keeper, ResultCode.SUCCESS);
    }

}
