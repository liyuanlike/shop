package com.d2c.shop.modules.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.security.mapper.UserMapper;
import com.d2c.shop.modules.security.model.RoleDO;
import com.d2c.shop.modules.security.model.UserDO;
import com.d2c.shop.modules.security.service.RoleService;
import com.d2c.shop.modules.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author BaiCai
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisTemplate<String, UserDO> redisTemplate;

    @Override
    public UserDO findByUsername(String username) {
        UserDO user = null;
        String key = "USER::findByUsername:" + username;
        UserDO cache = redisTemplate.opsForValue().get(key);
        if (cache != null) {
            user = cache;
        } else {
            Wrapper<UserDO> queryWrapper = new QueryWrapper();
            ((QueryWrapper<UserDO>) queryWrapper).eq("username", username);
            user = this.getOne(queryWrapper);
        }
        if (user == null) return null;
        List<RoleDO> roles = roleService.findByUserId(user.getId());
        user.setRoles(roles);
        if (cache == null) {
            redisTemplate.opsForValue().set(key, user, 1, TimeUnit.DAYS);
        }
        return user;
    }

}
