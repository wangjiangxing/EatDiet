package com.wang.eatdiet.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone {
    public static boolean isPhone(String inputText)
    {
        //判断号码是否正确

        Pattern p= Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m=p.matcher(inputText);
        return m.matches();
    }
}
