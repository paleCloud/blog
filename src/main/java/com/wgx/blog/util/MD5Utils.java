package com.wgx.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Pale language
 * @Description: 密码加密
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */


public class MD5Utils {
    public static String code(String str) {
        /**
         * md5加密类
         */
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            int i;
            StringBuffer sb = new StringBuffer("");
            for (int offset = 0; offset < digest.length; offset++) {
                i=digest[offset];
                if(i<0){
                    i +=256;
                }
                if(i<16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            //32位加密
            return sb.toString();
            //16位加密
            //return sb.toString().substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("123456"));
    }
}
