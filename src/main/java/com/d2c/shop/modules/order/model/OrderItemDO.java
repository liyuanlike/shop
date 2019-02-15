package com.d2c.shop.modules.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.modules.order.model.support.ITradeItemBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("o_order_item")
@ApiModel(description = "订单明细表")
public class OrderItemDO extends ITradeItemBO {

    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "支付方式")
    private Integer paymentType;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @ApiModelProperty(value = "实时单价")
    private BigDecimal realPrice;
    @ApiModelProperty(value = "优惠券加权折减")
    private BigDecimal couponWeightingAmount;

    public enum TypeEnum {
        //
        NORMAL("普通", NormalStatusEnum.class), CROWD("拼团", CrowdStatusEnum.class);
        //
        private String description;
        private Class<?> statusClazz;

        TypeEnum(String description, Class<?> statusClazz) {
            this.description = description;
            this.statusClazz = statusClazz;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Class<?> getStatusClazz() {
            return statusClazz;
        }

        public void setStatusClazz(Class<?> statusClazz) {
            this.statusClazz = statusClazz;
        }
    }

    public enum NormalStatusEnum {
        //
        WAIT_PAY(0, "待付款"),
        WAIT_DELIVER(4, "待发货"),
        DELIVERED(6, "已发货"),
        SUCCESS(8, "交易成功"),
        CLOSED(-9, "交易关闭");
        //
        private Integer code;
        private String description;

        NormalStatusEnum(Integer code, String description) {
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

    public enum CrowdStatusEnum {
        //
        WAIT_PAY(0, "待付款"),
        PAID(1, "已付款"),
        WAIT_DELIVER(4, "待发货"),
        DELIVERED(6, "已发货"),
        SUCCESS(8, "交易成功"),
        WAIT_REFUND(-4, "待退款"),
        REFUNDED(-8, "已退款"),
        CLOSED(-9, "交易关闭");
        //
        private Integer code;
        private String description;

        CrowdStatusEnum(Integer code, String description) {
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
