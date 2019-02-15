package com.d2c.shop.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.order.mapper.OrderItemMapper;
import com.d2c.shop.modules.order.model.OrderItemDO;
import com.d2c.shop.modules.order.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDO> implements OrderItemService {

}
