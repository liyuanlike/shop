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
@TableName("core_shop_withdraw")
@ApiModel(description = "店铺提现表")
public class ShopWithdrawDO extends BaseDelDO {

}
