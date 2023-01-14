package com.tass.apigw.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    public static String getValueFromHeader(HttpServletRequest request, String header) {
        String value = request.getHeader(header);

        if (StringUtils.isBlank(value)) return null;

        return value;
    }

}
