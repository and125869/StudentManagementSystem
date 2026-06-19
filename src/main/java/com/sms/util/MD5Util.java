package com.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类 - 密码加密
 */
public class MD5Util {

    /**
     * MD5加密
     */
    public static String encrypt(String plainText) {
        if (plainText == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(plainText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 验证MD5
     */
    public static boolean verify(String plainText, String hash) {
        if (plainText == null || hash == null) return false;
        return hash.equals(encrypt(plainText));
    }
}