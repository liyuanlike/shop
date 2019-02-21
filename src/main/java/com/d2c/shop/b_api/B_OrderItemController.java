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

/**
 * @author Cai
 */
@Api(description = "订单明细业务")
@RestController
@RequestMapping("/b_api/order_item")
public class B_OrderItemController extends B_BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<OrderItemDO>> list(PageModel page, OrderItemQuery query) {
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<OrderItemDO> pager = (Page<OrderItemDO>) orderItemService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<OrderDO> select(@PathVariable Long id) {
        OrderItemDO orderItem = orderItemService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        OrderQuery query = new OrderQuery();
        query.setSn(orderItem.getOrderSn());
        OrderDO order = orderService.getOne(QueryUtil.buildWrapper(query));
        order.getOrderItemList().add(orderItem);
        return Response.restResult(order, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "订单明细发货")
    @RequestMapping(value = "/deliver", method = RequestMethod.POST)
    public R deliverItem(Long orderItemId, String logisticsCom, String logisticsNum) {
        OrderItemDO orderItem = orderItemService.getById(orderItemId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        Asserts.eq(orderItem.getStatus(), OrderItemDO.StatusEnum.WAIT_DELIVER.name(), "订单明细状态异常");
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
