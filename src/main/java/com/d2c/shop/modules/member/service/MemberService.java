package com.d2c.shop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.member.model.MemberDO;

/**
 * @author BaiCai
 */
public interface MemberService extends IService<MemberDO> {

    MemberDO findByAccount(String account);

    boolean doLogin(String account, String accessToken);

    boolean doLogout(String account);

    boolean updatePassword(String account, String password);

}
