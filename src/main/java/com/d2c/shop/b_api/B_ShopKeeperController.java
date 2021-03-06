package com.d2c.shop.b_api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.b_api.base.B_BaseController;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Cai
 */
@Api(description = "店员业务")
@RestController
@RequestMapping("/b_api/shopkeeper")
public class B_ShopKeeperController extends B_BaseController {

    @Autowired
    private ShopkeeperService shopkeeperService;

    @ApiOperation(value = "登录信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R<ShopkeeperDO> info() {
        return Response.restResult(loginKeeperHolder.getLoginKeeper(), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R<String> login(String account, String password) {
        Asserts.notNull("账号和密码不能为空", account, password);
        ShopkeeperDO keeper = shopkeeperService.findByAccount(account);
        Asserts.notNull("账号不存在或密码错误", keeper);
        Asserts.gt(keeper.getStatus(), 0, "账号未激活，请联系管理员");
        password = new BCryptPasswordEncoder().encode(password);
        Asserts.eq(keeper.getPassword(), password, "账号不存在或密码错误");
        Date accessExpired = DateUtil.offsetDay(new Date(), 30);
        String accessToken = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(account)
                .claim(SecurityConstant.AUTHORITIES, keeper.getRole())
                .setExpiration(accessExpired)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        shopkeeperService.doLogin(account, getRequestIp(), accessToken, accessExpired);
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
    public R<String> register(String account, String password, String code) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        if (!Validator.isMobile(account)) {
            Response.failed(ResultCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        ShopkeeperDO keeper = ShopkeeperDO.builder()
                .account(account)
                .password(new BCryptPasswordEncoder().encode(password))
                .registerIp(getRequestIp())
                .role(ShopkeeperDO.RoleEnum.BOSS.name())
                .status(1)
                .build();
        shopkeeperService.save(keeper);
        return this.login(account, password);
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public R password(String account, String password, String code) {
        // TODO 验证码
        Asserts.notNull("账号和密码不能为空", account, password);
        password = new BCryptPasswordEncoder().encode(password);
        shopkeeperService.updatePassword(account, password);
        shopkeeperService.doLogout(account);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<ShopkeeperDO>> list(PageModel page) {
        ShopkeeperQuery query = new ShopkeeperQuery();
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<ShopkeeperDO> pager = (Page<ShopkeeperDO>) shopkeeperService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<ShopkeeperDO> select(@PathVariable Long id) {
        ShopkeeperDO keeper = shopkeeperService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, keeper);
        Asserts.eq(keeper.getShopId(), loginKeeperHolder.getLoginKeeper().getShopId(), "您不是本店店员");
        return Response.restResult(keeper, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "店员新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<ShopkeeperDO> insert(@RequestBody ShopkeeperDO keeper) {
        Asserts.notNull("账号和密码不能为空", keeper.getAccount(), keeper.getPassword());
        if (!Validator.isMobile(keeper.getAccount())) {
            Response.failed(ResultCode.SERVER_EXCEPTION, "手机号不符合规则");
        }
        ShopkeeperDO entity = ShopkeeperDO.builder()
                .account(keeper.getAccount())
                .password(new BCryptPasswordEncoder().encode(keeper.getPassword()))
                .registerIp(getRequestIp())
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
    public R<ShopkeeperDO> update(Long id, String nickname, String avatar, Integer status) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        ShopkeeperDO entity = ShopkeeperDO.builder()
                .nickname(nickname)
                .avatar(avatar)
                .status(status)
                .build();
        entity.setId(keeper.getId());
        if (id != null) {
            ShopkeeperDO clerk = shopkeeperService.getById(id);
            Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, clerk);
            Asserts.eq(keeper.getShopId(), clerk.getShopId(), "您不是本店店员");
            entity.setId(id);
        }
        shopkeeperService.updateById(entity);
        return Response.restResult(entity, ResultCode.SUCCESS);
    }

}
