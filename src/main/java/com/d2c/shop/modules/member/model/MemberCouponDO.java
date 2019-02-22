package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Cai
 */
@Data
@Builder
@TableName("m_member_shop")
@ApiModel(description = "会员优惠券")
public class MemberCouponDO extends BaseDO {

    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;
    @ApiModelProperty(value = "使用固定期限-开始")
    private Date serviceStartDate;
    @ApiModelProperty(value = "使用固定期限-结束")
    private Date serviceEndDate;

}
