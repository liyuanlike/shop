package com.d2c.shop.modules.security.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.security.model.MenuDO;
import com.d2c.shop.modules.security.query.MenuQuery;
import com.d2c.shop.modules.security.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "菜单管理")
@RestController
@RequestMapping("/shop/menu")
public class MenuController extends BaseCtrl<MenuService, MenuDO, MenuQuery> {

}
