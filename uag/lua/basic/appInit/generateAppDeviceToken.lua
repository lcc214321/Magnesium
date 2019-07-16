--
-- Created by IntelliJ IDEA.
-- User: 0neBean
-- Date: 2019/3/13
-- Time: 16:00
-- To change this template use File | Settings | File Templates.
--
local webUtil = require("util.webUtil");
local stringUtil = require("util.stringUtil");
local constants = require("comm.constants");
local jsonUtil = require("util.jsonUtil");
local redisUtil = require("util.redisUtil");
local comm = require("comm.comm");
local errorCodesEnum = require("comm.errorCodesEnum");

--[[根据req ua 获取请求平台code 和 对应加密字符]]
local function getplatform()
    local platform = "";
    local platformPrefix = "platform=";
    local ua = webUtil.getReqHeader("user-agent");
    if (stringUtil.isBlank(ua)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus ua is empty");
        comm.error4OpenApiWithCode("invalid request",errorCodesEnum.invalid_request);
    end

    ua = stringUtil.lower(ua)
    if (stringUtil.contains(ua,constants.PLATFORM_TYPE_OF_ANDROID)) then
        platform = constants.PLATFORM_TYPE_OF_ANDROID_CODE;
        platformPrefix = platformPrefix..constants.PLATFORM_TYPE_OF_ANDROID;
    elseif (stringUtil.contains(ua,"iphone") or stringUtil.contains(ua,"ipad") or stringUtil.contains(ua,"ipod")) then
        platform = constants.PLATFORM_TYPE_OF_IOS_CODE;
        platformPrefix = platformPrefix..constants.PLATFORM_TYPE_OF_IOS;
    else
        platform = constants.PLATFORM_TYPE_OF_OTHER_CODE;
        platformPrefix = platformPrefix..constants.PLATFORM_TYPE_OF_OTHER;
    end
    return platform,platformPrefix;
end

--[[生成设备编号]]
local function doGenerate(platform,encryption,appId)
    local str = require "resty.string"
    local resty_sha1 = require "resty.sha1"

    local sha1 = resty_sha1:new()
    if not sha1 then
        comm.errorStatusCommon("generateAppDeviceToken doGenerate resty_sha1:new() error");
    end

    local ok = sha1:update(encryption);
    if not ok then
        comm.errorStatusCommon("generateAppDeviceToken doGenerate sha1 update encryption error");
    end

    ok = sha1:update("timestamp="..stringUtil.getTimeStampStr());
    if not ok then
        comm.errorStatusCommon("generateAppDeviceToken doGenerate sha1 update timestamp error");
    end


    local digest = sha1:final();
    return str.to_hex(digest)..platform..appId;
end

--[[返回deviceToken]]
local function returnDeviceToken(deviceToken)
    ngx.log(ngx.DEBUG, "generate deviceToken success token ["..deviceToken.."]");
    local ajaxTable = {};
    ajaxTable["errCode"] = "0";
    ajaxTable["errMsg"] = "";
    ajaxTable["datas"] = deviceToken;
    webUtil.setRespHeader("Content-Type", "application/json");
    ngx.print(jsonUtil.encode(ajaxTable));
    ngx.exit(200);
end


-- ===================================================================
--[[初始化redis]]
redisUtil.initConfig(comm.getRedisConfig());
local appId = webUtil.getReqHeader(constants.HEADER_REQUEST_APP_ID_KEY);
local platform,encryption = getplatform();
local deviceToken = doGenerate(platform,encryption,appId);
returnDeviceToken(deviceToken);

