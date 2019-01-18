package com.d2c.shop.modules.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.security.model.UserDO;

/**
 * @author BaiCai
 */
public interface UserService extends IService<UserDO> {

    UserDO findByUsername(String username);

}
