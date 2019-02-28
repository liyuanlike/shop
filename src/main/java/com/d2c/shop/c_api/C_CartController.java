package com.d2c.shop.c_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.order.model.CartItemDO;
import com.d2c.shop.modules.order.query.CartItemQuery;
import com.d2c.shop.modules.order.service.CartItemService;
import com.d2c.shop.modules.product.model.ProductDO;
import com.d2c.shop.modules.product.model.ProductSkuDO;
import com.d2c.shop.modules.product.service.ProductService;
import com.d2c.shop.modules.product.service.ProductSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "购物车业务")
@RestController
@RequestMapping("/c_api/cart")
public class C_CartController extends C_BaseController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<List<CartItemDO>> list(CartItemQuery query) {
        query.setMemberId(loginMemberHolder.getLoginId());
        List<CartItemDO> list = (List<CartItemDO>) cartItemService.list(QueryUtil.buildWrapper(query));
        List<Long> skuIds = new ArrayList<>();
        Map<Long, CartItemDO> map = new ConcurrentHashMap<>();
        for (CartItemDO cartItem : list) {
            skuIds.add(cartItem.getProductSkuId());
            map.put(cartItem.getProductSkuId(), cartItem);
        }
        List<ProductSkuDO> skuList = (List<ProductSkuDO>) productSkuService.listByIds(skuIds);
        for (ProductSkuDO productSku : skuList) {
            if (map.get(productSku.getId()) != null) {
                map.get(productSku.getId()).setRealPrice(productSku.getSellPrice());
            }
        }
        return Response.restResult(list, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(Long shopId, Long productSkuId, Integer quantity) {
        Asserts.gt(quantity, 0, "数量必须大于0");
        ProductSkuDO sku = productSkuService.getById(productSkuId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, sku);
        CartItemQuery query = new CartItemQuery();
        query.setMemberId(loginMemberHolder.getLoginId());
        query.setShopId(shopId);
        query.setProductSkuId(productSkuId);
        CartItemDO cartItem = cartItemService.getOne(QueryUtil.buildWrapper(query));
        if (cartItem == null) {
            Asserts.ge(sku.getStock(), quantity, sku.getId() + "的SKU库存不足");
            ProductDO product = productService.getById(sku.getProductId());
            CartItemDO entity = CartItemDO.builder()
                    .shopId(shopId)
                    .memberId(loginMemberHolder.getLoginId())
                    .memberAccount(loginMemberHolder.getLoginAccount())
                    .productId(sku.getProductId())
                    .productSkuId(sku.getId())
                    .quantity(quantity)
                    .standard(sku.getStandard())
                    .productName(product.getName())
                    .productPic(product.getFirstPic())
                    .productPrice(product.getPrice())
                    .build();
            cartItemService.save(entity);
        } else {
            Asserts.ge(sku.getStock(), cartItem.getQuantity() + quantity, sku.getId() + "的SKU库存不足");
            CartItemDO entity = CartItemDO.builder()
                    .quantity(cartItem.getQuantity() + quantity)
                    .build();
            entity.setId(cartItem.getId());
            cartItemService.updateById(entity);
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(Long id, Integer quantity) {
        Asserts.gt(quantity, 0, "数量必须大于0");
        CartItemDO cartItem = cartItemService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, cartItem);
        Asserts.eq(cartItem.getMemberId(), loginMemberHolder.getLoginId(), "您不是本人");
        ProductSkuDO sku = productSkuService.getById(cartItem.getProductSkuId());
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, sku);
        Asserts.ge(sku.getStock(), quantity, sku.getId() + "的SKU库存不足");
        CartItemDO entity = CartItemDO.builder()
                .quantity(quantity)
                .build();
        entity.setId(cartItem.getId());
        cartItemService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Long[] ids) {
        CartItemDO cartItem = cartItemService.getById(ids[0]);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, cartItem);
        Asserts.eq(cartItem.getMemberId(), loginMemberHolder.getLoginId(), "您不是本人");
        cartItemService.removeByIds(Arrays.asList(ids));
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}

