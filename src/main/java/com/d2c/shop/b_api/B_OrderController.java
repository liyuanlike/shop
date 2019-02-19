package com.d2c.shop.b_api;

import com.d2c.shop.b_api.base.B_BaseController;
import com.d2c.shop.modules.order.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "订单业务")
@RestController
@RequestMapping("/b_api/order")
public class B_OrderController extends B_BaseController {

    @Autowired
    private OrderService orderService;

}
