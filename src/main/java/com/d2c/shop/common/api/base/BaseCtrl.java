package com.d2c.shop.common.api.base;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.PageModel;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.utils.QueryUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author BaiCai
 */
public abstract class BaseCtrl<E extends BaseDO, Q extends BaseQuery> {

    @Autowired
    public IService<E> service;

    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(@RequestBody E entity) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, entity);
        boolean success = service.saveOrUpdate(entity);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(entity, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID获取数据")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public R select(@PathVariable Long id) {
        E entity = service.getById(id);
        if (entity == null) {
            return Response.failed(ErrorCode.RESPONSE_DATA_NULL);
        }
        return Response.restResult(entity, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID更新数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody E entity) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, entity);
        boolean success = service.updateById(entity);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Long[] ids) {
        boolean success = service.removeByIds(CollUtil.toList(ids));
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "分页查询数据")
    @RequestMapping(value = "/select/page", method = RequestMethod.POST)
    public R selectPage(PageModel page, Q query) {
        Page<E> pager = (Page<E>) service.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ErrorCode.SUCCESS);
    }

}
