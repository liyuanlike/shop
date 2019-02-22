package com.d2c.shop.c_api;

import com.d2c.shop.c_api.base.C_BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "优惠券业务")
@RestController
@RequestMapping("/c_api/coupon")
public class C_CouponController extends C_BaseController {

}
