package com.sc.common.util.bairongSignatureUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CreateKeys {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 获得公钥
     *
     * @param keyMap
     * @return
     */
    private static String getPublicKey(Map<String, Object> keyMap) {
        //获得map中的公钥对象  转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     */
    private static String getPrivateKey(Map<String, Object> keyMap) {
        //获取map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 编码返回字符串
     *
     * @param key
     * @return
     */
    private static String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * 初始化公私钥 map对象中存放公私钥
     *
     * @return
     * @throws Exception
     */
    private static Map<String, Object> initKey() throws Exception {
        // 获得对象 KeyPairGenerator 参数 RSA 1024字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        // 通过对象KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 通过对象 KeyPair 获取RSA公私钥对象 RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 生成RSA
     *
     * @return
     */
    public static Map<String, String> getRSAKey() {
        Map<String, Object> keyMapObj;
        Map<String, String> keyMap = new HashMap<String, String>();

        try {
            keyMapObj = initKey();
            String publicKey = getPublicKey(keyMapObj);
            keyMap.put("pubKey", publicKey);
            String privateKey = getPrivateKey(keyMapObj);
            keyMap.put("priKey", privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成公私钥异常: " + e.getMessage());
            return null;
        }
        return keyMap;
    }

    public static String getAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //要生成多少位 只需要修改这里即可 支持128/192/256
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytes = secretKey.getEncoded();
            return parseByte2HexStr(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("生成AESKEY异常: " + e.getMessage());
        }
        return "";
    }

    /**
     * 将二进制 转换成 16进制
     *
     * @param bytes
     * @return
     */
    private static String parseByte2HexStr(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringBuffer.length(); i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuffer.append(hex.toUpperCase());
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        // RSAKey
        Map map = CreateKeys.getRSAKey();
        String pubKey = map.get("pubKey").toString();
        String priKey = map.get("priKey").toString();

        //AESKey
        String aesKey = CreateKeys.getAESKey();

        System.out.println("pubKey: " + pubKey);
        System.out.println("priKey: " + priKey);
        System.out.println("aesKey: " + aesKey);

    }

}
