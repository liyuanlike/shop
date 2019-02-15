package com.d2c.shop.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.core.model.ShopkeeperDO;

/**
 * @author BaiCai
 */
public interface ShopkeeperService extends IService<ShopkeeperDO> {

    ShopkeeperDO findByAccount(String account);

    boolean doLogin(String account, String accessToken);

    boolean doLogout(String account);

    boolean updateShopId(String account, Long shopId);

    boolean updatePassword(String account, String password);

}
