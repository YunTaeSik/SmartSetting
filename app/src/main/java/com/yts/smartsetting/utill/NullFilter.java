package com.yts.smartsetting.utill;

public class NullFilter {
    public static String check(String text) {
        return text != null ? text : "";
    }

    public static Integer check(Integer integer) {
        return integer != null ? integer : 0;
    }
}
