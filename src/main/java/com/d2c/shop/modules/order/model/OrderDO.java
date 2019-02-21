package com.d2c.shop.modules.order.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import com.d2c.shop.modules.member.model.support.IAddress;
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
public class OrderDO extends BaseDelDO implements IAddress {

    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "区县")
    private String district;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "订单号")
    private String sn;
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
    @ApiModelProperty(value = "商品总价")
    private BigDecimal productAmount;
    @ApiModelProperty(value = "优惠券折减")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "实际支付")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "支付方式")
    private String paymentType;
    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名")
    private String paymentTypeName;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @TableField(exist = false)
    @ApiModelProperty(value = "订单明细列表")
    private List<OrderItemDO> orderItemList = new ArrayList<>();

    public String getTypeName() {
        if (StrUtil.isBlank(type)) return "";
        return TypeEnum.valueOf(type).getDescription();
    }

    public String getStatusName() {
        if (StrUtil.isBlank(status)) return "";
        return StatusEnum.valueOf(status).getDescription();
    }

    public String getPaymentTypeName() {
        if (StrUtil.isBlank(paymentType)) return "";
        return PaymentDO.PaymentTypeEnum.valueOf(paymentType).getDescription();
    }

    public enum TypeEnum {
        //
        NORMAL("普通"), CROWD("拼团");
        //
        private String description;

        TypeEnum(String description) {
            this.description = description;
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
        WAIT_PAY("待付款"), PAID("已付款"),
        SUCCESS("交易成功"), CLOSED("交易关闭");
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
