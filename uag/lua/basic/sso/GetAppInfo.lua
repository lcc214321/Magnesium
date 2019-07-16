local webUtil = require("util.webUtil");
local constants = require("comm.constants");
local redisUtil = require("util.redisUtil");
local comm = require("comm.comm");
local stringUtil = require("util.stringUtil");
local jsonUtil = require("util.jsonUtil");
local errorCodesEnum = require("comm.errorCodesEnum");

local function returnAppInfoJson()
    ngx.log(ngx.DEBUG, "do returnLoginSuccess");
    local ajaxTable = {};
    ajaxTable["errCode"] = "0";
    ajaxTable["errMsg"] = "";
    ajaxTable["datas"] = ngx.ctx.loginType;
    webUtil.setRespHeader("Content-Type", "application/json");
    ngx.print(jsonUtil.encode(ajaxTable));
    ngx.exit(200);
end

local function returnFaileJson(errMsg,errCode)
    ngx.log(ngx.DEBUG, "do returnLoginSuccess");
    local ajaxTable = {};
    ajaxTable["errCode"] = errCode;
    ajaxTable["errMsg"] = errMsg;
    ajaxTable["datas"] = {};
    webUtil.setRespHeader("Content-Type", "application/json");
    ngx.print(jsonUtil.encode(ajaxTable));
    ngx.exit(200);
end

--[[检查application 状态是否 is 可以访问状态]]
local function checkAppIdStatus()
    local appId = ngx.ctx.appId;
    --[[use appId从缓存获取appIdStatus]]
    local redisKey = constants.OPENAPI_APP_INFO_KEY;

    ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisKey .. "],hkey [" .. appId .. "] get appInfo from redis");

    local isSuccess, appInfo, errReturn = redisUtil.hget(redisKey, appId);

    --[[redis 里有值继续校验 否则可能 is 本地application 或基础application ]]
    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisKey .. "] get appInfo from redis, failure, error message:" .. errReturn);
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end


    if(stringUtil.isBlank(appInfo)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisKey .. "] get appInfo from redis, is empty");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[appInfo结果转json]]
    local appInfoJson = jsonUtil.decode(appInfo);
    if (appInfoJson == nil or appInfoJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus appInfoJson is empty");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    local appStatus = stringUtil.toStringTrim(appInfoJson["appStatus"]);
    local appCategory = stringUtil.toStringTrim(appInfoJson["appCategory"]);
    local tenantId = stringUtil.toStringTrim(appInfoJson["tenantId"]);
    local authType = stringUtil.toStringTrim(appInfoJson["authType"]);
    local loginType = stringUtil.toStringTrim(appInfoJson["loginType"]);


    --[[loginType 放入缓存]]
    if (stringUtil.isNotBlank(loginType)) then
        ngx.ctx.loginType = loginType;
    end


    --[[appStatus 非空校验]]
    if (stringUtil.isBlank(appStatus)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus appStatus is empty");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[appCategory 非空校验]]
    if (stringUtil.isBlank(appCategory)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus appCategory is empty");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[如果是云应用 设置租户id到全局变量]]
    if(appCategory == constants.OPENAPI_APP_CATEGORY_CLOUD)then
        if (stringUtil.isBlank(tenantId)) then
            ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus tenantId is empty");
            returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
        end
        ngx.ctx.tenantId = tenantId;
    end

    --[[authType 非空校验]]
    if (stringUtil.isBlank(authType)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus authType is empty");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[校验请求header[appId] 是否和缓存中一致]]
    if(appStatus ~= constants.OPENAPI_APP_ON_STATUS) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus application current status is not accessible");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end
    --[[设置 appId,authType 到全局 checkAppApiRelation 用到]]
    ngx.ctx.authType = authType;
    return appInfoJson;
end


--[[验证checkAccessToken的有效性]]
local function checkAccessToken(accessToken,authType)
    local uri = ngx.ctx.uri;
    local appId = ngx.ctx.appId;
    if (stringUtil.isBlank(accessToken)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessToken is empty [uri = "..uri.."]");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    ngx.log(ngx.INFO, "openAPI AccessToken check, get header[token] [uri = "..uri.."] [accessToken = "..accessToken.."]");

    if (stringUtil.isBlank(appId)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken appIdis is empty [uri = "..uri.."]");
        returnFaileJson("invalid appId",errorCodesEnum.invalid_appId);
    end

    ngx.log(ngx.INFO, "openAPI AccessToken check, get header[appId] [uri = "..uri.."] [appId = "..appId.."]");

    local isSuccess, accessTokenCashe, errReturn;
    local redisKey = constants.OPENAPI_ACCESSTOKEN_KEY..":"..appId;
    --[[use accessToken从缓存获取 appInfo ]]
    --[[oauth 授权码模式]]
    if(authType == constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE or authType == constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE_AVOID_LOGIN) then
        --[[oauth hybrid token]]
        redisKey = redisKey..constants.PRIVATETOKEN_KEY..":"..accessToken;
        isSuccess, accessTokenCashe, errReturn = redisUtil.getCache(redisKey);
    else
        isSuccess, accessTokenCashe, errReturn = redisUtil.getCache(redisKey);
    end


    ngx.log(ngx.DEBUG, "openAPI checkAccessToken use key [" .. redisKey .. "] get accessToken from redis");

    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken use key [" .. redisKey .. "] get accessToken from redis, failure");
        comm.errorStatusCommon("openAPI get accessToken from redis, by key [" .. redisKey .. "] error, error message:" .. errReturn);
    end

    if(stringUtil.isBlank(accessTokenCashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken use key [" .. redisKey .. "] get checkAccessToken from redis, is empty");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[appInfo结果转json]]
    local accessTokenCasheJson = jsonUtil.decode(accessTokenCashe);
    if (accessTokenCasheJson == nil or accessTokenCasheJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessTokenCasheJson is empty");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    local appId_cashe = stringUtil.toStringTrim(accessTokenCasheJson["appId"]);
    local accessTokenCashe = stringUtil.toStringTrim(accessTokenCasheJson["accessToken"]);

    --[[accessTokenCashe 非空校验]]
    if (stringUtil.isBlank(appId_cashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken appId_cashe is empty");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[accessTokenCashe 非空校验]]
    if (stringUtil.isBlank(accessTokenCashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessTokenCasheis empty");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[校验请求header[appId] 是否和缓存中一致]]
    if(appId ~= appId_cashe) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken header[appId] is not equal to redis value");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end


    --[[校验请求header[appId]是否和缓存中一致]]
    if(accessToken ~= accessTokenCashe) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken header[appId] is not equal to redis value");
        returnFaileJson("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    ngx.ctx.accessToken = accessToken;

    return accessTokenCasheJson;
end

local function intiRedisConf()
    redisUtil.initConfig(comm.getRedisConfig());
end

--[[==============================================================================================]]

--[[初始化redis]]
intiRedisConf();
--[[获取请求uri 放到全局里]]
local uri = webUtil.getUri();
ngx.ctx.uri = uri;
--[[获取header[token]]
local accessToken = webUtil.getReqHeader(constants.HEADER_REQUEST_ACCESSTOKEN_KEY);
--[[获取header[appId]]
local appId = webUtil.getReqHeader(constants.HEADER_REQUEST_APP_ID_KEY);
ngx.ctx.appId = appId;
--[[检查application 状态是否 is 可以访问状态]]
checkAppIdStatus();
--[[检查accessToken合法性]]
checkAccessToken(accessToken,constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE);
--[[返回appinfo]]
returnAppInfoJson();