package com.d2c.shop.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.order.model.OrderDO;

/**
 * @author BaiCai
 */
public interface OrderService extends IService<OrderDO> {

    OrderDO create(OrderDO order);

}
