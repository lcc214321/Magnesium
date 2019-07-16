package net.onebean.app.mngt.common;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 0neBean
 * 密码生成器
 */
public class PassswordGetter {
    /**
     * 生成密码
     * @param length 密码长度
     * @return 返回密码
     */
    public static String generatePassword (Integer length){
        StringBuffer password = new StringBuffer();
        int min =33;
        int max =126;
        char start = 33;
        String test = "[A-Za-z0-9\\-]*";
        Matcher m;
        while (password.length() != length){
            start = (char) getRandom(max, min);
            m = Pattern.compile(test).matcher(start + "");
            if (m.find() && (start != 34 && start != 92)) {
                password.append(start);
            }
        }
       return password.toString();
    }


    private static int getRandom(int max,int min) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

}
