package com.greg.lab8.client.util;

import com.greg.lab8.common.util.data.Organization;

import java.util.Locale;

public class LocaleHolder {
    private  static Locale locale = null;

    private LocaleHolder(){

    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        LocaleHolder.locale = locale;
    }
}
