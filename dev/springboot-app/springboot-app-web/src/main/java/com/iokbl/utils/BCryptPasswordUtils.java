package com.iokbl.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordUtils {

    private final static BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        System.out.println(getBCryptPassword("123456"));
    }

    public static String getBCryptPassword(String plainText) {
        try {
            if(StringUtils.isNotEmpty(plainText)){
                return bcryptPasswordEncoder.encode(plainText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static boolean matches(String plainText, String hashPass) {
        try {
            if(StringUtils.isNotEmpty(plainText)){
                return bcryptPasswordEncoder.matches(plainText,hashPass);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

}
