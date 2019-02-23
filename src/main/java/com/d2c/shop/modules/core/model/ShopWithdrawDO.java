package com.d2c.shop.modules.core.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("core_shop_withdraw")
@ApiModel(description = "店铺提现表")
public class ShopWithdrawDO extends BaseDelDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "店员ID")
    private Long shopKeeperId;
    @ApiModelProperty(value = "店员账号")
    private String shopKeeperAccount;
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "到账金额")
    private BigDecimal arrivalAmount;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "支付方式")
    private String payType;
    @ApiModelProperty(value = "支付流水")
    private String paySn;
    @ApiModelProperty(value = "支付时间")
    private Date payDate;
    @ApiModelProperty(value = "支付账号")
    private String payAccount;
    @TableField(exist = false)
    @ApiModelProperty(value = "状态名")
    private String statusName;

    public String getStatusName() {
        if (StrUtil.isBlank(status)) return "";
        return StatusEnum.valueOf(status).getDescription();
    }

    public enum StatusEnum {
        //
        CLOSE("已关闭"),
        APPLY("申请中"),
        SUCCESS("已完成");
        //
        private String description;

        StatusEnum(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
