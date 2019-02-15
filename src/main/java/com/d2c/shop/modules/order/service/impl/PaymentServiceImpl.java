package com.d2c.shop.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.order.mapper.PaymentMapper;
import com.d2c.shop.modules.order.model.PaymentDO;
import com.d2c.shop.modules.order.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentDO> implements PaymentService {

}
