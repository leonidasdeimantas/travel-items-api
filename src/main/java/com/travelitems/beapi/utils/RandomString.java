package com.travelitems.beapi.utils;

import java.security.SecureRandom;

public class RandomString {
    static final String seed = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String get(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(seed.charAt(rnd.nextInt(seed.length())));
        return sb.toString();
    }
}
