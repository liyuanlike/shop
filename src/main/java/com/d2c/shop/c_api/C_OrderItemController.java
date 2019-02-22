package com.d2c.shop.c_api;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.c_api.handler.OrderHandler;
import com.d2c.shop.c_api.handler.impl.OrderCouponHandler;
import com.d2c.shop.c_api.handler.impl.OrderPromotionHandler;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.api.constant.PrefixConstant;
import com.d2c.shop.modules.member.model.AddressDO;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.model.support.IAddress;
import com.d2c.shop.modules.member.service.AddressService;
import com.d2c.shop.modules.order.model.CartItemDO;
import com.d2c.shop.modules.order.model.OrderDO;
import com.d2c.shop.modules.order.model.OrderItemDO;
import com.d2c.shop.modules.order.model.support.ITradeItem;
import com.d2c.shop.modules.order.service.CartItemService;
import com.d2c.shop.modules.order.service.OrderService;
import com.d2c.shop.modules.product.model.ProductSkuDO;
import com.d2c.shop.modules.product.service.ProductSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cai
 */
@Api(description = "订单明细业务")
@RestController
@RequestMapping("/c_api/order_item")
public class C_OrderItemController extends C_BaseController {

}
