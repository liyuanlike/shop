package com.d2c.shop.modules.security.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.security.model.RoleDO;
import com.d2c.shop.modules.security.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "角色管理")
@RestController
@RequestMapping("/shop/role")
public class RoleController extends BaseCtrl<RoleService, RoleDO> {

}
