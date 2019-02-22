package com.d2c.shop.c_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.c_api.base.C_BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.member.model.AddressDO;
import com.d2c.shop.modules.member.query.AddressQuery;
import com.d2c.shop.modules.member.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cai
 */
@Api(description = "收货地址业务")
@RestController
@RequestMapping("/c_api/address")
public class C_AddressController extends C_BaseController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<AddressDO>> list(PageModel page) {
        AddressQuery query = new AddressQuery();
        query.setMemberId(loginMemberHolder.getLoginId());
        Page<AddressDO> pager = (Page<AddressDO>) addressService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<AddressDO> select(@PathVariable Long id) {
        AddressQuery query = new AddressQuery();
        query.setId(id);
        query.setMemberId(loginMemberHolder.getLoginId());
        AddressDO address = addressService.getOne(QueryUtil.buildWrapper(query));
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, address);
        return Response.restResult(address, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<AddressDO> insert(@RequestBody AddressDO address) {
        address.setMemberId(loginMemberHolder.getLoginId());
        address.setMemberAccount(loginMemberHolder.getLoginAccount());
        addressService.save(address);
        return Response.restResult(address, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<AddressDO> update(@RequestBody AddressDO address) {
        Asserts.eq(address.getMemberId(), loginMemberHolder.getLoginId(), "您不是本人");
        if (address.getDefaults() == 1) {
            AddressQuery query = new AddressQuery();
            query.setMemberId(loginMemberHolder.getLoginId());
            AddressDO entity = AddressDO.builder().defaults(0).build();
            addressService.update(entity, QueryUtil.buildWrapper(query));
        }
        addressService.updateById(address);
        return Response.restResult(address, ResultCode.SUCCESS);
    }

}
