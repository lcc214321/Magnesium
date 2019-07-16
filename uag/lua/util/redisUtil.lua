--[[
 	操作redis的工具lua,注意只能操作单个redis,不能再多个redis之间切换
 	
 	参数格式
 	{
 		"ip": "127.0.0.1",		必填，值带双引号
 		"port": 6379,			必填，值不带双引号
 		"timeout": 3000,		超时时间，单位毫秒，非必填，值不带双引号
 		"password": "123456",	非必填，值带双引号
 		"poolSize": 1000,		非必填，值不带双引号
 		"maxIdleTime": 3000,	连接池中连接最大空闲时间，单位毫秒，非必填，值不带双引号
 		"select": 0				0-15,redis库，非必填，值不带双引号
 	}
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

-- string工具
local stringUtil = require("util.stringUtil");
-- 引用lua-resty-redis模块
local restyRedis = require("resty.redis");

local redisUtil = {};

local redisConfig;

-- 设置redis的配置，比如ip、port等,参数为table
function redisUtil.initConfig(redisConfigParam)
    redisConfig = redisConfigParam;
end

-- 创建redis对象、connetct、auth、select
local function init(options)
    if options then
        redisConfig = options;
    end

    local isSuccess, redisClientReturn, errReturn;
    -- creates a redis object
    local redisClient, err = restyRedis:new();
    if not redisClient then
        ngx.log(ngx.ERR, "调用new方法，创建redis对象出错: ", err);
        isSuccess = false;
        redisClientReturn = redisClient;
        errReturn = err;
        return isSuccess, redisClientReturn, errReturn;
    end

    -- 操作超时时间
    if (redisConfig["timeout"]) then
        redisClient:set_timeout(redisConfig["timeout"]);
    else
        -- 设置默认超时时间
        redisClient:set_timeout(3000);
    end

    -- connect
    local redisIp = redisConfig["ip"];
    local redisPort = redisConfig["port"];
    ngx.log(ngx.DEBUG, "尝试连接redis, ip:port[" .. redisIp .. ":" .. redisPort .. "]");
    local ok, err = redisClient:connect(redisIp, redisPort);
    if not ok then
        ngx.log(ngx.ERR, "Failed to connect redis by ip:port[" .. redisIp .. ":" .. redisPort .. "]: ", err);
        isSuccess = false;
        redisClientReturn = redisClient;
        errReturn = err;
        return isSuccess, redisClientReturn, errReturn;
    end
    ngx.log(ngx.DEBUG, "连接redis成功, ip:port[" .. redisIp .. ":" .. redisPort .. "]");

    -- 密码校验过程
    if stringUtil.isNotBlank(redisConfig["password"]) then
        local count, err = redisClient:get_reused_times();
        if 0 == count then
            local ok, err = redisClient:auth(redisConfig["password"]);
            if not ok then
                ngx.log(ngx.ERR, "redis auth 过程出错: ", err);
                isSuccess = false;
                redisClientReturn = redisClient;
                errReturn = err;
                return isSuccess, redisClientReturn, errReturn;
            end
        elseif err then
            ngx.log(ngx.ERR, "failed to get reused times: ", err);
            isSuccess = false;
            redisClientReturn = redisClient;
            errReturn = err;
            return isSuccess, redisClientReturn, errReturn;
        end
    end

    -- 切换redis数据库索引
    if (redisConfig["select"] ~= nil and redisConfig["select"] ~= 0) then
        local ok, err = redisClient:select(redisConfig["select"]);
        if not ok then
            ngx.log(ngx.ERR, "failed to select db to " .. redisConfig["select"] .. ": ", err)
            isSuccess = false;
            redisClientReturn = redisClient;
            errReturn = err;
            return isSuccess, redisClientReturn, errReturn;
        end
    end

    isSuccess = true;
    redisClientReturn = redisClient;
    return isSuccess, redisClientReturn, errReturn;
end

-- 关闭redis连接,其实是把连接放回连接池
local function close(redisClient, options)
    if options then
        redisConfig = options;
    end

    local maxIdleTime = redisConfig["maxIdleTime"];
    local poolSize = redisConfig["poolSize"];
    local ok, err = redisClient:set_keepalive(maxIdleTime, poolSize);
    if not ok then
        ngx.log(ngx.ERR, "Redis close error: ", err);
    end
end

-- redis判断是否存在key
function redisUtil.exists(key)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isExists = false;
    if key then
        local res, err = redisClient:exists(key);
        close(redisClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to exists key[" .. key .. "]: ", err);
            isSuccess = false;
            isExists = nil;
            errReturn = err;
            return isSuccess, isExists, errReturn;
        end

        if res == 1 then
            isExists = true;
        else
            isExists = false;
        end
    else
        ngx.log(ngx.ERR, "参数必须为非空");
        isSuccess = false;
        isExists = nil;
        errReturn = "参数不可以为空";
    end

    return isSuccess, isExists, errReturn;
end

-- 往redis中设置值，expireTime单位为秒
function redisUtil.setCache(key, value, expireTime)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isSetSuccess = true;

    redisClient:init_pipeline();
    redisClient:set(key, value);
    redisClient:expire(key, expireTime);
    local results, err = redisClient:commit_pipeline();
    close(redisClient);

    if not results then
        ngx.log(ngx.ERR, "Failed to set, key:value is [" .. key .. ":" .. value .. "],expire[" .. expireTime .. "]: ", err);
        isSetSuccess = false;
        errReturn = err;
        return isSuccess, isSetSuccess, errReturn;
    end

    for i, res in ipairs(results) do
        if type(res) == "table" then
            if not res[1] then
                ngx.log(ngx.ERR, "Failed to set, key:value is [" .. key .. ":" .. value .. "],expire[" .. expireTime .. "]: ", err);
                isSetSuccess = false;
                errReturn = err;
                return isSuccess, isSetSuccess, errReturn;
            end
        end
    end

    return isSuccess, isSetSuccess, errReturn;
end

-- 设置过期时间
function redisUtil.expireCache(key, expireTime)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isExpireSuccess;

    local ok, err = redisClient:expire(key, expireTime);
    close(redisClient);

    if not ok then
        ngx.log(ngx.ERR, "Failed to expire key[" .. key .. "]: ", err);
        isExpireSuccess = false;
        errReturn = err;
    else
        isExpireSuccess = true;
    end

    return isSuccess, isExpireSuccess, errReturn;
end

-- redis获取值 ，注意nil和ngx.null区别
function redisUtil.getCache(key)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local result;
    if key then
        local res, err = redisClient:get(key);
        close(redisClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to get key[" .. key .. "]: ", err);
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

-- 删除方法
function redisUtil.delCache(key)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isDelSuccess = true;
    if key then
        local ok, err = redisClient:del(key);
        close(redisClient);

        if not ok then
            ngx.log(ngx.ERR, "Failed to del key[" .. key .. "]: ", err);
            isDelSuccess = false;
            errReturn = err;
        end
    else
        ngx.log(ngx.ERR, "参数必须为非空");
        isSuccess = false;
        isDelSuccess = nil;
        errReturn = "参数不可以为空";
    end

    return isSuccess, isDelSuccess, errReturn;
end

--[[往redis中设置值，hash存储方式
-- 参数存在两种方式：
-- 1.redisUtil.hmset("hashKey", "key1", "val1", "key2", "val2");    
-- 2.redisUtil.hmset("hashKey", {["key1"] = "val1", ["key2"] = "val2"}),但是一定要注意，
--   一旦传入的参数为table，则只能是一个table,如果传入多个时候则直接把table当做key了。
-- 存储的value只能是string类型，如果value是table，则只会存储table的tostring的值(比如table: 0x40b691a0)，
-- 也就是说真正的table值被丢弃了，只存储了table的引用了
--]]
function redisUtil.hmset(key, ...)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isSetSuccess = true;

    local ok, err = redisClient:hmset(key, ...);

    close(redisClient);

    if not ok then
        ngx.log(ngx.ERR, "Failed to hmset key[" .. key .. "]: ", err);
        isSetSuccess = false;
        errReturn = err;
    end

    return isSuccess, isSetSuccess, errReturn;
end

-- 往redis中设置值，hash存储方式，expireTime单位为秒
function redisUtil.hmsetExpire(key, expireTime, ...)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isSetSuccess = true;

    redisClient:init_pipeline();
    redisClient:hmset(key, ...);
    redisClient:expire(key, expireTime);
    local results, err = redisClient:commit_pipeline();
    close(redisClient);

    if not results then
        ngx.log(ngx.ERR, "Failed to hmset, key is [" .. key .. "],expire[" .. expireTime .. "]: ", err);
        isSetSuccess = false;
        errReturn = err;
        return isSuccess, isSetSuccess, errReturn;
    end

    for i, res in ipairs(results) do
        if type(res) == "table" then
            if not res[1] then
                ngx.log(ngx.ERR, "Failed to set, key is [" .. key .. "],expire[" .. expireTime .. "]: ", err);
                isSetSuccess = false;
                errReturn = err;
            end
        end
    end

    return isSuccess, isSetSuccess, errReturn;
end

-- redis获取值 ,hget，注意nil和ngx.null区别
function redisUtil.hget(key, field)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local result;

    if (key and field) then
        local res, err = redisClient:hget(key, field);
        close(redisClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to hget key[" .. key .. "]、field[" .. field .. "]: ", err);
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

-- [[
-- redis获取值 ，hgetall，但是这个方法一般基本用不到，因为返回的值太多奇葩了，比如：
-- 存储结构为{"test1": {"key1": "value1", "key2": "value2"}}
-- hgetAll("test1")返回的值为{"1": "key1","2": "value1","3": "key2","4": "value2"}
-- 
--]]
function redisUtil.hgetAll(key)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local result;

    if (key) then
        local res, err = redisClient:hgetall(key);
        close(redisClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to hgetall key[" .. key .. "]: ", err);
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

-- [[删除hash中相应的值,传入的参数为可变参数，
-- 比如redisUtil.hdel("hashKey", "key1", "key2", "key3")
-- 删除hashKey对应hash中key为key1、key2、key3的键值对；
-- 请注意：传入table达不到删除效果，比如redisUtil.hdel("hashKey", {"key1", "key2", "key3"})，
-- 可以通过unpack实现效果redisUtil.hdel("hashKey", unpack({"key1", "key2", "key3"}))
--]]
function redisUtil.hdel(key, ...)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local isDelSuccess = true;
    if key then
        local ok, err = redisClient:hdel(key, ...);
        close(redisClient);

        if not ok then
            ngx.log(ngx.ERR, "Failed to hdel key[" .. key .. "]: ", err);
            isDelSuccess = false;
            errReturn = err;
        end
    else
        ngx.log(ngx.ERR, "参数必须为非空");
        isSuccess = false;
        isDelSuccess = nil;
        errReturn = "参数不可以为空";
    end

    return isSuccess, isDelSuccess, errReturn;
end


-- redis判断是否为set中的一个member
function redisUtil.sismember(key, member)
    local isSuccess, redisClient, errReturn = init();
    if (not isSuccess) then
        return isSuccess, nil, errReturn;
    end

    local result;
    if key then
        local res, err = redisClient:sismember(key, member);
        close(redisClient);

        -- 出错
        if not res then
            ngx.log(ngx.ERR, "Failed to execute sismember,  key is [" .. key .. "], member is ["..member.."]", err);
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

return redisUtil;
