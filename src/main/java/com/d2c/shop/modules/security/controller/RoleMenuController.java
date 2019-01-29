package com.d2c.shop.modules.security.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.security.model.RoleMenuDO;
import com.d2c.shop.modules.security.query.RoleMenuQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "角色菜单关系")
@RestController
@RequestMapping("/shop/role_menu")
public class RoleMenuController extends BaseCtrl<RoleMenuDO, RoleMenuQuery> {

}
