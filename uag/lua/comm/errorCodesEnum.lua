--  错误码定义
--  @author 0neBean
--  1-4位 1201 代表 onebean-uag
--  异常类型：5～6位，标识数据库、接口、缓存、权限等
-- 	01-参数异常，参数有效性校验错误，在接口的response 的msg中请将校验有问题的参数返回给调用方；
-- 	02-数据库异常，操作数据库异常，log的message中要有sql、参数信息，sql如果能在堆栈中体现，可以不放在message中；
-- 	03-缓存异常，如对redis、JVM缓存操作失败；
-- 	04-权限异常，功能权限、数据权限类异常；
-- 	05-接口异常，接口调用异常，log 的message中要包含接口地址、接口参数信息，如果能在堆栈中体现，可以不放在message中；
-- 	06-业务异常, 业务执行异常， 比如”用户名已存在”，此种类型的异常通常前端要关注；
-- 	07-其它运行异常，如JAVA程序运行时异常；
-- 	08-消息队列异常，如对rabbitMq 的操作失败；
-- 	...
-- 	99-除以上外的异常
-- 	异常序列号：6～10位，每个模块自定义，要求模块范围内唯一性

local errorCodesEnum = {};

errorCodesEnum.success = "0";
--[[01]]
errorCodesEnum.invalid_request = "1201010001";
errorCodesEnum.required_param_empty = "1201010002";
errorCodesEnum.invalid_deviceToken = "1201010003";
errorCodesEnum.invalid_telphone = "1201010004";
--[[02]]
errorCodesEnum.insert_account_failure = "1201020001";
--[[03]]
errorCodesEnum.invalid_logout_failure = "1201030001";
errorCodesEnum.invalid_value_null = "1201030002";
--[[04]]
errorCodesEnum.invalid_accessToken = "1201040001";
errorCodesEnum.invalid_appId = "1201040002";
errorCodesEnum.no_permissions_api_address = "1201040003";
errorCodesEnum.invalid_staticToken = "1201040004";
errorCodesEnum.unlogin_request = "1201040005";
errorCodesEnum.invalid_account = "1201040006";
errorCodesEnum.invalid_host = "1201040007";
--[[05]]
errorCodesEnum.invalid_api_address = "1201050001";
errorCodesEnum.app_unusable = "1201050002";
--[[06]]
errorCodesEnum.invalid_sms_code = "1201060001";
errorCodesEnum.un_know_app_login_strategy = "1201060002";

return errorCodesEnum;