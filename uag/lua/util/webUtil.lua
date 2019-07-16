--[[	
	web请求，request、response等公用方法
]]--

local stringUtil = require("util.stringUtil");

local webUtil = {};

-- 获取请求中所有头部信息
function webUtil.getReqHeaders() 
	return ngx.req.get_headers();
end

-- 获取请求头部信息
function webUtil.getReqHeader(name) 
	return stringUtil.toStringTrim(webUtil.getReqHeaders()[name]);
end

-- 设置请求头部
function webUtil.setReqHeader(key, value)
	ngx.req.set_header(key, value);
end

-- 请求请求头部key
function webUtil.clearReqHeader(key) 
	ngx.req.clear_header(key);
end

-- 设置响应中的头部信息
function webUtil.setRespHeader(key, value)
	ngx.header[key] = value;
end

-- 获取响应中的头部信息
function webUtil.getRespHeader(key)
	return ngx.header[key];
end

-- 获取域名比如http://www.wisedu.com/aa/bb?a=b,则返回www.wisedu.com
function webUtil.getHost() 
	return stringUtil.toStringTrim(ngx.var.host);
end

-- 获取请求方式get、post、put、delete
-- 或者用ngx.req.get_method()，经测试应该相同的含义
function webUtil.getRequestMethod() 
	return stringUtil.toStringTrim(ngx.var.request_method);
end

-- 获取uri,比如http://www.wisedu.com/aa/bb?a=b,则返回/aa/bb
function webUtil.getUri() 
	return stringUtil.toStringTrim(ngx.var.uri);
end

-- 获取request_uri,比如http://www.wisedu.com/aa/bb?a=b,则返回/aa/bb?a=b
function webUtil.getRequestUri() 
	return stringUtil.toStringTrim(ngx.var.request_uri);
end

-- 获取请求的参数，注意和webUtil.getVarArgs()的区别
-- 如果没有参数，此方法会返回空table
-- 注意若参数为a=b&c=d&a=x，获取到的a对应的值还是一个table，里面包含b和x
function webUtil.getUriArgs() 
	return ngx.req.get_uri_args();
end

-- 获取请求参数key的值
-- 示例a=b&c=d&a=x，则获取c时候返回字符串d，获取a时候获取是table
--经测试，这个方法获取到的参数是已经urlDecode过的值
function webUtil.getUriArg(key)
	return webUtil.getUriArgs()[key];
end

-- 获取请求的的参数，注意和webUtil.getUriArgs()的区别
-- 如果没有参数，此方法会返回nil
-- 此方法直接返回字符串，例如a=b&c=d&a=x则直接返回此字符串
function webUtil.getVarArgs() 
	return ngx.var.args;
end

-- 获取请求参数key的值
-- 示例a=b&c=d&a=x，则获取c时候返回字符串d，获取a时候获取是字符串b(即第一个参数值)
-- 经测试，这个方法获取到的参数没有进行urlDecode，但是getUriAgr方法获取到的参数就是已经urlDecode过的值
function webUtil.getVarArg(key)
	return ngx.var["arg_" .. key];
end

-- 获取请求者的ip地址
-- 首先获取x-forwarded-for头信息，若存在则返回，若不存在则进行下一步
-- 获取ngx.var.remote_addr
function webUtil.getRemoteIp() 
	local remoteIp = webUtil.getReqHeader("x-forwarded-for");
	if (stringUtil.isNotBlank(remoteIp)) then
		return remoteIp;
	end
	
	return stringUtil.toStringTrim(ngx.var.remote_addr);
end

-- 获取请求的端口号
function webUtil.getServerPort()
	return ngx.var.server_port;
end

-- 获取请求的scheme
function webUtil.getScheme()
	return ngx.var.scheme;
end

-- 返回请求体，一般是post请求时候可以获取到请求体
function webUtil.getReqBody()
	ngx.req.read_body();
	return stringUtil.toStringTrim(ngx.req.get_body_data());
end

-- 获取响应的状态
function webUtil.getResponseStatus() 
	return stringUtil.toStringTrim(ngx.status);
end

return webUtil;