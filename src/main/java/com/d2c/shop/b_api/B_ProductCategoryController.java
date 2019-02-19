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
import com.d2c.shop.modules.product.model.ProductCategoryDO;
import com.d2c.shop.modules.product.query.ProductCategoryQuery;
import com.d2c.shop.modules.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Cai
 */
@Api(description = "商品类目业务")
@RestController
@RequestMapping("/b_api/product_category")
public class B_ProductCategoryController extends B_BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "类目查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Collection<ProductCategoryDO>> list(PageModel page) {
        ProductCategoryQuery query = new ProductCategoryQuery();
        query.setShopId(loginKeeperHolder.getLoginId());
        page.setPs(PageModel.MAX_SIZE);
        page.setDesc("sort", "create_date");
        Page<ProductCategoryDO> pager = (Page<ProductCategoryDO>) productCategoryService.page(page, QueryUtil.buildWrapper(query));
        Map<Long, ProductCategoryDO> first = new LinkedHashMap<>();
        List<ProductCategoryDO> second = new ArrayList<>();
        // 一级分类
        for (ProductCategoryDO pc : pager.getRecords()) {
            if (pc.getParentId() == null) {
                first.put(pc.getId(), pc);
            } else if (pc.getParentId() > 0) {
                second.add(pc);
            }
        }
        // 二级分类
        for (ProductCategoryDO pc : second) {
            if (first.get(pc.getParentId()) != null) {
                first.get(pc.getParentId()).getChildren().add(pc);
            }
        }
        return Response.restResult(first.values(), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<ProductCategoryDO> select(@PathVariable Long id) {
        ProductCategoryDO productCategory = productCategoryService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, productCategory);
        return Response.restResult(productCategory, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<ProductCategoryDO> insert(@RequestBody ProductCategoryDO productCategory) {
        productCategory.setShopId(loginKeeperHolder.getLoginKeeper().getShopId());
        productCategoryService.save(productCategory);
        return Response.restResult(productCategory, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<ProductCategoryDO> update(@RequestBody ProductCategoryDO productCategory) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(productCategory.getShopId(), keeper.getShopId(), "您不是本店店员");
        productCategoryService.updateById(productCategory);
        return Response.restResult(productCategory, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public R delete(@PathVariable Long id) {
        ProductCategoryDO productCategory = productCategoryService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, productCategory);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(productCategory.getShopId(), keeper.getShopId(), "您不是本店店员");
        productCategoryService.removeById(id);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
