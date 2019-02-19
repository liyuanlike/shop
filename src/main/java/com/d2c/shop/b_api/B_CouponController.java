package com.d2c.shop.b_api;

import com.d2c.shop.b_api.base.B_BaseController;
import com.d2c.shop.modules.member.service.CouponService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "优惠券业务")
@RestController
@RequestMapping("/b_api/coupon")
public class B_CouponController extends B_BaseController {

    @Autowired
    private CouponService couponService;

}
