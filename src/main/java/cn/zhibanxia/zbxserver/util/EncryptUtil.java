package cn.zhibanxia.zbxserver.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedHashMap;

/**
 * Created by zzy on  2018/10/02 21:46
 */
public class EncryptUtil {

    private static final String CIPHER_NAME = "Blowfish/CFB8/NoPadding";
    private static final String KEY_SPEC_NAME = "Blowfish";

    private static final ThreadLocal<LinkedHashMap<String, Pair<Cipher, Cipher>>> pool = new ThreadLocal<>();

    private EncryptUtil() {
    }

    /**
     * 加密
     *
     * @param str 需要加密的字符串
     * @param key 加解密用的密钥
     * @return
     */
    public static String encrypt(String str, String key) {
        Cipher enCipher = getInstance(key).getKey();
        String result = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                byte[] encrypted = enCipher.doFinal(str.getBytes());
                result = Base58.encode(encrypted);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return result;
    }

    /**
     * 解密
     *
     * @param str 需要解密的字符串
     * @param key 加解密用的密钥
     * @return
     */
    public static String decrypt(String str, String key) {
        Cipher deCipher = getInstance(key).getValue();
        String result = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                byte[] decrypted = Base58.decode(str);
                result = new String(deCipher.doFinal(decrypted));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return result;
    }

    //缓存起来加快下次访问速度
    private static Pair<Cipher, Cipher> getInstance(String key) {
        LinkedHashMap<String, Pair<Cipher, Cipher>> keyMap = pool.get();
        if (keyMap == null || keyMap.isEmpty()) {
            keyMap = new LinkedHashMap<>();
            pool.set(keyMap);
        }
        Pair<Cipher, Cipher> instance = keyMap.get(key);
        if (instance == null) {
            String iv = StringUtils.substring(DigestUtils.md5Hex(key), 0, 8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), KEY_SPEC_NAME);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            try {
                Cipher enCipher = Cipher.getInstance(CIPHER_NAME);
                Cipher deCipher = Cipher.getInstance(CIPHER_NAME);
                enCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
                deCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
                instance = Pair.of(enCipher, deCipher);
            } catch (Exception e) {
                throw new IllegalStateException("初始化失败", e);
            }
            keyMap.put(key, instance);
        }
        return instance;
    }

}

