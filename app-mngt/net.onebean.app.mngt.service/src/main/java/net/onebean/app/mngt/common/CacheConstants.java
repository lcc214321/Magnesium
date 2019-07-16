package net.onebean.app.mngt.common;

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
		/**接口应用对应关系*/
		APP_API_RELATION("UAG:OPENAPI:APP_API_RELATION"),
		URI_API_RELATION("UAG:OPENAPI:URI_API_RELATION"),
		APP_INFO("UAG:OPENAPI:APP_INFO"),
		IP_ADDRESS_WHITE_LIST("UAG:OPENAPI:IP_ADDRESS_WHITE_LIST"),
		;
		private String value;
		
		private UagScopeKeys(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
		
	}


}
