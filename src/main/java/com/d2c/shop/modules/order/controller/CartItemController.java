package com.d2c.shop.modules.order.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.order.model.CartItemDO;
import com.d2c.shop.modules.order.query.CartItemQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "购物车管理")
@RestController
@RequestMapping("/back/cart_item")
public class CartItemController extends BaseCtrl<CartItemDO, CartItemQuery> {

}
