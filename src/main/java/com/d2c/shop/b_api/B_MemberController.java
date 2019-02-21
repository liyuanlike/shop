package com.d2c.shop.b_api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.shop.b_api.base.B_BaseController;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
import com.d2c.shop.common.utils.QueryUtil;
import com.d2c.shop.modules.member.model.MemberDO;
import com.d2c.shop.modules.member.model.MemberShopDO;
import com.d2c.shop.modules.member.query.MemberShopQuery;
import com.d2c.shop.modules.member.service.MemberService;
import com.d2c.shop.modules.member.service.MemberShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cai
 */
@Api(description = "客户业务")
@RestController
@RequestMapping("/b_api/member")
public class B_MemberController extends B_BaseController {

    @Autowired
    private MemberShopService memberShopService;
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<MemberDO>> list(PageModel page) {
        MemberShopQuery query = new MemberShopQuery();
        query.setShopId(loginKeeperHolder.getLoginId());
        Page<MemberShopDO> pager = (Page<MemberShopDO>) memberShopService.page(page, QueryUtil.buildWrapper(query));
        List<Long> memberIds = new ArrayList<>();
        pager.getRecords().forEach(item -> memberIds.add(item.getMemberId()));
        List<MemberDO> members = (List<MemberDO>) memberService.listByIds(memberIds);
        pager.setRecords(null);
        Page<MemberDO> result = new Page<>();
        BeanUtils.copyProperties(pager, result);
        result.setRecords(members);
        return Response.restResult(result, ResultCode.SUCCESS);
    }

}
