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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BaiCai
 */
@CacheConfig(cacheNames = "USER")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private RoleService roleService;

    @Override
    @Cacheable(key = "'findByUsername:'+#username")
    public UserDO findByUsername(String username) {
        Wrapper<UserDO> queryWrapper = new QueryWrapper();
        ((QueryWrapper<UserDO>) queryWrapper).eq("username", username);
        UserDO user = this.getOne(queryWrapper);
        if (user == null) return null;
        List<RoleDO> roles = roleService.findByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

}
