package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("o_coupon")
@ApiModel(description = "优惠券表")
public class CouponDO extends BaseDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "满XX元")
    private BigDecimal needAmount;
    @ApiModelProperty(value = "减XX元")
    private BigDecimal amount;
    @ApiModelProperty(value = "总发行量")
    private Integer circulation;
    @ApiModelProperty(value = "单人限领")
    private Integer restriction;
    @ApiModelProperty(value = "领取期限-开始")
    private Date receiveStartDate;
    @ApiModelProperty(value = "领取期限-结束")
    private Date receiveEndDate;
    @ApiModelProperty(value = "使用固定期限-开始")
    private Date serviceStartDate;
    @ApiModelProperty(value = "使用固定期限-结束")
    private Date serviceEndDate;
    @ApiModelProperty(value = "使用顺延期限-小时")
    private Integer serviceSustain;

}
