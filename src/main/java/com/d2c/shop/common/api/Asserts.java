package com.d2c.shop.common.api;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;

/**
 * @author Cai
 */
public class Asserts extends Assert {

    public static void notNull(String msg, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            throw new ApiException(msg);
        }
    }

    public static void eq(Object obj1, Object obj2, String msg) {
        if (!obj1.equals(obj2)) {
            throw new ApiException(msg);
        }
    }

    public static void gt(Integer num1, Integer num2, String msg) {
        if (num1 <= num2) {
            throw new ApiException(msg);
        }
    }

    public static void ge(Integer num1, Integer num2, String msg) {
        if (num1 < num2) {
            throw new ApiException(msg);
        }
    }

}
