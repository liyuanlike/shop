package com.d2c.shop.modules.member.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.member.model.AddressDO;
import com.d2c.shop.modules.member.query.AddressQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "收货地址管理")
@RestController
@RequestMapping("/back/address")
public class AddressController extends BaseCtrl<AddressDO, AddressQuery> {

}
