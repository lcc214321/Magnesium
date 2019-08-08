--[[
	常量lua。
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

local constants = {};

-- begin，配置文件config.json中对应的配置项
constants.REDIS_IP = "redis_ip";
constants.REDIS_PORT = "redis_port";
constants.REDIS_PASSWORD = "redis_password";
constants.REDIS_SELECT = "redis_select";
constants.REDIS_TIMEOUT = "redis_timeout";
constants.REDIS_MAX_IDLE_TIME = "redis_max_idle_time";
constants.REDIS_POOL_SIZE = "redis_pool_size";
constants.IAP_URL = "iap_url";
constants.IAP_DOMAIN_CONTEXT = "iap_domain_context";
constants.USER_EXPIRE = "user_expire";
constants.AUTH_EXPIRE = "auth_expire";
constants.IGNORE_AUTH = "ignore_auth";
constants.ALLOW_IPS = "allow_ips";
constants.WEC_TOKEN = "wec_token";
constants.MOBILE_USER_AGENT = "mobile_user_agent";
constants.MYSQL_HOST = "mysql_host";
constants.MYSQL_PORT = "mysql_port";
constants.MYSQL_TIMEOUT = "mysql_timeout";
constants.MYSQL_POOL_SIZE = "mysql_pool_size";
constants.MYSQL_MAX_IDLE_TIME = "mysql_max_idle_time";
constants.MYSQL_DATABASE = "mysql_database";
constants.MYSQL_USER = "mysql_user";
constants.MYSQL_PASSWORD = "mysql_password";


-- end，配置文件config.json中对应的配置项


-- config.json配置文件路径
constants.configPath = "/usr/program/web/Magnesiumo/uag/conf/config.json";
-- uag标识
constants.UAG = "UAG";
constants.UAG_HOST_ARRAY = "preuag.onebean.cn,uag.onebean.cn";

-- begin，openapi相关内容
constants.OPENAPI_ACCESSTOKEN_KEY = constants.UAG .. ":OPENAPI:ACCESSTOKEN";
constants.OPENAPI_APP_API_RELATION_KEY = constants.UAG .. ":OPENAPI:APP_API_RELATION";
constants.OPENAPI_APP_INFO_KEY = constants.UAG .. ":OPENAPI:APP_INFO";
constants.OPENAPI_APP_API_RELATION = constants.UAG .. ":OPENAPI:APP_API_RELATION";
constants.OPENAPI_URI_API_RELATION = constants.UAG .. ":OPENAPI:URI_API_RELATION";
constants.IP_ADDRESS_WHITE_LIST = constants.UAG .. ":OPENAPI:IP_ADDRESS_WHITE_LIST";

constants.PRIVATETOKEN_KEY = ":PRIVATETOKEN";
constants.OPENAPI_APP_ON_STATUS = "0";
constants.OPENAPI_API_ON_STATUS = "1";
constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE = "0";
constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE = "1";
constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE_AVOID_LOGIN = "3";
constants.OPENAPI_APP_AUTH_TYPE_IP_tOEKN = "2";
constants.OPENAPI_APP_LOGIN_TYPE_SMS = "0";
constants.OPENAPI_APP_LOGIN_TYPE_PASSWORD = "1";
constants.OPENAPI_APP_CATEGORY_CLOUD = "2";
constants.HEADER_APPID_KEY = "EAKAY-APPID";
constants.HEADER_ACCESSTOKEN_KEY = "EAKAY-ACCESSTOKEN";
constants.HEADER_TENANT_ID_KEY = "EAKAY-TENANT-ID";
constants.HEADER_REQUEST_ACCESSTOKEN_KEY = "accessToken";
constants.HEADER_REQUEST_INNER_API_TOKEN_KEY = "staticToken";
constants.HEADER_REQUEST_APP_ID_KEY = "appId";
-- end，openapi相关内容


-- begin，生成设备码相关内容
constants.PLATFORM_TYPE_OF_ANDROID = "android"
constants.PLATFORM_TYPE_OF_ANDROID_CODE = "0"
constants.PLATFORM_TYPE_OF_IOS = "ios"
constants.PLATFORM_TYPE_OF_IOS_CODE = "1"
constants.PLATFORM_TYPE_OF_OTHER = "other"
constants.PLATFORM_TYPE_OF_OTHER_CODE = "2"
-- end，生成设备码相关内容

--[[私有令牌登录标识]]
constants.RS_SALES_LOGIN_FLAG_DEVICETOKEN_KEY = constants.UAG .. ":OPENAPI:LOGIN:FLAG:DEVICETOKEN";
constants.RS_SALES_LOGIN_FLAG_ACCOUNT_KEY = constants.UAG .. ":OPENAPI:LOGIN:FLAG:ACCOUNT";
constants.RS_SALES_LOGIN_SMS_CODE_INFO_KEY = constants.UAG .. ":OPENAPI:LOGIN:SMS:CODE";
constants.RS_SALES_UNLOGIN_ACCESS_WHITELIST_API = constants.UAG .. ":OPENAPI:UNLOGIN:ACCESS:WHITELIST:API";
constants.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_REGISTER = "0";
constants.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN = "1";
constants.HEADER_UAG_AUTH_USER_ID_KEY = "UAG-AUTH-USER-ID";
constants.HEADER_UAG_AUTH_DEVICE_TOKEN = "UAG-AUTH-DEVICE-TOKEN";

--[[appId]]
constants.RS_SALES_APP_ID = "52636034711";
constants.RS_AFTER_SALES_APP_ID = "56350671685";



return constants;