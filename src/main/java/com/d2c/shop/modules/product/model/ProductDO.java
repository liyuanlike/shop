package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BaiCai
 */
@Data
@TableName("p_product")
@ApiModel(description = "商品表")
public class ProductDO extends BaseDO {

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
    @TableField(exist = false)
    @ApiModelProperty(value = "商品的SKU列表")
    private List<ProductSkuDO> skuList = new ArrayList<>();

}
