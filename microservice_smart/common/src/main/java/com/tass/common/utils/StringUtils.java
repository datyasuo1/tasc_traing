package com.tass.common.utils;

public class StringUtils {

    public static String getConditionTrimValue(final String value) {
        if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return null;
        } else {
            return value.trim();
        }
    }

    public static String getConditionTrimValueAdd(final String value) {
        if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return null;
        } else {
            return value.trim();
        }
    }
}
