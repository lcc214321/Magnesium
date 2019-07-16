--[[
  	cookie工具lua
   
 	User: 0neBean
 	Date: 2019/01/16
--]]
local webUtil = require("util.webUtil");

local cookieUtil = {};

-- 设置cookie的header信息，先获取已存在的header，然后add新的cookie操作，防止两次操作同时存在的情况
local function setCookieHeader(cookieStr)
    local headers = {};
    local existCookie = webUtil.getRespHeader("Set-Cookie");
    if existCookie ~= nil then
        table.insert(headers,existCookie);
    end
    table.insert(headers,cookieStr);
    webUtil.setRespHeader("Set-Cookie", headers);
end

-- 清除key对应的cookie
function cookieUtil.invalidate(key)
    local cookieStr = key .. "=; Max-Age=0; path=/; Httponly";
    setCookieHeader(cookieStr);
end

-- 获取key对应的cookie值
function cookieUtil.getValue(key)
    return ngx.var["cookie_" .. key];
end

-- 设置cookie
function cookieUtil.create(key, value)
    local cookieStr = key .. "=" .. value .. "; path=/; Httponly";
    setCookieHeader(cookieStr);
end


return cookieUtil;
