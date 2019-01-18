package com.d2c.shop.modules.security.controller;

import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.security.model.UserDO;
import com.d2c.shop.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/shop/user")
public class UserController extends BaseCtrl<UserService, UserDO> {

    @ApiOperation(value = "登录过期")
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public R expired() {
        return Response.failed(ErrorCode.LOGIN_EXPIRED);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public R insert(@RequestBody UserDO user) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean success = service.save(user);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(user, ErrorCode.SUCCESS);
    }

}
