package com.d2c.shop.modules.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.product.mapper.ProductMapper;
import com.d2c.shop.modules.product.model.ProductDO;
import com.d2c.shop.modules.product.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

}
