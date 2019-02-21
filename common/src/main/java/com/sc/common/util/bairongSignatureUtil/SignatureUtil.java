package com.sc.common.util.bairongSignatureUtil;


import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static tk.mybatis.mapper.util.StringUtil.isEmpty;
import static tk.mybatis.mapper.util.StringUtil.isNotEmpty;

/**
 * 签名加密工具类-简化版
 */
public class SignatureUtil {
    private static final String charset = "utf-8";
    private static final String signTypeSHA1 = "SHA1withRSA";
    private static final String signTypeSHA256 = "SHA256withRSA";
    private static final String signTypeMD5 = "MD5withRSA";
    // AES_256_cbc pkcs77
    private static final String algorithm = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param signParams
     * @return
     */
    public static String signMD5(Map<String, String> signParams) {
        String content = getSignContent(signParams);
        return signMD5(content);
    }

    public static String signSHA1(Map<String, String> signParams) {
        String content = getSignContent(signParams);
        return signSHA1(content);
    }

    public static String signSHA256(Map<String, String> signParams) {
        String content = getSignContent(signParams);
        return signSHA256(content);
    }

    public static String signMD5(String content) {
        return DigestUtils.md5Hex(content);
    }

    public static String signSHA1(String content) {
        return DigestUtils.sha1Hex(content);
    }

    public static String signSHA256(String content) {
        return DigestUtils.sha256Hex(content);
    }

    /**
     * 加签
     *
     * @param signParams
     * @param privateKey
     * @return
     */
    public static String signMD5(Map<String, String> signParams, String privateKey) {
        return initSign(signTypeMD5, signParams, privateKey);
    }

    public static String signSHA1(Map<String, String> signParams, String privateKey) {
        return initSign(signTypeSHA1, signParams, privateKey);
    }

    public static String signSHA256(Map<String, String> signParams, String privateKey) {
        return initSign(signTypeSHA256, signParams, privateKey);
    }

    /**
     * 根据类型生成签名
     *
     * @param signType
     * @param signParams
     * @param privateKey
     * @return
     */
    private static String initSign(String signType, Map<String, String> signParams, String privateKey) {
        try {
            String content = getSignContent(signParams);
            PrivateKey priKey = getPrivateKeyFromPKCS8(privateKey);
            java.security.Signature signature = java.security.Signature.getInstance(signType);
            signature.initSign(priKey);
            if (isEmpty((charset))) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            System.out.println("生成签名异常: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 验证签名
     *
     * @param signParams
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean checkSignMD5(Map<String, String> signParams, String sign, String publicKey) {
        return rsa256CheckContent(signTypeMD5, signParams, sign, publicKey);
    }

    public static boolean checkSignSHA1(Map<String, String> signParams, String sign, String publicKey) {
        return rsa256CheckContent(signTypeSHA1, signParams, sign, publicKey);
    }

    public static boolean checkSignSHA256(Map<String, String> signParams, String sign, String publicKey) {
        return rsa256CheckContent(signTypeSHA256, signParams, sign, publicKey);
    }

    /**
     * 根据加签类型验证签名
     *
     * @param signType
     * @param signParams
     * @param sign
     * @param publicKey
     * @return
     */
    private static boolean rsa256CheckContent(String signType, Map<String, String> signParams, String sign, String publicKey) {
        try {
            String content = getSignContent(signParams);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodeKey = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodeKey));
            java.security.Signature signature = java.security.Signature.getInstance(signType);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));
            boolean bverify = signature.verify(Base64.getDecoder().decode(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * RSA - 公钥加密过程
     *
     * @param params
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static String encryptRSA(Map<String, String> params, String pubKey) throws Exception {
        if (isEmpty(pubKey)) {
            throw new Exception("加密公钥为空，请设置");
        }
        try {
            String context = getSignContent(params);
            PublicKey publicKey = getPublicKeyFromX509(pubKey);
            Cipher cipher;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(context.getBytes());
            return Base64.getEncoder().encodeToString(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * RSA - 私钥解密过程
     *
     * @param context
     * @param prikey
     * @return
     * @throws Exception
     */
    public static String decryptRSA(String context, String prikey) throws Exception {
        if (isEmpty(prikey)) {
            throw new Exception("解密私钥为空，请设置");
        }
        PrivateKey privateKey = getPrivateKeyFromPKCS8(prikey);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(Base64.getDecoder().decode(context));
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("key非法");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥对象生成
     *
     * @param pubKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String pubKey) {
        PublicKey publicKey = null;
        if (isEmpty(pubKey)) {
            return publicKey;
        }
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(pubKey)
            );
            KeyFactory keyFactory;
            keyFactory = KeyFactory.getInstance("RSA");
            // 取公钥对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取公钥对象失败: " + e.getMessage());
        }
        return publicKey;
    }

    /**
     * 私钥对象生成
     *
     * @param priKey
     * @return
     */
    private static PrivateKey getPrivateKeyFromPKCS8(String priKey) {
        PKCS8EncodedKeySpec priPKCS8;
        PrivateKey privateKey = null;
        if (isEmpty(priKey)) {
            return privateKey;
        }
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(priPKCS8);
        } catch (Exception e) {
            System.out.println("私钥解析错误: " + e.getMessage());
        }
        return privateKey;
    }

    /**
     * 参数格式转换 Map转String
     *
     * @param signParams
     * @return
     */
    private static String getSignContent(Map<String, String> signParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(signParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = signParams.get(key);
            if (isNotEmpty(key) && isNotEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }
}
