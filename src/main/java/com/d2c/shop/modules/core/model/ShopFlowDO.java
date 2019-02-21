package com.d2c.shop.modules.core.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import com.d2c.shop.modules.order.model.OrderDO;
import com.d2c.shop.modules.order.model.PaymentDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Cai
 */
@Data
@Builder
@TableName("core_shop_flow")
@ApiModel(description = "店铺流水表")
public class ShopFlowDO extends BaseDelDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "支付方式")
    private String paymentType;
    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名")
    private String paymentTypeName;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "类型")
    private String type;
    @TableField(exist = false)
    @ApiModelProperty(value = "类型名")
    private String typeName;
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    public String getTypeName() {
        if (StrUtil.isBlank(type)) return "";
        return OrderDO.TypeEnum.valueOf(type).getDescription();
    }

    public String getPaymentTypeName() {
        if (StrUtil.isBlank(paymentType)) return "";
        return PaymentDO.PaymentTypeEnum.valueOf(this.paymentType).getDescription();
    }

    public enum TypeEnum {
        //
        ORDER("订单"), WITHDRAW("提现");
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

}
