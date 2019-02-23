package com.d2c.shop.modules.product.query;

import com.d2c.shop.common.api.annotation.Condition;
import com.d2c.shop.common.api.base.BaseQuery;
import com.d2c.shop.common.api.emuns.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CouponQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.GT, field = "receive_start_date")
    @ApiModelProperty(value = "领取期限-开始起")
    public Date receiveStartDateL;
    @Condition(condition = ConditionEnum.LE, field = "receive_start_date")
    @ApiModelProperty(value = "领取期限-开始止")
    public Date receiveStartDateR;
    @Condition(condition = ConditionEnum.GE, field = "receive_end_date")
    @ApiModelProperty(value = "领取期限-结束起")
    public Date receiveEndDateL;
    @Condition(condition = ConditionEnum.LT, field = "receive_end_date")
    @ApiModelProperty(value = "领取期限-结束止")
    public Date receiveEndDateR;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "状态")
    private Integer status;

}
