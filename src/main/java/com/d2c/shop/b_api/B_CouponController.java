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
import com.d2c.shop.modules.product.query.CouponQuery;
import com.d2c.shop.modules.product.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Cai
 */
@Api(description = "优惠券业务")
@RestController
@RequestMapping("/b_api/coupon")
public class B_CouponController extends B_BaseController {

    @Autowired
    private CouponService couponService;

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

}
