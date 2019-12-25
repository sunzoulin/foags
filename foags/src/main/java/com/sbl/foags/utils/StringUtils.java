package com.sbl.foags.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class StringUtils {

    private static final Pattern PATTERN = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");

    /**
     * 判断是否为手机号
     *
     * @param s 手机号
     */
    public static boolean isPhoneNumber(String s) {
        return PATTERN.matcher(s).matches();
    }

    /**
     * 检查字符串是否为空 Create at 2013-6-26
     *
     * @return boolean
     */
    public static boolean isEmpty(String txt) {
        return txt == null || txt.trim().isEmpty() || "null".equalsIgnoreCase(txt);
    }

    /**
     * 检查字符串是否为空 Create at 2013-6-26
     *
     * @return boolean
     */
    public static boolean isNotEmpty(String txt) {
        return !isEmpty(txt);
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符，+86 . + -
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        String allspecial = "[-`~!@#$%^&*()=|{}':;,\\[\\].<>/?！￥…（）—+【】‘；：”“’。，、？86\\s\\t\\n\\r]";
        if (str != null) {
            Pattern p = Pattern.compile(allspecial);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 设置文字变色
     */
    public static void setColorSpan(SpannableString string, int color, int start, int end) {
        // 设置颜色
        string.setSpan(new ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置文字变色
     */
    public static void setColorSpan(SpannableStringBuilder string, int color, int start, int end) {
        // 设置颜色
        string.setSpan(new ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置文字变大
     */
    public static void setNormalSizeSpan(SpannableString string, int size, int start, int end) {
        // 设置颜色
        string.setSpan(new AbsoluteSizeSpan(size),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置文字变大
     */
    public static void setNormalSizeSpan(SpannableStringBuilder string, int size, int start, int end) {
        // 设置颜色
        string.setSpan(new AbsoluteSizeSpan(size),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置文字大小
     */
    public static void setColorSizeSpan(SpannableString string, int size, int color, int start, int end) {
        // 设置垂直居中
        string.setSpan(new CustomVerticalCenterSpan(size, color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    /**
     * 安全的将字符串转 Int 类型，如果转换失败则返回 0
     *
     * @param string 待转换的字符串
     * @return 如果转换失败则返回 0
     */
    public static int str2Int(String string) {
        return str2Int(string, 0);
    }

    /**
     * 安全的将字符串转 Int 类型，如果转换失败则返回 defaultValue
     *
     * @param string       待转换的字符串
     * @param defaultValue 缺省值
     * @return 如果转换失败则返回 0
     */
    private static int str2Int(String string, int defaultValue) {
        if (TextUtils.isEmpty(string)) {
            return defaultValue;
        }
        int temp;
        try {
            temp = Integer.parseInt(string);
        } catch (Exception e) {
            temp = defaultValue;
        }
        return temp;
    }

    /**
     * 判断密码是否只有数字和字母组成
     */
    public static boolean passwordRight(String password) {
        String regex = "^[a-z0-9A-Z]+$";
        return password.matches(regex);
    }

    /**
     * 返回国家，地区，城市不为空的文字
     */
    public static String getLocationText(String nation, String state, String city) {
        if (!StringUtils.isEmpty(city)) {
            return city;
        }
        if (!StringUtils.isEmpty(state)) {
            return state;
        }
        if (!StringUtils.isEmpty(nation)) {
            return nation;
        }
        return "";
    }

    /**
     * 判断是否是中文
     */
    public static boolean isChineseOrEnglish(String content) {
        Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
        Matcher m = p.matcher(content);
        return m.matches();
    }

    /**
     * 把字符串集合拼接成字符串
     */
    public static String convertListToString(Collection<String> list, String split) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(split).append(item);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

}
