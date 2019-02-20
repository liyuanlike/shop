package com.d2c.shop.modules.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cai
 */
@Data
@Builder
@TableName("core_shop_flow")
@ApiModel(description = "店铺流水表")
public class ShopFlowDO extends BaseDelDO {

}
