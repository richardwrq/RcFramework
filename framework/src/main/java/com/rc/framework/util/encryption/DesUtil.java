package com.rc.framework.util.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description: Des加解密
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-03-10 11:08
 */
public class DesUtil {

    public static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";

    /**
     * @param key   Base64过的解密私钥,长度不能够小于8位
     * @param ivKey Base64过的向量key，8位
     * @param data  待加密的字符串
     * @return Base64编码过的Des加密字符串，失败返回null
     */
    public static String encode(String key, String ivKey, String data) {

        return encode(key, ivKey, data.getBytes());
    }

    /**
     * @param key   Base64过的解密私钥,长度不能够小于8位
     * @param ivKey Base64过的向量key，8位
     * @param data  待加密的字节数组
     * @return Base64编码过的Des加密字符串，失败返回null
     */
    public static String encode(String key, String ivKey, byte[] data) {

        byte[] result = null;
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "DES");
        IvParameterSpec ips = new IvParameterSpec(Base64Util.decode(ivKey));

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, sks, ips);
            result = cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return Base64Util.encode(result);
    }

    /**
     * @param key   Base64过的解密私钥,长度不能够小于8位
     * @param ivKey Base64过的向量key，8位
     * @param data  待解密的Base64过的字符串
     * @return 解密后的字节数组，失败返回null
     */
    public static byte[] decode(String key, String ivKey, String data) {

        byte[] result = null;
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "DES");
        IvParameterSpec iv = new IvParameterSpec(Base64Util.decode(ivKey));

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, sks, iv);
            result = cipher.doFinal(Base64Util.decode(data));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param key   Base64过的解密私钥,长度不能够小于8位
     * @param ivKey Base64过的向量key，8位
     * @param data  待解密字符数组
     * @return 解密后的字节数组，失败返回null
     */
    public static byte[] decode(String key, String ivKey, byte[] data) {

        byte[] result = null;
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "DES");
        IvParameterSpec ips = new IvParameterSpec(Base64Util.decode(ivKey));

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, sks, ips);
            result = cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return result;
    }
}
