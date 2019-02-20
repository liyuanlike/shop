package com.d2c.shop.modules.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import com.d2c.shop.modules.order.model.support.ITradeItem;
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
public class OrderItemDO extends BaseDelDO implements ITradeItem {

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
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "类型")
    private Integer type;
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
    @ApiModelProperty(value = "物流公司")
    private String logisticsCom;
    @ApiModelProperty(value = "物流单号")
    private String logisticsNum;

    public enum TypeEnum {
        //
        NORMAL(0, "普通"), CROWD(1, "拼团");
        //
        private Integer code;
        private String description;

        TypeEnum(Integer code, String description) {
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

    public enum StatusEnum {
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
