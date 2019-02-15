package com.d2c.shop.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.core.model.ShopDO;

/**
 * @author BaiCai
 */
public interface ShopService extends IService<ShopDO> {

    ShopDO create(ShopDO shop, String account);

}
