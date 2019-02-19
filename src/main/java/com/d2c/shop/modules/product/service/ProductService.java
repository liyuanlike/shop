package com.d2c.shop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.modules.product.model.ProductDO;

/**
 * @author BaiCai
 */
public interface ProductService extends IService<ProductDO> {

    ProductDO create(ProductDO product);

    boolean update(ProductDO product);

}
