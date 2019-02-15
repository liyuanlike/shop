package com.d2c.shop.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.order.mapper.CartItemMapper;
import com.d2c.shop.modules.order.model.CartItemDO;
import com.d2c.shop.modules.order.service.CartItemService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItemDO> implements CartItemService {

}
