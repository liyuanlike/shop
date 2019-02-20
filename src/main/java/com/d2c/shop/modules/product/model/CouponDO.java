package com.d2c.shop.modules.product.model;

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
@TableName("p_coupon")
@ApiModel(description = "优惠券表")
public class CouponDO extends BaseDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "满XX元")
    private BigDecimal needAmount;
    @ApiModelProperty(value = "减XX元")
    private BigDecimal amount;
    @ApiModelProperty(value = "总发行量")
    private Integer circulation;
    @ApiModelProperty(value = "已领用量")
    private Integer consumption;
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

    public enum TypeEnum {
        //
        ALL(0, "全部商品"),
        INCLUDE(1, "指定商品可用"),
        EXCLUDE(-1, "指定商品不可用");
        //
        private Integer code;
        private String description;

        TypeEnum(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
