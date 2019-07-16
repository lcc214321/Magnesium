--[[
 	操作mysql的工具lua,注意只能操作单个mysql,不能再多个mysql之间切换
 	
 	参数格式
 	{
 		"host": "172.16.6.150",		必填，值带双引号，如果是域名，需要域名解析，配置resolver
		"port": 3306,				必填，值不带双引号
		"database": "wec_pt_uag",	必填，值带双引号
		"user": "root",				必填，值带双引号
		"password": "123456",		必填，值带双引号
		"timeout": 3000				非必填，值不带双引号，单位毫秒
		"poolSize": 30				非必填，值不带双引号
		"maxIdleTime": 30000		非必填，值不带双引号，连接池中连接最大空闲时间，单位毫秒
 	}
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

-- string工具
local stringUtil = require("util.stringUtil");
-- 引用lua-resty-mysql模块
local restyMysql = require "resty.mysql";

local mysqlUtil = {};

local mysqlConfig;

-- 设置mysql的配置，比如ip、port等,参数为table
function mysqlUtil.initConfig(mysqlConfigParam)
    mysqlConfig = mysqlConfigParam;
end

-- 创建mysql对象、new、connetct
local function init(options)
    if options then
        mysqlConfig = options;
    end

    local isSuccess, mysqlClientReturn, errReturn;
    -- creates a mysql object
    local mysqlClient, err = restyMysql:new();
    if not mysqlClient then
        ngx.log(ngx.ERR, "调用new方法，创建mysql对象出错: ", err);
        isSuccess = false;
        mysqlClientReturn = mysqlClient;
        errReturn = err;
        return isSuccess, mysqlClientReturn, errReturn;
    end

    -- 操作超时时间
    if (mysqlConfig["timeout"]) then
        mysqlClient:set_timeout(mysqlConfig["timeout"]);
    else
        -- 设置默认超时时间
        mysqlClient:set_timeout(3000);
    end

    -- connect
    local mysqlHost = mysqlConfig["host"];
    local mysqlPort = mysqlConfig["port"];
    ngx.log(ngx.DEBUG, "尝试连接mysql, ip:port[" .. mysqlHost .. ":" .. mysqlPort .. "]");
    
    local ok, err, errcode, sqlstate = mysqlClient:connect({
    	host = mysqlHost,
    	port = mysqlPort,
    	database = mysqlConfig["database"],
    	user = mysqlConfig["user"],
    	password = mysqlConfig["password"],
    });
    if not ok then
        ngx.log(ngx.ERR, "Failed to connect mysql by ip:port[" .. mysqlHost .. ":" .. mysqlPort .. "]: ", err, ": ", errcode, ":", sqlstate);
        isSuccess = false;
        mysqlClientReturn = mysqlClient;
        errReturn = err;
        return isSuccess, mysqlClientReturn, errReturn;
    end
    ngx.log(ngx.DEBUG, "连接mysql成功, host:port[" .. mysqlHost .. ":" .. mysqlPort .. "]");

    isSuccess = true;
    mysqlClientReturn = mysqlClient;
    return isSuccess, mysqlClientReturn, errReturn;
end

-- 关闭mysql连接,其实是把连接放回连接池
local function close(mysqlClient, options)
    if options then
        mysqlConfig = options;
    end


	local maxIdleTime = mysqlConfig["maxIdleTime"];
	if (not maxIdleTime) then
        maxIdleTime = 10000;
    end    
    local poolSize = mysqlConfig["poolSize"];
	if (not poolSize) then
        poolSize = 20;
    end
    local ok, err = mysqlClient:set_keepalive(maxIdleTime, poolSize);
    if not ok then
        ngx.log(ngx.ERR, "mysql close error: ", err);
    end
end

-- mysql查询 
-- 返回的数据为table，注意若返回记录数为0时候可以使用tableUtil.isBlank()进行判断
-- 可以通过ndk.set_var.set_quote_sql_str过滤参数放置依赖注入
function mysqlUtil.query(sql)
    local isSuccess, mysqlClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local result;
    if sql then
        local res, err, errcode, sqlstate = mysqlClient:query(sql);        
        close(mysqlClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to query sql[" .. sql .. "]: ", err, ":", errcode, ":", sqlstate);
            isSuccess = false;
            result = nil;
            errReturn = err;
            return isSuccess, result, errReturn;
        end

        result = res;
    else
        ngx.log(ngx.ERR, "参数必须为非空");
        isSuccess = false;
        result = nil;
        errReturn = "参数不可以为空";
    end

    return isSuccess, result, errReturn;
end

return mysqlUtil;
