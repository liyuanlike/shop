package com.d2c.shop.modules.member.controller;

import com.d2c.shop.common.api.base.BaseCtrl;
import com.d2c.shop.modules.member.model.MemberShopDO;
import com.d2c.shop.modules.member.query.MemberShopQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "会员店铺关系")
@RestController
@RequestMapping("/back/member_shop")
public class MemberShopController extends BaseCtrl<MemberShopDO, MemberShopQuery> {

}
