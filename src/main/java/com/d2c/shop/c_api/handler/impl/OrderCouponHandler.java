package com.d2c.shop.c_api.handler.impl;

import com.d2c.shop.c_api.handler.OrderHandler;
import com.d2c.shop.modules.order.model.OrderDO;
import org.springframework.stereotype.Component;

/**
 * @author Cai
 */
@Component
public class OrderCouponHandler implements OrderHandler {

    @Override
    public void operator(OrderDO order, Object... conditions) {
        // TODO
    }

}
