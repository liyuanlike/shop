package com.d2c.shop.modules.security.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.api.base.extension.BaseExcelCtrl;
import com.d2c.shop.modules.security.model.UserDO;
import com.d2c.shop.modules.security.query.UserQuery;
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
@RequestMapping("/back/user")
public class UserController extends BaseExcelCtrl<UserDO, UserQuery> {

    @ApiOperation(value = "登录过期")
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public R expired() {
        return Response.failed(ResultCode.LOGIN_EXPIRED);
    }

    /**
     * 方法签名一致，可覆盖不安全的insert
     */
    @Override
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<UserDO> insert(@RequestBody UserDO user) {
        Asserts.notNull(ResultCode.REQUEST_PARAM_NULL, user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return super.insert(user);
    }

    /**
     * 方法签名一致，可覆盖不安全的update
     */
    @Override
    @ApiOperation(value = "用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<UserDO> update(@RequestBody UserDO user) {
        Asserts.notNull(ResultCode.REQUEST_PARAM_NULL, user);
        user.setUsername(null);
        user.setPassword(null);
        return super.update(user);
    }

}
