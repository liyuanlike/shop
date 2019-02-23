package com.d2c.shop.modules.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
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
@TableName("core_shop")
@ApiModel(description = "店铺表")
public class ShopDO extends BaseDelDO {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "Logo")
    private String logo;
    @ApiModelProperty(value = "Banner")
    private String banner;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "公告")
    private String notice;
    @ApiModelProperty(value = "经营范围")
    private String scope;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String telephone;
    @ApiModelProperty(value = "营业时间")
    private String hours;
    @ApiModelProperty(value = "退货地址")
    private String returnAddress;
    @ApiModelProperty(value = "认证 1,0")
    private Integer authenticate;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "有效期")
    private Date validDate;
    @ApiModelProperty(value = "企业名称")
    private String enterprise;
    @ApiModelProperty(value = "营业执照号")
    private String licenseNum;
    @ApiModelProperty(value = "营业执照图")
    private String licensePic;
    @ApiModelProperty(value = "法人姓名")
    private String corporationName;
    @ApiModelProperty(value = "法人身份证号")
    private String corporationCard;
    @ApiModelProperty(value = "法人身份证图")
    private String corporationPic;
    @ApiModelProperty(value = "资金余额")
    private BigDecimal balance;

}
