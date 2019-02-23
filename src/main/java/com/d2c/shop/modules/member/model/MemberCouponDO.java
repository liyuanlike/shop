package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Cai
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("m_member_shop")
@ApiModel(description = "会员优惠券")
public class MemberCouponDO extends BaseDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "店铺名")
    private String shopName;
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;
    @ApiModelProperty(value = "使用固定期限-开始")
    private Date serviceStartDate;
    @ApiModelProperty(value = "使用固定期限-结束")
    private Date serviceEndDate;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @TableField(exist = false)
    @ApiModelProperty(value = "名称")
    private String name;
    @TableField(exist = false)
    @ApiModelProperty(value = "满XX元")
    private BigDecimal needAmount;
    @TableField(exist = false)
    @ApiModelProperty(value = "减XX元")
    private BigDecimal amount;
    @TableField(exist = false)
    @ApiModelProperty(value = "备注说明")
    private String remark;

}
