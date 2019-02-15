package com.d2c.shop.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.core.mapper.ShopMapper;
import com.d2c.shop.modules.core.model.ShopDO;
import com.d2c.shop.modules.core.service.ShopService;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BaiCai
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, ShopDO> implements ShopService {

    @Autowired
    private ShopkeeperService shopkeeperService;

    @Override
    @Transactional
    public ShopDO create(ShopDO shop, String account) {
        this.save(shop);
        shopkeeperService.updateShopId(account, shop.getId());
        return shop;
    }

}
