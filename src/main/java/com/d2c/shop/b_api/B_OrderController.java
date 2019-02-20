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
import com.d2c.shop.modules.order.model.OrderDO;
import com.d2c.shop.modules.order.model.OrderItemDO;
import com.d2c.shop.modules.order.query.OrderItemQuery;
import com.d2c.shop.modules.order.query.OrderQuery;
import com.d2c.shop.modules.order.service.OrderItemService;
import com.d2c.shop.modules.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cai
 */
@Api(description = "订单业务")
@RestController
@RequestMapping("/b_api/order")
public class B_OrderController extends B_BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<OrderDO>> list(PageModel page, OrderQuery query) {
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<OrderDO> pager = (Page<OrderDO>) orderService.page(page, QueryUtil.buildWrapper(query));
        List<String> orderSns = new ArrayList<>();
        Map<String, OrderDO> orderMap = new ConcurrentHashMap<>();
        for (OrderDO order : pager.getRecords()) {
            orderSns.add(order.getSn());
            orderMap.put(order.getSn(), order);
        }
        OrderItemQuery itemQuery = new OrderItemQuery();
        itemQuery.setOrderSn(orderSns.toArray(new String[0]));
        List<OrderItemDO> orderItemList = orderItemService.list(QueryUtil.buildWrapper(itemQuery));
        for (OrderItemDO orderItem : orderItemList) {
            if (orderMap.get(orderItem.getOrderSn()) != null) {
                orderMap.get(orderItem.getOrderSn()).getOrderItemList().add(orderItem);
            }
        }
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<OrderDO> select(@PathVariable Long id) {
        OrderDO order = orderService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, order);
        OrderItemQuery itemQuery = new OrderItemQuery();
        itemQuery.setOrderSn(new String[]{order.getSn()});
        List<OrderItemDO> orderItemList = orderItemService.list(QueryUtil.buildWrapper(itemQuery));
        order.getOrderItemList().addAll(orderItemList);
        return Response.restResult(order, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "修改收货地址")
    @RequestMapping(value = "/update/address", method = RequestMethod.POST)
    public R updateAddress(Long orderId, String province, String city, String district, String address, String name, String mobile) {
        OrderDO order = orderService.getById(orderId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, order);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(order.getShopId(), keeper.getShopId(), "您不是本店店员");
        OrderDO entity = OrderDO.builder()
                .province(province)
                .city(city)
                .district(district)
                .address(address)
                .name(name)
                .mobile(mobile)
                .build();
        entity.setId(orderId);
        orderService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "修改付款金额")
    @RequestMapping(value = "/update/amount", method = RequestMethod.POST)
    public R updateAmount(Long orderId, BigDecimal productAmount) {
        Asserts.notNull("修改金额不能为空", productAmount);
        OrderDO order = orderService.getById(orderId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, order);
        Asserts.eq(order.getStatus(), OrderDO.StatusEnum.WAIT_PAY.getCode(), "只能修改未付款订单金额");
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(order.getShopId(), keeper.getShopId(), "您不是本店店员");
        Asserts.ge(productAmount.subtract(order.getCouponAmount()), BigDecimal.ZERO, "付款金额必须大于0");
        OrderDO entity = OrderDO.builder()
                .productAmount(productAmount)
                .build();
        entity.setId(orderId);
        orderService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "分页查询明细")
    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public R<Page<OrderItemDO>> listItem(PageModel page, OrderItemQuery query) {
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<OrderItemDO> pager = (Page<OrderItemDO>) orderItemService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询明细")
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public R<OrderDO> selectItem(@PathVariable Long id) {
        OrderItemDO orderItem = orderItemService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        OrderQuery query = new OrderQuery();
        query.setSn(orderItem.getOrderSn());
        OrderDO order = orderService.getOne(QueryUtil.buildWrapper(query));
        order.getOrderItemList().add(orderItem);
        return Response.restResult(order, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "订单明细发货")
    @RequestMapping(value = "/item/deliver", method = RequestMethod.POST)
    public R deliverItem(Long orderItemId, String logisticsCom, String logisticsNum) {
        OrderItemDO orderItem = orderItemService.getById(orderItemId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        Asserts.eq(orderItem.getStatus(), OrderItemDO.StatusEnum.WAIT_DELIVER.getCode(), "订单明细状态异常");
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(orderItem.getShopId(), keeper.getShopId(), "您不是本店店员");
        OrderItemDO entity = OrderItemDO.builder()
                .logisticsCom(logisticsCom)
                .logisticsNum(logisticsNum)
                .build();
        entity.setId(orderItemId);
        orderItemService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
