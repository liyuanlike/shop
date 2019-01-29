package com.d2c.shop.modules.security.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.security.model.UserRoleDO;
import com.d2c.shop.modules.security.query.UserRoleQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "用户角色关系")
@RestController
@RequestMapping("/shop/user_role")
public class UserRoleController extends BaseCtrl<UserRoleDO, UserRoleQuery> {

}
