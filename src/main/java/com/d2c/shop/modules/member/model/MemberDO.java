package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import com.d2c.shop.modules.core.model.support.IMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@TableName("m_member")
@ApiModel(description = "会员表")
public class MemberDO extends BaseDO implements IMember {

    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "令牌")
    private String accessToken;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "最后登录")
    private Date loginDate;
    @ApiModelProperty(value = "QQ昵称")
    private String qqNickname;
    @ApiModelProperty(value = "QQ头像")
    private String qqAvatar;
    @ApiModelProperty(value = "QQ的OpenId")
    private String qqOpenId;
    @ApiModelProperty(value = "微信昵称")
    private String wechatNickname;
    @ApiModelProperty(value = "微信头像")
    private String wechatAvatar;
    @ApiModelProperty(value = "微信的OpenId")
    private String wechatOpenId;
    @ApiModelProperty(value = "微信的UnionId")
    private String wechatUnionId;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
