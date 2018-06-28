package com.tom.clientmgr.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {
    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMD5Str(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，"+e.toString());
        }
    }

    public static boolean isValidMessageAudio(String msg) {
        int cnt = 0;
        for (int i=0; i<msg.length(); ++i) {
            switch (msg.charAt(i)) {
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
                case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
                    ++ cnt;
                    if (32 <= cnt) return true;
                    break;
                case '/':
                    if ((i + 10) < msg.length()) {// "/storage/"
                        char ch1 = msg.charAt(i+1);
                        char ch2 = msg.charAt(i+8);
                        if ('/' == ch2 && ('s' == ch1 || 'S' == ch1)) return true;
                    }
                default:
                    cnt = 0;
                    break;
            }
        }
        return false;
    }
}
