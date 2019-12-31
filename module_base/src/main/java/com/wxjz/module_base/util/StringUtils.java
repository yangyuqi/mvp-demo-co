package com.wxjz.module_base.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

public class StringUtils {

    public static int count(String str) {
//		int singelC = 0, doubleC = 0;
//		String s = "[^\\x00-\\xff]";
//		Pattern pattern = Pattern.compile(s);
//		Matcher ma = pattern.matcher(str);
//
//		while (ma.find()) {
//			doubleC++;
//		}
//		singelC = str.length() - doubleC;
//		if (singelC % 2 != 0) {
//			doubleC += (singelC + 1) / 2;
//		} else {
//			doubleC += singelC / 2;
//		}
//
//		return doubleC;
        return str.length();
    }

    public static String replacePhoneNo(String str) {
        // 必须为11位手机号
        if (str == null || str.length() != 11) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (i >= 3 && i <= 6)
                sb.append("*");
            else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public final static String formatFloat(float value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }

    public final static boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)
                || TextUtils.isEmpty(value.trim())
                || "null".equals(value)
                || "NULL".equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

}
