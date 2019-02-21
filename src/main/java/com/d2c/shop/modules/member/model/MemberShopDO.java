package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cai
 */
@Data
@Builder
@TableName("m_member_shop")
@ApiModel(description = "会员店铺关系表")
public class MemberShopDO extends BaseDO {

    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;

}
