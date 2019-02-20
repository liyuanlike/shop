package com.d2c.shop.b_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.b_api.base.B_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.product.model.ProductDO;
import com.d2c.shop.modules.product.model.ProductDetailDO;
import com.d2c.shop.modules.product.model.ProductSkuDO;
import com.d2c.shop.modules.product.query.ProductDetailQuery;
import com.d2c.shop.modules.product.query.ProductQuery;
import com.d2c.shop.modules.product.service.ProductDetailService;
import com.d2c.shop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cai
 */
@Api(description = "商品业务")
@RestController
@RequestMapping("/b_api/product")
public class B_ProductController extends B_BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<ProductDO>> list(PageModel page, ProductQuery query) {
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<ProductDO> pager = (Page<ProductDO>) productService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<ProductDO> select(@PathVariable Long id) {
        ProductDO product = productService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, product);
        return Response.restResult(product, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<ProductDO> insert(@RequestBody ProductDO product) {
        product.setShopId(loginKeeperHolder.getLoginKeeper().getShopId());
        for (ProductSkuDO sku : product.getSkuList()) {
            sku.setShopId(loginKeeperHolder.getLoginKeeper().getShopId());
        }
        productService.create(product);
        return Response.restResult(product, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<ProductDO> update(@RequestBody ProductDO product) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(product.getShopId(), keeper.getShopId(), "您不是本店店员");
        productService.update(product);
        return Response.restResult(product, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据商品ID查询详情")
    @RequestMapping(value = "/detail/{productId}", method = RequestMethod.GET)
    public R<ProductDetailDO> selectDetail(@PathVariable Long productId) {
        ProductDetailQuery query = new ProductDetailQuery();
        query.setProductId(productId);
        ProductDetailDO productDetail = productDetailService.getOne(QueryUtil.buildWrapper(query));
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, productDetail);
        return Response.restResult(productDetail, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增详情")
    @RequestMapping(value = "/detail/insert", method = RequestMethod.POST)
    public R<ProductDetailDO> insertDetail(@RequestBody ProductDetailDO productDetail) {
        productDetail.setShopId(loginKeeperHolder.getLoginKeeper().getShopId());
        productDetailService.save(productDetail);
        return Response.restResult(productDetail, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新详情")
    @RequestMapping(value = "/detail/update", method = RequestMethod.POST)
    public R<ProductDetailDO> updateDetail(@RequestBody ProductDetailDO productDetail) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(productDetail.getShopId(), keeper.getShopId(), "您不是本店店员");
        productDetailService.updateById(productDetail);
        return Response.restResult(productDetail, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "通过商品ID删除详情")
    @RequestMapping(value = "/detail/delete/{productId}", method = RequestMethod.POST)
    public R deleteDetail(@PathVariable Long productId) {
        ProductDetailQuery query = new ProductDetailQuery();
        query.setProductId(productId);
        ProductDetailDO productDetail = productDetailService.getOne(QueryUtil.buildWrapper(query));
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, productDetail);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(productDetail.getShopId(), keeper.getShopId(), "您不是本店店员");
        productDetailService.removeById(productDetail.getId());
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
