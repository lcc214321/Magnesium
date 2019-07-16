package net.onebean.api.adapter.enumModel;

import net.onebean.util.StringUtils;

public class CacheConstants {

    public static final String ACCESSTOKEN_KEY = "UAG:OPENAPI:ACCESSTOKEN";
    public static final String RS_SALES_PRIVATE_TOKEN_KEY = "PRIVATETOKEN";


    /**
     * 生成key
     * @param keys keys
     * @return key
     */
    public static String generateKey(String ...keys){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            if(StringUtils.isNotEmpty(key)){
                stringBuilder.append(key).append(":");
            }
        }
        String key = stringBuilder.toString();
        return key.substring(0,key.length()-1);
    }

}
