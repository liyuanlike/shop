package com.d2c.shop.modules.order.model;

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
    private Integer paymentType;
    @ApiModelProperty(value = "支付流水")
    private String paymentSn;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value = "unionId")
    private String unionId;

}
