package com.longruan.mobile.appframe.utils;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * <dl>  Class Description
 * <dd> 项目名称：mea
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2017/12/1
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */

public class AESEnconder {

    public static String encode(String content, byte[] salt) {
        String encodeStr = "";
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
//            keygen.init(128, new SecureRandom(SALT.getBytes()));
            SecureRandom secureRandom;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            } else {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            }
            secureRandom.setSeed(salt);
            keygen.init(128, secureRandom);

            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
//            byte[] raw = original_key.getEncoded();
            byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES生成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串

            // 11.将字符串返回
//            encodeStr = new BASE64Encoder().encode(byte_AES);
            encodeStr = Base64.encodeToString(byte_AES,Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        // 如果有错就返加null JeC7XS5VBuhq3v4atFl8Tg==
        return encodeStr;
    }

    public static String decode(String content, byte[] salt) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            } else {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            }
            secureRandom.setSeed(salt);
            keygen.init(128, secureRandom);

            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
//            byte[] raw = original_key.getEncoded();
            byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
//			byte[] byte_content = Base64.getDecoder().decode(content);
//            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            byte[] byte_content = Base64.decode(content,Base64.NO_WRAP);

            /*
             * 解密
			 */
            byte[] byte_decode = cipher.doFinal(byte_content);
            return new String(byte_decode, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        // 如果有错就返加nulll
        return null;
    }
}
