package com.d2c.shop.b_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.b_api.base.BaseControllerB;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.modules.core.model.ShopDO;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cai
 */
@Api(description = "店铺业务")
@RestController
@RequestMapping("/b_api/shop")
public class ShopController extends BaseControllerB {

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "店铺信息查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R select(@PathVariable Long id) {
        ShopDO shop = shopService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, shop);
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(id, keeper.getShopId(), "您不是本店店员");
        return Response.restResult(shop, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "开店")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(@RequestBody ShopDO shop) {
        shopService.create(shop, loginKeeperHolder.getLoginAccount());
        return Response.restResult(shop, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody ShopDO shop) {
        ShopkeeperDO keeper = loginKeeperHolder.getLoginKeeper();
        Asserts.eq(shop.getId(), keeper.getShopId(), "您不是本店店员");
        shopService.updateById(shop);
        return Response.restResult(shop, ResultCode.SUCCESS);
    }

}
