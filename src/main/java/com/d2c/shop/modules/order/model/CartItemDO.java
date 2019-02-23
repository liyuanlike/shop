package com.d2c.shop.modules.order.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import com.d2c.shop.modules.order.model.support.ITradeItem;
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
@TableName("o_cart_item")
@ApiModel(description = "购物车表")
public class CartItemDO extends BaseDO implements ITradeItem {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @ApiModelProperty(value = "商品SKU的ID")
    private Long productSkuId;
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    @ApiModelProperty(value = "商品规格")
    private String standard;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品图片")
    private String productPic;
    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;
    @TableField(exist = false)
    @ApiModelProperty(value = "实时单价")
    private BigDecimal realPrice;

}
