package com.d2c.shop.modules.order.query;

import com.d2c.shop.common.api.annotation.Condition;
import com.d2c.shop.common.api.base.BaseQuery;
import com.d2c.shop.common.api.emuns.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
public class OrderItemQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @Condition(condition = ConditionEnum.IN)
    @ApiModelProperty(value = "订单号")
    private String[] orderSn;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "类型")
    private Integer type;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "状态")
    private Integer status;

}
