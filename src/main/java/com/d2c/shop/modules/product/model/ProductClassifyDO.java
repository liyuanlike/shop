package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("p_product_classify")
@ApiModel(description = "商品分类表")
public class ProductClassifyDO extends BaseDO {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "父级ID")
    private Long parentId;
    @ApiModelProperty(value = "级别")
    private Integer level;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @TableField(exist = false)
    @ApiModelProperty(value = "子级列表")
    private List<ProductClassifyDO> children = new ArrayList<>();

}
