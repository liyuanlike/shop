package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author BaiCai
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("p_product_sku")
@ApiModel(description = "商品SKU表")
public class ProductSkuDO extends BaseDelDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @ApiModelProperty(value = "规格")
    private String standard;
    @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "预警库存")
    private Integer warnStock;

}
