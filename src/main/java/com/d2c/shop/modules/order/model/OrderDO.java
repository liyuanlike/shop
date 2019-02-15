package com.d2c.shop.modules.order.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.modules.member.model.support.IAddressBO;
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
@TableName("o_order")
@ApiModel(description = "订单表")
public class OrderDO extends IAddressBO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "订单号")
    private String sn;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "商品总价")
    private BigDecimal productAmount;
    @ApiModelProperty(value = "优惠券折减")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "实际支付")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "支付方式")
    private Integer paymentType;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @TableField(exist = false)
    @ApiModelProperty(value = "订单明细列表")
    private List<OrderItemDO> orderItemList = new ArrayList<>();

    public enum StatusEnum {
        //
        WAIT_PAY(0, "待付款"),
        PAID(1, "已付款"),
        SUCCESS(8, "交易成功"),
        CLOSED(-9, "交易关闭");
        //
        private Integer code;
        private String description;

        StatusEnum(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
