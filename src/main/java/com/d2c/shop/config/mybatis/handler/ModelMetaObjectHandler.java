package com.d2c.shop.config.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.d2c.shop.common.api.constant.FieldConstant;
import com.d2c.shop.common.utils.SpringUtil;
import com.d2c.shop.config.security.context.LoginKeeperHolder;
import com.d2c.shop.config.security.context.LoginMemberHolder;
import com.d2c.shop.config.security.context.LoginUserHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author BaiCai
 */
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = this.getFieldValByName(FieldConstant.CREATE_DATE, metaObject);
        if (null == createDate) {
            this.setFieldValByName(FieldConstant.CREATE_DATE, new Date(), metaObject);
        }
        Object createMan = this.getFieldValByName(FieldConstant.CREATE_MAN, metaObject);
        if (null == createMan) {
            this.setFieldValByName(FieldConstant.CREATE_MAN, getLoginName(), metaObject);
        }
        Object deleted = this.getFieldValByName(FieldConstant.DELETED, metaObject);
        if (null == deleted) {
            this.setFieldValByName(FieldConstant.DELETED, new Integer(0), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifyDate = this.getFieldValByName(FieldConstant.MODIFY_DATE, metaObject);
        this.setFieldValByName(FieldConstant.MODIFY_DATE, new Date(), metaObject);
        Object modifyMan = this.getFieldValByName(FieldConstant.MODIFY_MAN, metaObject);
        if (null == modifyMan) {
            this.setFieldValByName(FieldConstant.MODIFY_MAN, getLoginName(), metaObject);
        }
    }

    private String getLoginName() {
        String uri = request.getRequestURI();
        if (uri.startsWith("/back")) {
            return SpringUtil.getBean(LoginUserHolder.class).getUsername();
        } else if (uri.startsWith("/b_api")) {
            return SpringUtil.getBean(LoginKeeperHolder.class).getLoginAccount();
        } else if (uri.startsWith("/c_api")) {
            return SpringUtil.getBean(LoginMemberHolder.class).getLoginAccount();
        }
        return null;
    }

}
