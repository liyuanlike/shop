package com.d2c.shop.modules.order.model.support;

import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Cai
 */
@Data
public class ITradeItemBO extends BaseDO implements ITradeItem {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @ApiModelProperty(value = "商品SKU的ID")
    private Long productSkuId;
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    @ApiModelProperty(value = "商品规格")
    private String standard;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品图片")
    private String productPic;

}
