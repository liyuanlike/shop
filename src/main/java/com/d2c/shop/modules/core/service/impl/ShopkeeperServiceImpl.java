package com.d2c.shop.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.core.mapper.ShopkeeperMapper;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author BaiCai
 */
@Service
public class ShopkeeperServiceImpl extends ServiceImpl<ShopkeeperMapper, ShopkeeperDO> implements ShopkeeperService {

    @Override
    @Cacheable(value = "SHOPKEEPER", key = "'findByAccount:'+#account", unless = "#result == null")
    public ShopkeeperDO findByAccount(String account) {
        QueryWrapper<ShopkeeperDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        ShopkeeperDO keeper = this.getOne(queryWrapper);
        return keeper;
    }

    @Override
    @Transactional
    @CacheEvict(value = "SHOPKEEPER", key = "'findByAccount:'+#account")
    public boolean doLogin(String account, String accessToken) {
        ShopkeeperDO entity = ShopkeeperDO.builder()
                .accessToken(accessToken)
                .loginDate(new Date())
                .build();
        UpdateWrapper<ShopkeeperDO> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", account);
        return this.update(entity, updateWrapper);
    }

}
