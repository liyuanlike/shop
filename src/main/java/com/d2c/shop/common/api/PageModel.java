package com.d2c.shop.common.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
public class PageModel extends Page {

    @ApiModelProperty(value = "页码")
    private long p;
    @ApiModelProperty(value = "页长")
    private long ps;

    public void setP(long p) {
        this.p = p;
        this.setCurrent(p);
    }

    public void setPs(long ps) {
        this.ps = ps;
        this.setSize(ps);
    }

    public PageModel() {
        super();
    }

}
