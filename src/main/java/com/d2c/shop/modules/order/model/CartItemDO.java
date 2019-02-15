package com.d2c.shop.modules.order.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.modules.order.model.support.ITradeItemBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BaiCai
 */
@Data
@TableName("o_cart_item")
@ApiModel(description = "购物车表")
public class CartItemDO extends ITradeItemBO {

    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;
    @TableField(exist = false)
    @ApiModelProperty(value = "实时单价")
    private BigDecimal realPrice;

}
