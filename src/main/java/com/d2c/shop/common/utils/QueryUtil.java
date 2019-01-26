package com.d2c.shop.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.d2c.shop.common.api.annotation.Condition;
import com.d2c.shop.common.api.base.BaseQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BaiCai
 */
public class QueryUtil {

    // 构建QueryWrapper
    public static <T extends BaseQuery> QueryWrapper buildWrapper(T query) throws IllegalAccessException {
        QueryWrapper<Object> queryWrapper = new QueryWrapper();
        for (Field field : getAllFields(query.getClass())) {
            // 查询条件标签
            Condition annotation = field.getAnnotation(Condition.class);
            // 数据库查询字段
            String key = annotation.field();
            if (StrUtil.isBlank(key)) {
                key = camelToUnderline(field.getName());
            }
            // 数据库查询的值
            Object value = field.get(query);
            Object[] array = new Object[]{};
            if (value != null && value.getClass().isArray()) {
                array = (Object[]) value;
            }
            // 数据库语句片段
            String sql = annotation.sql();
            // 当搜索条件有值时
            switch (annotation.condition()) {
                case EQ:
                    if (value != null) {
                        queryWrapper.eq(key, value);
                    }
                    break;
                case NE:
                    if (value != null) {
                        queryWrapper.ne(key, value);
                    }
                    break;
                case GE:
                    if (value != null) {
                        queryWrapper.ge(key, value);
                    }
                    break;
                case GT:
                    if (value != null) {
                        queryWrapper.gt(key, value);
                    }
                    break;
                case LE:
                    if (value != null) {
                        queryWrapper.le(key, value);
                    }
                    break;
                case LT:
                    if (value != null) {
                        queryWrapper.lt(key, value);
                    }
                    break;
                case LIKE:
                    if (value != null) {
                        queryWrapper.like(key, value);
                    }
                    break;
                case NOT_LIKE:
                    if (value != null) {
                        queryWrapper.notLike(key, value);
                    }
                    break;
                case IN:
                    if (array != null && array.length > 0) {
                        queryWrapper.in(key, array);
                    }
                    break;
                case NOT_IN:
                    if (array != null && array.length > 0) {
                        queryWrapper.notIn(key, array);
                    }
                    break;
                case IS_NULL:
                    if (value != null && (int) value == 1) {
                        queryWrapper.isNull(key);
                    }
                    break;
                case IS_NOT_NULL:
                    if (value != null && (int) value == 1) {
                        queryWrapper.isNotNull(key);
                    }
                    break;
                case EXIST:
                    if (StrUtil.isNotBlank(sql)) {
                        queryWrapper.exists(sql);
                    }
                    break;
                case NOT_EXIST:
                    if (StrUtil.isNotBlank(sql)) {
                        queryWrapper.notExists(sql);
                    }
                    break;
                default:
                    break;
            }
        }
        return queryWrapper;
    }

    // 获取基类和父类所有的字段
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    // 字段名称驼峰转下划线
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
