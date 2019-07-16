--[[
 	业务公用lua，此方法打印日志时候不允许加上ngx.ctx.RequestIdLog标识
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

local stringUtil = require("util.stringUtil");
local tableUtil = require("util.tableUtil");
local webUtil = require("util.webUtil");
-- 配置文件模块
local config = require("comm.config");
-- 常量lua
local constants = require("comm.constants");
local jsonUtil=require("util.jsonUtil");

local comm = {};

-- 操作成功
function comm.success()
	ngx.log(ngx.DEBUG, "SECCESS!!!");
	ngx.exit(200);
end

function comm.returnOptionReq()
	ngx.log(ngx.DEBUG, "SECCESS!!!");
	local ajaxTable = {};
	ngx.print(jsonUtil.encode(ajaxTable));
	ngx.exit(204);
end

-- 没有权限，直接跳转到没有权限页面
function comm.noAuthPage(errorMessage)
	if (stringUtil.isBlank(errorMessage)) then
		--errorMessage = "请求没有权限";
		errorMessage = "request no auth,status is 401!";
	end
	ngx.log(ngx.ERR, errorMessage);
	if (ngx.ctx.isAjax) then
		local ajaxTable = {};
		ajaxTable["code"] = 999;
		ajaxTable["message"]="您目前无权限操作，服务器拒绝处理您的请求";
		ajaxTable["datas"] = {};
		webUtil.setRespHeader("Content-Type", "application/json");
		ngx.print(jsonUtil.encode(ajaxTable));
		ngx.exit(200);
	else
		ngx.exit(401);
	end

end

-- 没有对应页面，直接跳转到没有页面提示页面
function comm.noAccessPage(errorMessage)
	if (stringUtil.isBlank(errorMessage)) then
		--errorMessage = "不存在访问的页面";
		errorMessage = "request page is not exit,status is 404!";
	end
	ngx.log(ngx.ERR, errorMessage);
	ngx.exit(404);
end

	-- 程序错误，直接跳转到错误页面
function comm.errorPage(errorMessage)	
	if (ngx.ctx.isAjax) then
		if (stringUtil.isBlank(errorMessage)) then
    		errorMessage = "程序出现错误，ajax方式直接跳转到错误处理页面";
    	end
    	ngx.log(ngx.ERR, errorMessage);
		webUtil.setRespHeader("errorMessage", stringUtil.encode(errorMessage));
	
		local ajaxTable = {};
		ajaxTable["code"] = 999;
		ajaxTable["message"]="fail";		
		local datasTable = {};
		ajaxTable["datas"] = datasTable;
		ngx.print(jsonUtil.encode(ajaxTable));
		ngx.exit(200);
	else
		if (stringUtil.isBlank(errorMessage)) then
    		errorMessage = "程序出现错误，非ajax方式直接跳转到错误处理页面";
    	end
    	ngx.log(ngx.ERR, errorMessage);
    	webUtil.setRespHeader("errorMessage", stringUtil.encode(errorMessage));
    	
		--ngx.exec("/uag/error/500.html");
		ngx.exit(500);
	end
end

-- 应用场景：比如innerapi、openapi等，调用方一般通过httpclient方式进行调用；
--	参数：必填，而且必须是英文错误信息，这样利于查看错误情况
function comm.errorStatusCommon(errorMessage)	
	if (stringUtil.isNotBlank(errorMessage)) then
		ngx.log(ngx.ERR, errorMessage);
		webUtil.setRespHeader("errorMessage", stringUtil.encode(errorMessage));
		webUtil.setRespHeader("easyLookErrorMessage", errorMessage);
	else 
		ngx.log(ngx.ERR, "--------------程序出错，errorStatusCommon方法必须传递错误信息参数--------------");
	end
	webUtil.setRespHeader("Content-Type", "text/plain;charset=utf-8");
	ngx.exit(481);
end

-- 返回相应结果,一般用于返回json串，执行此方法以后直接停止执行
function comm.print(content)
	ngx.print(content);
	ngx.exit(ngx.HTTP_FORBIDDEN);
end

-- 重定向到指定页面，支持ajax方式
function comm.redirect(redirectUrl)	
	if (ngx.ctx.isAjax) then
		ngx.log(ngx.DEBUG, "ajax方式跳转到[" .. redirectUrl .. "]");
		local ajaxTable = {};
		ajaxTable["code"] = 0;
		ajaxTable["message"]="successs";		
		local datasTable = {};
		datasTable["WEC-HASLOGIN"] = false;
		--datasTable["redirectUrl"] = redirectUrl;
		-- ajax的时候返回的redirectUrl为固定地址
		datasTable["WEC-REDIRECTURL"] = "/portal/login";
		ajaxTable["datas"] = datasTable;
		webUtil.setRespHeader("Content-Type", "application/json");
		ngx.print(jsonUtil.encode(ajaxTable));
		ngx.exit(200);
	else
		ngx.log(ngx.DEBUG, "非ajax方式跳转到[" .. redirectUrl .. "]");
		ngx.redirect(redirectUrl, 302); 
	end
end

-- 获取redis配置并以table的形式返回
function comm.getRedisConfig()
	local redisConfig = {}; 	
    redisConfig["ip"] = config.get(constants.REDIS_IP); 
    redisConfig["port"] = config.get(constants.REDIS_PORT); 
    redisConfig["timeout"] = config.get(constants.REDIS_TIMEOUT); 
    redisConfig["password"] = config.get(constants.REDIS_PASSWORD); 
    redisConfig["poolSize"] = config.get(constants.REDIS_POOL_SIZE); 
    redisConfig["maxIdleTime"] = config.get(constants.REDIS_MAX_IDLE_TIME); 
    redisConfig["select"] = config.get(constants.REDIS_SELECT); 
    return redisConfig;
end

-- 获取mysql配置并以table的形式返回
function comm.getMysqlConfig()
	local mysqlConfig = {}; 	
    mysqlConfig["host"] = config.get(constants.MYSQL_HOST); 
    mysqlConfig["port"] = config.get(constants.MYSQL_PORT); 
    mysqlConfig["timeout"] = config.get(constants.MYSQL_TIMEOUT);
    mysqlConfig["poolSize"] = config.get(constants.MYSQL_POOL_SIZE); 
    mysqlConfig["maxIdleTime"] = config.get(constants.MYSQL_MAX_IDLE_TIME); 
    return mysqlConfig;
end


-- 获取mysql配置并以table的形式返回
function comm.getMysqlConfig()
	local mysqlConfig = comm.getMysqlConfig();
	mysqlConfig["database"] = config.get(constants.MYSQL_DATABASE);
	mysqlConfig["user"] = config.get(constants.MYSQL_USER);
	mysqlConfig["password"] = config.get(constants.MYSQL_PASSWORD);
	return mysqlConfig;
end

-- 判断请求是否是mobile
function comm.isMobile()
	return ngx.ctx.isMobile;
end

-- 判断是否是移动端
-- 此方法暂时职位clearAndSet用，所以其他地方都调用comm.isMobile()方法
function comm.isMobileForInit()
	local isMobile = false;
    local userAgent = webUtil.getReqHeader(constants.USER_AGENT);
	local mobileUserAgent = config.get(constants.MOBILE_USER_AGENT);
	if (stringUtil.isBlank(mobileUserAgent)) then
		ngx.log(ngx.ERR, "config.json中mobile_user_agent配置不存在，请确认");
		return isMobile;
	end
	
	local mobileUserAgentArr = stringUtil.split(mobileUserAgent, ",");	
	for _, tempVal in pairs(mobileUserAgentArr) do
		if (stringUtil.contains(userAgent, tempVal)) then
			isMobile = true;
			break;
		end
	end
	ngx.log(ngx.DEBUG, "是否移动端访问:" .. stringUtil.toStringTrim(isMobile));
	return isMobile;
end

-- 若结尾没有/,则补充/
function comm.completeWithSlash(str)
	if (stringUtil.endsWith(str, "/")) then
		return str;
	else 
		return str .. "/";
	end		
end

--获取当前请求地址的主域名，比如http://cloud.onebean.com/portal/index.html则返回http://cloud.onebean.com
function comm.getCurrentDomain()
	local rootUrl;
	-- 域名
	local domain = webUtil.getHost();

	local urlPort = webUtil.getServerPort();
	if (urlPort == nil or urlPort == "80" or urlPort == "443") then
		rootUrl = webUtil.getScheme() .. "://" .. domain ;
	else
		rootUrl = webUtil.getScheme() .. "://".. domain .. ":" .. urlPort;
	end

	return rootUrl;
end

--获取当前请求地址（不带参数），比如http://cloud.onebean.com/portal/index.html
function comm.getCurrentUrl()
	return comm.getCurrentDomain()..webUtil.getUri();
end

-- 获取被请求应用的url地址
-- sourceUrl、redirectUrl区别是redirectUrl中去除ticket参数
-- 尽量不要使用redirectUrl,因为参数存在特殊字符时候会丢失部分数据
-- 比如http://cloud.onebean.com/portal/xxxxx?aa=b#c，会丢失#c
function comm.getAppUrlOld()
	local sourceUrl;
	local redirectUrl;
	local urlPort = webUtil.getServerPort();

	if (urlPort == nil or urlPort == "80" or urlPort == "443") then
		sourceUrl = webUtil.getScheme() .. "://" .. webUtil.getHost() .. webUtil.getRequestUri();
		redirectUrl = webUtil.getScheme().."://"..webUtil.getHost()..webUtil.getUri();
	else
		sourceUrl = webUtil.getScheme() .. "://"..webUtil.getHost() .. ":" .. urlPort .. webUtil.getRequestUri();
		redirectUrl = webUtil.getScheme() .. "://"..webUtil.getHost() .. ":" .. urlPort .. webUtil.getUri();
	end	
	redirectUrl = redirectUrl .. "?";
	
	local reqUriArgs = webUtil.getUriArgs();
	local tempArgs = {};
    if(not tableUtil.isEmpty(reqUriArgs)) then
        for key, val in pairs(reqUriArgs) do
        	table.insert(tempArgs, key)
        end
        table.sort(tempArgs);  
        
        for key, val in pairs(tempArgs) do
            if type(val)=="string" and val ~= constants.TICKET then
            	local paramVal = reqUriArgs[val];
            	if (type(paramVal)=="string") then
            		redirectUrl = redirectUrl .. val .. "=".. stringUtil.encode(paramVal) .. "&";
            	elseif (type(paramVal)=="table") then
            		for tempKey, tempVar in pairs(paramVal) do
            			redirectUrl = redirectUrl .. val .. "=".. stringUtil.encode(tempVar) .. "&";
            		end
            	else
            		ngx.log(ngx.ERR, "参数"..val .. "存在异常");
            	end                
            end
        end	      
    end    
	redirectUrl = string.sub(redirectUrl, 0, string.len(redirectUrl) - 1);
	
	return sourceUrl,redirectUrl;
end

-- 获取被请求应用的url地址
-- sourceUrl、redirectUrl区别是redirectUrl中去除ticket参数
-- 尽量不要使用redirectUrl,因为参数存在特殊字符时候会丢失部分数据
-- 比如http://cloud.onebean.com/portal/xxxxx?aa=b#c，会丢失#c
function comm.getAppUrl(wipeOffParam)
	local sourceUrl;
	local redirectUrl;
	local urlPort = webUtil.getServerPort();

	if (urlPort == nil or urlPort == "80" or urlPort == "443") then
		sourceUrl = webUtil.getScheme() .. "://" .. webUtil.getHost() .. webUtil.getRequestUri();
		redirectUrl = webUtil.getScheme().."://"..webUtil.getHost()..webUtil.getRequestUri();
	else
		sourceUrl = webUtil.getScheme() .. "://"..webUtil.getHost() .. ":" .. urlPort .. webUtil.getRequestUri();
		redirectUrl = webUtil.getScheme() .. "://"..webUtil.getHost() .. ":" .. urlPort .. webUtil.getRequestUri();
	end	
	
	if (stringUtil.isBlank(wipeOffParam)) then
		return sourceUrl,redirectUrl;
	end
	
	local startIndex, endIndex = string.find(redirectUrl, "?"..wipeOffParam.."=", 0, true);
    if (startIndex == nil) then
    	startIndex, endIndex = string.find(redirectUrl, "&"..wipeOffParam.."=", 0, true);
    end
    
    if (startIndex ~= nil) then
    	redirectUrl = string.sub(redirectUrl, 0, startIndex-1);
    end
	
	return sourceUrl,redirectUrl;
end

-- 成功并返回json
function comm.successWithResp()
	local ajaxTable = {};
	ajaxTable["code"] = 0;
	ajaxTable["message"]="";
	ajaxTable["datas"] = {};
	ngx.print(jsonUtil.encode(ajaxTable));
	ngx.exit(200);
end

-- openApi  失败返回错误json
function comm.error4OpenApiWithCode(errorMessage,errCode)
	ngx.log(ngx.ERR, errorMessage);
	webUtil.setRespHeader("errorMessage", stringUtil.encode(errorMessage));
	local ajaxTable = {};
	ajaxTable["errCode"] = errCode;
	ajaxTable["errMsg"]=errorMessage;
	ajaxTable["datas"]={};
	webUtil.setRespHeader("Content-Type", "application/json");
	ngx.print(jsonUtil.encode(ajaxTable));
	ngx.exit(200);
end

-- openApi  失败返回错误json
function comm.error4OpemApiv2(errorMessage)
	ngx.log(ngx.ERR, errorMessage);
	webUtil.setRespHeader("errorMessage", stringUtil.encode(errorMessage));
	local ajaxTable = {};
	ajaxTable["errCode"] = "999";
	ajaxTable["errMsg"]=errorMessage;
	ajaxTable["datas"]={};
	webUtil.setRespHeader("Content-Type", "application/json");
	ngx.print(jsonUtil.encode(ajaxTable));
	ngx.exit(200);
end

return comm;

