package com.d2c.shop.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.order.mapper.OrderMapper;
import com.d2c.shop.modules.order.model.OrderDO;
import com.d2c.shop.modules.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BaiCai
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Override
    @Transactional
    public OrderDO create(OrderDO order) {
        // TODO
        return null;
    }

}
