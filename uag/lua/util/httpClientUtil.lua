--[[
  	实现类似httpclient功能，lua-resty-http实现  		
--]]

local httpClientUtil = {};

-- 发起请求,lua_resty_http
function httpClientUtil.request(uri, options, timeout) 
	local http = require "3rd.http";
	local isSuccess, code, bodyResult, errResult; 
	local httpc = http.new();
	if (timeout == nil or timeout == ngx.null) then
		timeout = 10000;
	end
	httpc:set_timeout(timeout);
	local res, err = httpc:request_uri(uri, options);
	if res then
		if res.status == ngx.HTTP_OK then
             isSuccess = true;
             code = ngx.HTTP_OK;
             bodyResult = res.body;
        else
        	isSuccess = false;
            code = res.status;
        end
    else
    	isSuccess = false;
        code = -1;
        errResult = err;
    end
    
	return isSuccess, code, bodyResult, errResult;
end

--[[ capture方式模拟httpclient，只用于内部
	需要在nginx配置文件中添加
	location ~/_uagHttpclientProxy/(.*) {
        internal;
        proxy_pass http://wec-iap-auth/$1$is_args$args;
	}
--]]
function httpClientUtil.capture(uri, options) 
	local isSuccess, code, bodyResult, errResult; 
	local err = "";
	local res = ngx.location.capture("/_uagHttpclientProxy/" .. uri, options);
	if res then
		if res.status == ngx.HTTP_OK then
             isSuccess = true;
             code = ngx.HTTP_OK;
             bodyResult = res.body;
        else
        	isSuccess = false;
            code = res.status;
        end
    else
    	isSuccess = false;
        code = -1;
        errResult = err;
    end
    
	return isSuccess, code, bodyResult, errResult;
end


return httpClientUtil;