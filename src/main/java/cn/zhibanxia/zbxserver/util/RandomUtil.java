package cn.zhibanxia.zbxserver.util;

import java.util.Random;

/**
 * Created by zzy on  2018/10/04 21:23
 */
public class RandomUtil {

    /**
     * 生成指定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
