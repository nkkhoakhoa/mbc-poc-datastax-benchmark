package com.pyco.db.benchmark.data.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by pyco on 12/12/16.
 */
public class SystemUtils {

    private SystemUtils() {

    }

    public static final String getSystemProperty(final String name, final String defaultValue) {
        final String value = System.getProperty(name);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

}
