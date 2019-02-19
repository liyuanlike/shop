package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("p_product")
@ApiModel(description = "商品表")
public class ProductDO extends BaseDelDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "条码")
    private String sn;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "图片")
    private String pic;
    @ApiModelProperty(value = "销售价")
    private BigDecimal price;
    @ApiModelProperty(value = "类目ID")
    private Long categoryId;
    @ApiModelProperty(value = "分类ID")
    private Long classifyId;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "虚拟 1,0")
    private Integer virtual;
    @TableField(exist = false)
    @ApiModelProperty(value = "商品的SKU列表")
    private List<ProductSkuDO> skuList = new ArrayList<>();

}
