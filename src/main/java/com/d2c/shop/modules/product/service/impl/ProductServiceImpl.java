package com.d2c.shop.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.product.mapper.ProductMapper;
import com.d2c.shop.modules.product.model.ProductDO;
import com.d2c.shop.modules.product.model.ProductSkuDO;
import com.d2c.shop.modules.product.service.ProductService;
import com.d2c.shop.modules.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BaiCai
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    @Autowired
    private ProductSkuService productSkuService;

    @Override
    @Transactional
    public ProductDO create(ProductDO product) {
        this.save(product);
        for (ProductSkuDO sku : product.getSkuList()) {
            sku.setProductId(product.getId());
            productSkuService.save(sku);
        }
        return product;
    }

    @Override
    @Transactional
    public boolean update(ProductDO product) {
        boolean success = this.updateById(product);
        QueryWrapper<ProductSkuDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", product.getId());
        List<ProductSkuDO> oldList = productSkuService.list(queryWrapper);
        Map<Long, ProductSkuDO> oldMap = new ConcurrentHashMap<>();
        for (ProductSkuDO old : oldList) {
            oldMap.put(old.getId(), old);
        }
        for (ProductSkuDO sku : product.getSkuList()) {
            if (oldMap.get(sku.getId()) != null) {
                // 更新
                productSkuService.updateById(sku);
                oldList.remove(sku);
            } else {
                // 新增
                sku.setProductId(product.getId());
                productSkuService.save(sku);
            }
        }
        // 删除
        for (ProductSkuDO sku : oldList) {
            productSkuService.removeById(sku.getId());
        }
        return success;
    }

}
