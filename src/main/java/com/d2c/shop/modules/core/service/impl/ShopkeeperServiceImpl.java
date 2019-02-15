package com.d2c.shop.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.core.mapper.ShopkeeperMapper;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author BaiCai
 */
@Service
public class ShopkeeperServiceImpl extends ServiceImpl<ShopkeeperMapper, ShopkeeperDO> implements ShopkeeperService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ShopkeeperDO findByAccount(String account) {
        QueryWrapper<ShopkeeperDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        ShopkeeperDO keeper = this.getOne(queryWrapper);
        return keeper;
    }

    @Override
    @Transactional
    public boolean doLogin(String account, String accessToken, String oldToken) {
        ShopkeeperDO entity = ShopkeeperDO.builder()
                .accessToken(accessToken)
                .loginDate(new Date())
                .build();
        UpdateWrapper<ShopkeeperDO> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", account);
        boolean success = this.update(entity, updateWrapper);
        if (success && StrUtil.isNotBlank(oldToken)) {
            redisTemplate.delete("SHOPKEEPER::findByToken:" + oldToken);
        }
        return success;
    }

    @Override
    @Cacheable(value = "SHOPKEEPER", key = "'findByToken:'+#token", unless = "#result == null")
    public ShopkeeperDO findByToken(String token) {
        QueryWrapper<ShopkeeperDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("access_token", token);
        ShopkeeperDO keeper = this.getOne(queryWrapper);
        return keeper;
    }

}
