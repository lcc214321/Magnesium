package net.onebean.user.mngt.common;

import net.onebean.util.StringUtils;

/**
 * 门户redis缓存使用到的key
 * 
 * @author 0neBean
 *
 */
public class CacheConstants {

	/***
	 * 
	 * @author 0neBean
	 * 以uag为单位存储的缓存的keys
	 */
	public static enum UagScopeKeys {
		APP_INFO("UAG:OPENAPI:APP_INFO"),
		LOGIN_SMS_CODE("UAG:OPENAPI:LOGIN:SMS:CODE"),
		UAG_LOGIN_FLAG_DEVICETOKEN_KEY("UAG:OPENAPI:LOGIN:FLAG:DEVICETOKEN"),
		UAG_LOGIN_FLAG_ACCOUNT_KEY("UAG:OPENAPI:LOGIN:FLAG:ACCOUNT")
		;
		private String value;
		
		private UagScopeKeys(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
		
	}

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
