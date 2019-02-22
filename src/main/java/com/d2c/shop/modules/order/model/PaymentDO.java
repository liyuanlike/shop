package com.d2c.shop.modules.order.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("o_payment")
@ApiModel(description = "支付表")
public class PaymentDO extends BaseDelDO {

    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "支付方式")
    private String paymentType;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value = "unionId")
    private String unionId;
    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名")
    private String paymentTypeName;

    public String getPaymentTypeName() {
        if (StrUtil.isBlank(paymentType)) return "";
        return PaymentTypeEnum.valueOf(paymentType).getDescription();
    }

    public enum PaymentTypeEnum {
        //
        ALI_PAY("支付宝"), WX_PAY("微信支付");
        //
        private String description;

        PaymentTypeEnum(String description) {
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
