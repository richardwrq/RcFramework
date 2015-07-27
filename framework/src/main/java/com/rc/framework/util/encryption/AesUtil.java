package com.rc.framework.util.encryption;

import android.util.Log;

import java.io.UnsupportedEncodingException;
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
 * Description: Aes加解密
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-03-10 11:08
 */
public class AesUtil {

    public static final String TAG = "AesUtil";
    public static final String CHARSET = "UTF-8";
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * @param strKey     key
     * @param strIvKey   矢量key
     * @param strContent 要解密的内容
     * @return 加密后的字符串，失败返回null
     */
    public static String encode(String strKey, String strIvKey, String strContent) {

        IvParameterSpec zeroIv = new IvParameterSpec(strIvKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
        Cipher cipher;
        byte[] encryptedData = null;

        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            encryptedData = cipher.doFinal(strContent.getBytes(CHARSET));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            Log.e(TAG, "无效的矢量key");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.e(TAG, "无效的key");
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (encryptedData == null) {
            return null;
        }
        return Base64Util.encode(encryptedData);
    }

    /**
     * @param strKey     key
     * @param strIvKey   矢量key
     * @param strContent 要解密的内容
     * @return 解密后的字符串，失败返回null
     */
    public static String decode(String strKey, String strIvKey, String strContent) {

        byte[] byteMi = Base64Util.decode(strContent);
        IvParameterSpec zeroIv = new IvParameterSpec(strIvKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
        Cipher cipher;
        byte[] decodedData = null;

        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            decodedData = cipher.doFinal(byteMi);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            Log.e(TAG, "无效的矢量key");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.e(TAG, "无效的key");
        }

        String result = null;
        if (decodedData == null) {
            return null;
        }
        try {
            result = new String(decodedData, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "无法把解密后的字符数组转化为" + CHARSET + "字符串");
        }

        return result;
    }

}
