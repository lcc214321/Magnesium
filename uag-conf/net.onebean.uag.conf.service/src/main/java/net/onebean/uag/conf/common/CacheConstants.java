package net.onebean.uag.conf.common;

import net.onebean.util.StringUtils;

/**
 * 门户redis缓存使用到的key
 * 
 * @author 0neBean
 *
 */
public class CacheConstants {

	public static final String APP_API_RELATION = "UAG:OPENAPI:APP_API_RELATION";
	public static final String URI_API_RELATION = "UAG:OPENAPI:URI_API_RELATION";
	public static final String APP_INFO = "UAG:OPENAPI:APP_INFO";
	public static final String IP_ADDRESS_WHITE_LIST = "UAG:OPENAPI:IP_ADDRESS_WHITE_LIST";
	public static final String UAG_OPENAPI_UNLOGIN_ACCESS_WHITELIST_API = "UAG:OPENAPI:UNLOGIN:ACCESS:WHITELIST:API";
	public static final String UAG_OPENAPI_LOGIN_SMS_CODE = "UAG:OPENAPI:LOGIN:SMS:CODE";

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
