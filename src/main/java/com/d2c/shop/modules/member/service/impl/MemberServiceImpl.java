package com.d2c.shop.modules.member.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.member.mapper.MemberMapper;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.service.MemberService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author BaiCai
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberDO> implements MemberService {

    @Override
    @Cacheable(value = "MEMBER", key = "'findByAccount:'+#account", unless = "#result == null")
    public MemberDO findByAccount(String account) {
        QueryWrapper<MemberDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        MemberDO member = this.getOne(queryWrapper);
        return member;
    }

    @Override
    @CacheEvict(value = "MEMBER", key = "'findByAccount:'+#account")
    public boolean doLogin(String account, String accessToken) {
        MemberDO entity = MemberDO.builder()
                .accessToken(DigestUtil.md5Hex(accessToken))
                .loginDate(new Date())
                .build();
        UpdateWrapper<MemberDO> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", account);
        return this.update(entity, updateWrapper);
    }

    @Override
    @CacheEvict(value = "MEMBER", key = "'findByAccount:'+#account")
    public boolean doLogout(String account) {
        MemberDO entity = MemberDO.builder()
                .accessToken("")
                .build();
        UpdateWrapper<MemberDO> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", account);
        return this.update(entity, updateWrapper);
    }

    @Override
    @CacheEvict(value = "MEMBER", key = "'findByAccount:'+#account")
    public boolean updatePassword(String account, String password) {
        MemberDO entity = MemberDO.builder()
                .password(password)
                .build();
        UpdateWrapper<MemberDO> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", account);
        return this.update(entity, updateWrapper);
    }

}
