package com.d2c.shop.c_api;

import com.d2c.shop.c_api.base.C_BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "订单明细业务")
@RestController
@RequestMapping("/c_api/order_item")
public class C_OrderItemController extends C_BaseController {

}
