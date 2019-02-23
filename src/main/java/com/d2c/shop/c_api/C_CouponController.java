package com.d2c.shop.c_api;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.core.model.ShopDO;
import com.d2c.shop.modules.core.service.ShopService;
import com.d2c.shop.modules.member.model.MemberCouponDO;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.query.MemberCouponQuery;
import com.d2c.shop.modules.member.service.MemberCouponService;
import com.d2c.shop.modules.product.model.CouponDO;
import com.d2c.shop.modules.product.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cai
 */
@Api(description = "优惠券业务")
@RestController
@RequestMapping("/c_api/coupon")
public class C_CouponController extends C_BaseController {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<MemberCouponDO>> list(PageModel page, Integer status) {
        MemberCouponQuery query = new MemberCouponQuery();
        query.setMemberId(loginMemberHolder.getLoginId());
        Date now = new Date();
        QueryWrapper<MemberCouponDO> queryWrapper = new QueryWrapper();
        switch (status) {
            case 1:
                query.setStatus(1);
                queryWrapper = QueryUtil.buildWrapper(query);
                break;
            case 0:
                query.setStatus(0);
                query.setServiceEndDateL(now);
                queryWrapper = QueryUtil.buildWrapper(query);
                break;
            case -1:
                query.setStatus(0);
                query.setServiceEndDateR(now);
                queryWrapper = QueryUtil.buildWrapper(query);
                break;
            default:
                break;
        }
        Page<MemberCouponDO> pager = (Page<MemberCouponDO>) memberCouponService.page(page, queryWrapper);
        Set<Long> couponIds = new HashSet<>();
        pager.getRecords().forEach(item -> couponIds.add(item.getCouponId()));
        List<CouponDO> couponList = (List<CouponDO>) couponService.listByIds(couponIds);
        Map<Long, CouponDO> couponMap = new ConcurrentHashMap<>();
        couponList.forEach(item -> couponMap.put(item.getId(), item));
        for (MemberCouponDO mc : pager.getRecords()) {
            mc.setName(couponMap.get(mc.getCouponId()).getName());
            mc.setNeedAmount(couponMap.get(mc.getCouponId()).getNeedAmount());
            mc.setAmount(couponMap.get(mc.getCouponId()).getAmount());
            mc.setRemark(couponMap.get(mc.getCouponId()).getRemark());
        }
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "领取")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<MemberCouponDO> insert(Long couponId, Long shopId) {
        MemberDO member = loginMemberHolder.getLoginMember();
        ShopDO shop = shopService.getById(shopId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, shop);
        CouponDO coupon = couponService.getById(couponId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, coupon);
        Asserts.eq(coupon.available(), false, "优惠券未在指定领取时间范围");
        Asserts.gt(coupon.getCirculation(), coupon.getConsumption(), "优惠券已被一抢而空");
        MemberCouponQuery query = new MemberCouponQuery();
        query.setMemberId(member.getId());
        query.setCouponId(coupon.getShopId());
        int count = memberCouponService.count(QueryUtil.buildWrapper(query));
        Asserts.gt(coupon.getRestriction(), count, "优惠券每人限领" + coupon.getRestriction() + "张，不要贪心哦");
        Date serviceStartDate = coupon.getServiceStartDate();
        Date serviceEndDate = coupon.getServiceEndDate();
        if (coupon.getServiceSustain() != null) {
            serviceStartDate = new Date();
            serviceEndDate = DateUtil.offsetHour(serviceStartDate, coupon.getServiceSustain());
        }
        MemberCouponDO memberCoupon = MemberCouponDO.builder()
                .memberId(member.getId())
                .couponId(coupon.getId())
                .shopId(shop.getId())
                .shopName(shop.getName())
                .status(0)
                .serviceStartDate(serviceStartDate)
                .serviceEndDate(serviceEndDate)
                .build();
        memberCouponService.doReceive(memberCoupon);
        return Response.restResult(memberCoupon, ResultCode.SUCCESS);
    }

}
