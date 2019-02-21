package com.d2c.shop.modules.order.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
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
    private String type;
    @TableField(exist = false)
    @ApiModelProperty(value = "类型名")
    private String typeName;
    @ApiModelProperty(value = "状态")
    private String status;
    @TableField(exist = false)
    @ApiModelProperty(value = "状态名")
    private String statusName;
    @ApiModelProperty(value = "支付方式")
    private String paymentType;
    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名")
    private String paymentTypeName;
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

    public String getTypeName() {
        if (StrUtil.isBlank(type)) return "";
        return OrderDO.TypeEnum.valueOf(type).getDescription();
    }

    public String getStatusName() {
        if (StrUtil.isBlank(status)) return "";
        return StatusEnum.valueOf(status).getDescription();
    }

    public String getPaymentTypeName() {
        if (StrUtil.isBlank(paymentType)) return "";
        return PaymentDO.PaymentTypeEnum.valueOf(paymentType).getDescription();
    }

    public enum StatusEnum {
        //
        WAIT_PAY("待付款"), PAID("已付款"),
        WAIT_DELIVER("待发货"), DELIVERED("已发货"),
        SUCCESS("交易成功"), WAIT_REFUND("待退款"),
        REFUNDED("已退款"), CLOSED("交易关闭");
        //
        private String description;

        StatusEnum(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
