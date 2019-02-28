package com.d2c.shop.c_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.product.model.ProductCategoryDO;
import com.d2c.shop.modules.product.model.ProductDO;
import com.d2c.shop.modules.product.model.ProductSkuDO;
import com.d2c.shop.modules.product.query.ProductCategoryQuery;
import com.d2c.shop.modules.product.query.ProductQuery;
import com.d2c.shop.modules.product.query.ProductSkuQuery;
import com.d2c.shop.modules.product.service.ProductCategoryService;
import com.d2c.shop.modules.product.service.ProductService;
import com.d2c.shop.modules.product.service.ProductSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cai
 */
@Api(description = "商品业务")
@RestController
@RequestMapping("/c_api/product")
public class C_ProductController extends C_BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<ProductDO>> list(PageModel page, ProductQuery query) {
        if (query.getCategoryId() != null) {
            ProductCategoryDO pc = productCategoryService.getById(query.getCategoryId());
            if (pc != null && pc.getParentId() == null) {
                ProductCategoryQuery pcq = new ProductCategoryQuery();
                pcq.setParentId(pc.getParentId());
                List<ProductCategoryDO> children = productCategoryService.list(QueryUtil.buildWrapper(pcq));
                List<Long> categoryIds = new ArrayList<>();
                children.forEach(item -> categoryIds.add(item.getId()));
                query.setCategoryId(null);
                query.setCategoryIds(categoryIds.toArray(new Long[0]));
            }
        }
        Page<ProductDO> pager = (Page<ProductDO>) productService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<ProductDO> select(@PathVariable Long id) {
        ProductDO product = productService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, product);
        ProductSkuQuery query = new ProductSkuQuery();
        query.setProductId(id);
        List<ProductSkuDO> skuList = productSkuService.list(QueryUtil.buildWrapper(query));
        product.getSkuList().addAll(skuList);
        return Response.restResult(product, ResultCode.SUCCESS);
    }

}
