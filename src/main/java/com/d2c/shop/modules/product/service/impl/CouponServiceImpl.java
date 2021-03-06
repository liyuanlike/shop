package com.d2c.shop.modules.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.product.mapper.CouponMapper;
import com.d2c.shop.modules.product.model.CouponDO;
import com.d2c.shop.modules.product.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public int updateConsumption(Long id) {
        return couponMapper.updateConsumption(id);
    }

}
