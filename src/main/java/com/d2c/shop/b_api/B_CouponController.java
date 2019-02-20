package com.d2c.shop.b_api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.b_api.base.B_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.product.model.CouponDO;
import com.d2c.shop.modules.product.model.CouponProductDO;
import com.d2c.shop.modules.product.query.CouponProductQuery;
import com.d2c.shop.modules.product.query.CouponQuery;
import com.d2c.shop.modules.product.service.CouponProductService;
import com.d2c.shop.modules.product.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Cai
 */
@Api(description = "优惠券业务")
@RestController
@RequestMapping("/b_api/coupon")
public class B_CouponController extends B_BaseController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponProductService couponProductService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<CouponDO>> list(PageModel page, Integer status) {
        CouponQuery query = new CouponQuery();
        query.setShopId(loginKeeperHolder.getLoginId());
        Date now = new Date();
        QueryWrapper<CouponDO> queryWrapper = new QueryWrapper();
        switch (status) {
            case 1:
                query.setStatus(1);
                query.setReceiveStartDateR(now);
                query.setReceiveEndDateL(now);
                queryWrapper = QueryUtil.buildWrapper(query);
                break;
            case 0:
                query.setStatus(1);
                query.setReceiveStartDateL(now);
                queryWrapper = QueryUtil.buildWrapper(query);
                break;
            case -1:
                break;
            default:
                queryWrapper.and(i -> i.eq("status", 0).or().lt("receive_end_date", now));
                break;
        }
        Page<CouponDO> pager = (Page<CouponDO>) couponService.page(page, queryWrapper);
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<CouponDO> select(@PathVariable Long id) {
        CouponDO coupon = couponService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, coupon);
        return Response.restResult(coupon, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<CouponDO> insert(@RequestBody CouponDO coupon) {
        coupon.setShopId(loginKeeperHolder.getLoginKeeper().getShopId());
        couponService.save(coupon);
        return Response.restResult(coupon, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<CouponDO> update(@RequestBody CouponDO coupon) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(coupon.getShopId(), keeper.getShopId(), "您不是本店店员");
        couponService.updateById(coupon);
        return Response.restResult(coupon, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "查询已绑商品")
    @RequestMapping(value = "/product/relation", method = RequestMethod.POST)
    public R<List<Long>> relationProduct(Long[] productIds, Long couponId) {
        CouponProductQuery query = new CouponProductQuery();
        query.setCouponId(couponId);
        query.setProductId(productIds);
        List<CouponProductDO> list = couponProductService.list(QueryUtil.buildWrapper(query));
        List<Long> resultList = new ArrayList<>();
        list.forEach(item -> resultList.add(item.getProductId()));
        return Response.restResult(resultList, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "绑定商品")
    @RequestMapping(value = "/product/insert", method = RequestMethod.POST)
    public R<CouponProductDO> insertProduct(@RequestBody CouponProductDO couponProduct) {
        CouponDO coupon = couponService.getById(couponProduct.getCouponId());
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, coupon);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(coupon.getShopId(), keeper.getShopId(), "您不是本店店员");
        couponProductService.saveOrUpdate(couponProduct);
        return Response.restResult(couponProduct, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "解绑商品")
    @RequestMapping(value = "/product/delete", method = RequestMethod.POST)
    public R deleteProduct(Long couponId, Long productId) {
        CouponDO coupon = couponService.getById(couponId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, coupon);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(coupon.getShopId(), keeper.getShopId(), "您不是本店店员");
        CouponProductQuery query = new CouponProductQuery();
        query.setCouponId(couponId);
        query.setProductId(new Long[]{productId});
        couponProductService.remove(QueryUtil.buildWrapper(query));
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
