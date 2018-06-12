package com.kingguanzhang.dealsites.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
            //生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
			generator.init(secureRandom);
            //生成密钥对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getEncryptString(String str) {
        //基于BASE64位编码,用于在以后接受byte[]并转换成String
        Base64 base64encoder = new Base64();
		try {
            //将字符串转成UTF-8编码的字节数组
			byte[] bytes = str.getBytes(CHARSETNAME);
            //获取加密对象的实例
			Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化加密策略
			cipher.init(Cipher.ENCRYPT_MODE, key);
            //将字节数组加密
			byte[] doFinal = cipher.doFinal(bytes);
            //将已经加密的字节数组转成字符串并返回
			return base64encoder.encodeAsString(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

    //获取解密之后的信息
	public static String getDecryptString(String str) {
        Base64 base64decoder = new Base64();
		try {
            //将字符串转成字节数组
			byte[] bytes = base64decoder.decode(str);
            //获取解密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化解密策略
			cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
			byte[] doFinal = cipher.doFinal(bytes);
            //将解密后的信息转成字符串并返回
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getEncryptString("root"));
		System.out.println(getEncryptString("123456"));
	}

}