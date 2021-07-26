package com.haley.demo.utils;

public class Secrect {

    /*加密*/
    public static String encryption(String s){
        s = "2020"+s;
        return s;
    }
    /*解密*/
    public  static String decrypt(String s){
        return s.substring(4);
    }
}
