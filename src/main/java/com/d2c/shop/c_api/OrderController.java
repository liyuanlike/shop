package com.d2c.shop.c_api;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.BaseController;
import com.d2c.shop.c_api.handler.OrderHandler;
import com.d2c.shop.c_api.handler.impl.OrderCouponHandler;
import com.d2c.shop.c_api.handler.impl.OrderPromotionHandler;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
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
@Api(description = "订单业务")
@RestController
@RequestMapping("/b_api/order")
public class OrderController extends BaseController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPromotionHandler orderPromotionHandler;
    @Autowired
    private OrderCouponHandler orderCouponHandler;

    @ApiOperation(value = "购物车下单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(Long[] cartIds, Long addressId, Long couponId) {
        // 登录用户
        MemberDO member = loginMemberHolder.getLoginMember();
        // 收货地址
        AddressDO address = addressService.getById(addressId);
        Asserts.notNull("收货地址不能为空", address);
        Asserts.eq(address.getMemberId(), member.getId(), "收货地址异常");
        // 生成订单
        Snowflake snowFlake = new Snowflake(1, 1);
        OrderDO order = OrderDO.builder()
                .sn(PrefixConstant.ORDER_PREFIX + String.valueOf(snowFlake.nextId()))
                .status(OrderDO.StatusEnum.WAIT_PAY.getCode())
                .build();
        IAddress iAddress = address;
        BeanUtils.copyProperties(iAddress, order);
        // 生成订单明细
        List<CartItemDO> carts = (List<CartItemDO>) cartItemService.listByIds(Arrays.asList(cartIds));
        List<Long> skuIds = new ArrayList<>();
        carts.forEach(item -> skuIds.add(item.getProductSkuId()));
        List<ProductSkuDO> skuList = (List<ProductSkuDO>) productSkuService.listByIds(skuIds);
        Map<Long, ProductSkuDO> skuMap = new ConcurrentHashMap<>();
        skuList.forEach(item -> skuMap.put(item.getId(), item));
        List<OrderItemDO> orderItemList = new ArrayList<>();
        for (CartItemDO cartItem : carts) {
            ProductSkuDO sku = skuMap.get(cartItem.getProductSkuId());
            Asserts.notNull("商品SKU不能为空", sku);
            Asserts.ge(sku.getStock(), cartItem.getQuantity(), sku.getId() + "的SKU库存不足");
            OrderItemDO orderItem = OrderItemDO.builder()
                    .type(OrderItemDO.TypeEnum.NORMAL.name())
                    .status(OrderItemDO.NormalStatusEnum.WAIT_PAY.getCode())
                    .realPrice(sku.getSellPrice())
                    .build();
            ITradeItem iTradeItem = cartItem;
            BeanUtils.copyProperties(iTradeItem, orderItem);
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        // 处理订单促销
        List<OrderHandler> handlers = new ArrayList<>();
        // 按照顺序
        handlers.add(orderPromotionHandler);
        handlers.add(orderCouponHandler);
        for (OrderHandler handler : handlers) {
            if (handler instanceof OrderCouponHandler) {
                handler.operator(order, couponId);
                continue;
            }
            handler.operator(order);
        }
        // 创建订单
        order = orderService.create(order);
        return Response.restResult(order, ErrorCode.SUCCESS);
    }

}
