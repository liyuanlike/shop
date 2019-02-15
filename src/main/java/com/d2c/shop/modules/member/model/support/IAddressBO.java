package com.d2c.shop.modules.member.model.support;

import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Cai
 */
@Data
public class IAddressBO extends BaseDO implements IAddress {

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

}