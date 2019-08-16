--
-- Created by IntelliJ IDEA.
-- User: 0neBean
-- Date: 2019/1/16
-- Time: 16:14
-- To change this template use File | Settings | File Templates.
--
local constants = require("comm.constants");
local comm = require("comm.comm");
local redisUtil = require("util.redisUtil");
local stringUtil = require("util.stringUtil");
local jsonUtil = require("util.jsonUtil");
local webUtil = require("util.webUtil");
local tableUtil = require("util.tableUtil");
local errorCodesEnum = require("comm.errorCodesEnum");


--[[检查访问地址是否是免登录地址]]
local function checkUnLoginAccessWhiteList()
    local currentApiPath = ngx.ctx.uri;
    local appId = ngx.ctx.appId;
    --[[use key从缓存获取 unLoginAccessWhiteList ]]
    local redisKey = constants.RS_SALES_UNLOGIN_ACCESS_WHITELIST_API;
        ngx.log(ngx.DEBUG, "openAPI checkUnLoginAccessWhiteList use key [" .. redisKey .. "] hash key ["..appId.."] get ipWhiteList from redis");

    local isSuccess, unLoginAccessWhiteList, errReturn = redisUtil.hget(redisKey,appId);

    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI checkUnLoginAccessWhiteList use key [" .. redisKey .. "] get unLoginAccessWhiteList from redis, failure");
        comm.errorStatusCommon("openAPI get unLoginAccessWhiteList from redis, by key [" .. redisKey .. "] error, error message:" .. errReturn);
    end

    unLoginAccessWhiteList = stringUtil.toStringTrim(unLoginAccessWhiteList);

    local pass = false;

    if(stringUtil.isBlank(unLoginAccessWhiteList)) then
        return pass;
    end

    --[[ipWhiteList 结果转json]]
    local unLoginAccessWhiteListJson = jsonUtil.decode(unLoginAccessWhiteList);

    if (unLoginAccessWhiteListJson ~= nil and unLoginAccessWhiteListJson ~= ngx.null) then
        for _,val in pairs(unLoginAccessWhiteListJson) do
            local apiPath = val.apiPath;
            if (stringUtil.isNotBlank(apiPath) and apiPath == currentApiPath) then
                pass = true;
            end
        end
    end
    return pass;
end

--[[混合令牌校验]]
local function checkHybridTokenLoginStatus(accessTokenCasheJson,appId)
    local deviceTokenCashe = stringUtil.toStringTrim(accessTokenCasheJson["deviceToken"]);
    ngx.ctx.deviceToken = deviceTokenCashe;
    ngx.log(ngx.DEBUG, "checkHybridTokenLoginStatus ngx.ctx.deviceToken = ["..deviceTokenCashe.."]");
    --[[检查未登录访问地址是否是免登录的资源]]
    local pass = checkUnLoginAccessWhiteList();
    if not pass then
        --[[登录状态 检查有无其他设备的登录]]
        local redisDeviceTokenKey = constants.RS_SALES_LOGIN_FLAG_DEVICETOKEN_KEY..":"..appId;
        local isSuccess, deviceTokenTable, errReturn = redisUtil.hget(redisDeviceTokenKey, deviceTokenCashe);
        --[[redis 里有值继续校验 否则可能 is 本地application 或基础application ]]
        if not isSuccess then
            ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisDeviceTokenKey .. "] hashkey ["..deviceTokenCashe.."] get account from redis, failure, error message:" .. errReturn);
            comm.errorStatusCommon("openAPI checkPrivateToken use key [" .. redisDeviceTokenKey .. "] hashkey ["..deviceTokenCashe.."] get account from redis, failure , error message:" .. errReturn);
        end

        if (deviceTokenTable == nil or deviceTokenTable == ngx.null) then
            ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisDeviceTokenKey .. "] hashkey ["..deviceTokenCashe.."] get account from redis, get empty value, error");
            --[[账号已经被登出  提示未登录]]
            comm.error4OpenApiWithCode("unlogin request",errorCodesEnum.unlogin_request);
        end

        local deviceTokenJson = jsonUtil.decode(deviceTokenTable);

        if(stringUtil.isBlank(deviceTokenJson)) then
            --[[账号已经被登出  提示未登录]]
            comm.error4OpenApiWithCode("unlogin request",errorCodesEnum.unlogin_request);
        else
            ngx.log(ngx.DEBUG, "deviceTokenJson = ["..jsonUtil.encode(deviceTokenJson).."]");
        end


        local uagUsername = stringUtil.toStringTrim(deviceTokenJson["uagUsername"]);
        local uagUserId = stringUtil.toStringTrim(deviceTokenJson["uagUserId"]);

        if(stringUtil.isBlank(uagUsername) or stringUtil.isBlank(uagUserId)) then
            --[[账号已经被登出  提示未登录]]
            comm.error4OpenApiWithCode("unlogin request",errorCodesEnum.unlogin_request);
        end
        --[[将 uagAuthUserId 设置到上下文]]
        ngx.ctx.uagAuthUserId = uagUserId;
    end

end

--[[校验免登陆token customerId 是否存在]]
local function checkAvoidLoginTokenCustomerId(accessTokenCasheJson,appId)
    local customerIdCashe = stringUtil.toStringTrim(accessTokenCasheJson["customerId"]);
    ngx.log(ngx.DEBUG, "customerIdCashe = "..customerIdCashe);
    if (stringUtil.isBlank(customerIdCashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAvoidLoginTokenCustomerId customerIdCashe is empty,mark unlogin request");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end
    ngx.ctx.customerId = customerIdCashe;
end

--[[验证checkAccessToken的有效性]]
local function checkAccessToken(accessToken,authType)
    local uri = ngx.ctx.uri;
    local appId = ngx.ctx.appId;
    if (stringUtil.isBlank(accessToken)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessToken is empty [uri = "..uri.."]");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    ngx.log(ngx.INFO, "openAPI AccessToken check, get header[token] [uri = "..uri.."] [accessToken = "..accessToken.."]");

    if (stringUtil.isBlank(appId)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken appIdis is empty [uri = "..uri.."]");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
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
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[appInfo结果转json]]
    local accessTokenCasheJson = jsonUtil.decode(accessTokenCashe);
    if (accessTokenCasheJson == nil or accessTokenCasheJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessTokenCasheJson is empty");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    local appId_cashe = stringUtil.toStringTrim(accessTokenCasheJson["appId"]);
    local accessTokenCashe = stringUtil.toStringTrim(accessTokenCasheJson["accessToken"]);

    --[[accessTokenCashe 非空校验]]
    if (stringUtil.isBlank(appId_cashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken appId_cashe is empty");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[accessTokenCashe 非空校验]]
    if (stringUtil.isBlank(accessTokenCashe)) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken accessTokenCasheis empty");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    --[[校验请求header[appId] 是否和缓存中一致]]
    if(appId ~= appId_cashe) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken header[appId] is not equal to redis value");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end


    --[[校验请求header[appId]是否和缓存中一致]]
    if(accessToken ~= accessTokenCashe) then
        ngx.log(ngx.DEBUG, "openAPI checkAccessToken header[appId] is not equal to redis value");
        comm.error4OpenApiWithCode("invalid accessToken",errorCodesEnum.invalid_accessToken);
    end

    ngx.ctx.accessToken = accessToken;

    return accessTokenCasheJson;
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
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end


    if(stringUtil.isBlank(appInfo)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus use key [" .. redisKey .. "] get appInfo from redis, is empty");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[appInfo结果转json]]
    local appInfoJson = jsonUtil.decode(appInfo);
    if (appInfoJson == nil or appInfoJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus appInfoJson is empty");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
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
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[appCategory 非空校验]]
    if (stringUtil.isBlank(appCategory)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus appCategory is empty");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[如果是云应用 设置租户id到全局变量]]
    if(appCategory == constants.OPENAPI_APP_CATEGORY_CLOUD)then
        if (stringUtil.isBlank(tenantId)) then
            ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus tenantId is empty");
            comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
        end
        ngx.ctx.tenantId = tenantId;
    end

    --[[authType 非空校验]]
    if (stringUtil.isBlank(authType)) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus authType is empty");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end

    --[[校验请求header[appId] 是否和缓存中一致]]
    if(appStatus ~= constants.OPENAPI_APP_ON_STATUS) then
        ngx.log(ngx.DEBUG, "openAPI checkAppIdStatus application current status is not accessible");
        comm.error4OpenApiWithCode("invalid appId",errorCodesEnum.invalid_appId);
    end
    --[[设置 appId,authType 到全局 checkAppApiRelation 用到]]
    ngx.ctx.authType = authType;
    return appInfoJson;
end

--[[检查api uri合法性 返回apiId]]
local function checkApipath()
    local uri = ngx.ctx.uri;
    if(stringUtil.isBlank(uri)) then
        ngx.log(ngx.DEBUG, "openAPI AccessToken check, get uriis empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    ngx.log(ngx.INFO, "openAPI AccessToken check start [uri = "..uri.."]");

    --[[use appId从缓存获取appIdStatus]]
    local redisKey = constants.OPENAPI_URI_API_RELATION;

    ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus use key [" .. redisKey .. "] get uriApiInfo from redis");

    local isSuccess, uriApiInfo, errReturn = redisUtil.hget(redisKey, uri);

    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus use key [" .. redisKey .. "] get uriApiInfo from redis, failure, error message:" .. errReturn);
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end


    if(stringUtil.isBlank(uriApiInfo)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus use key [" .. redisKey .. "] get uriApiInfo from redis, is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    --[[appInfo结果转json]]
    local uriApiInfoJson = jsonUtil.decode(uriApiInfo);
    if (uriApiInfoJson == nil or uriApiInfoJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus appInfoJson is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    local apiStatus = stringUtil.toStringTrim(uriApiInfoJson["apiStatus"]);
    local apiId = stringUtil.toStringTrim(uriApiInfoJson["id"]);
    local realPath = stringUtil.toStringTrim(uriApiInfoJson["apiUri"]);
    local hostPath = stringUtil.toStringTrim(uriApiInfoJson["hostPath"]);
    local deployType = stringUtil.toStringTrim(uriApiInfoJson["deployType"]);

    --[[appStatus 非空校验]]
    if (stringUtil.isBlank(apiStatus)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus apiStatus is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    --[[apiId 非空校验]]
    if (stringUtil.isBlank(apiId)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus apiId is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    --[[realPath 非空校验]]
    if (stringUtil.isBlank(realPath)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus realPath is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    --[[serverHost 非空校验]]
    if (stringUtil.isBlank(hostPath)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus hostPath is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    --[[deployType 非空校验]]
    if (stringUtil.isBlank(deployType)) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus deployType is empty");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address)
    end

    --[[检查API是否 is 可用状态]]
    if(apiStatus ~= constants.OPENAPI_API_ON_STATUS) then
        ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus api current status is not accessible");
        comm.error4OpenApiWithCode("invalid api address",errorCodesEnum.invalid_api_address);
    end

    ngx.log(ngx.DEBUG, "openAPI checkApipathAndStatus apiId["..apiId.."], realPath["..realPath.."], hostPath["..hostPath.."],");
    return apiId,realPath,hostPath,deployType
end

--[[检查app api 关联关系]]
local function checkAppApiRelation(apiIds,apiId)

    local uri = ngx.ctx.uri;
    local appId = ngx.ctx.appId;

    if(stringUtil.isBlank(apiIds)) then
        ngx.log(ngx.DEBUG, "openAPI api permission check, use appId get apiIdsis from redis, empty [uri = "..uri.."]");
        comm.error4OpenApiWithCode("no permissions api address",errorCodesEnum.no_permissions_api_address);
    end
    ngx.log(ngx.INFO, "openAPI api permission check, use appId get apiIds from redis, [uri = "..uri.."] [apiIds = "..apiIds.."]");


    --[[判断AppId下是否包含该api的apiId]]
    if (not stringUtil.contains(apiIds, apiId)) then
        ngx.log(ngx.DEBUG, "openAPI api permission check failure,current url has no permissions for current app [uri = "..uri.."] [appId = "..appId.."]");
        comm.error4OpenApiWithCode("no permissions api address",errorCodesEnum.no_permissions_api_address);
    end


    if (stringUtil.contains(apiIds, apiId)) then
        ngx.log(ngx.INFO, "openAPI api permission check over, allow [uri = "..uri.."] [appId = "..appId.."]");
    end
end

--[[通过appId获取ApiIds的用逗号分隔的字符串]]
local function getApiIdsByAppId()
    local appId = ngx.ctx.appId;
    ngx.log(ngx.DEBUG, "openAPI use key [" .. constants.OPENAPI_APP_API_RELATION .. "], hkey [" .. appId .. "] get apiIds from redis, start");
    local isSuccess, apiIds, errReturn = redisUtil.hget(constants.OPENAPI_APP_API_RELATION, appId);

    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI api permission check, use appId get apiIds from redis, failure");
        comm.errorStatusCommon("get apiIds from redis, by key [" .. appId .. "] error, error mesage:" .. errReturn);
    end
    --[[apiIdsis empty 提示api地址无效]]
    if (stringUtil.isBlank(apiIds)) then
        ngx.log(ngx.DEBUG, "openAPI api permission check, use appId get apiIds from redis, is empty");
        comm.error4OpenApiWithCode("no permissions api address",errorCodesEnum.no_permissions_api_address);
    end

    return apiIds;
end

--[[判断Uri有没有参数 拼接到转发地址后面]]
local function setReqParam2ProxyPass(realPath)
    local requestUri = webUtil.getRequestUri();
    local requestUriTable = stringUtil.split(requestUri, "?");
    if (stringUtil.isNotBlank(requestUriTable[2])) then
        if (stringUtil.contains(realPath, "?")) then
            realPath = realPath .. "&" .. requestUriTable[2];
        else
            realPath = realPath .. "?" .. requestUriTable[2];
        end
    end
    return realPath;
end

--[[设置api的实际访问地址]]
local function setApiAddressProxyPass(realPath,hostPath,deployType)
    if (not stringUtil.startsWith(realPath, "/")) then
        realPath = "/" .. realPath;
    end
    ngx.var.proxyPass = "http://" .. hostPath .. realPath;
end

--[[记录访问信息]]
local function recordInformation(realPath)
    --[[记录访问地址]]
    ngx.ctx.openapiReplaceContent = realPath;
    --[[记录请求的method,args]]
    ngx.ctx.openapiMethod = webUtil.getRequestMethod();
    ngx.ctx.openapiArgs = stringUtil.lower(webUtil.getVarArgs());
end

--[[检查ip白名单和token合法性]]
local function checkIpWhtieListAndToken(appInfoJson)
    --[[获取header[staticToken]]
    local innerApiToken = stringUtil.toStringTrim(appInfoJson["innerApiToken"]);
    local currentIpAddress = webUtil.getRemoteIp();
    local staticToken = webUtil.getReqHeader(constants.HEADER_REQUEST_INNER_API_TOKEN_KEY);


    --[[appStatus 非空校验]]
    if (stringUtil.isBlank(innerApiToken)) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken innerApiToken is empty");
        comm.error4OpenApiWithCode("invalid staticToken",errorCodesEnum.invalid_staticToken);
    end

    --[[currentIpAddress 非空校验]]
    if (stringUtil.isBlank(currentIpAddress)) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken currentIpAddress is empty");
        comm.error4OpenApiWithCode("invalid staticToken",errorCodesEnum.invalid_staticToken);
    end

    --[[innerApiToken 非空校验]]
    if (stringUtil.isBlank(staticToken)) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken staticToken is empty");
        comm.error4OpenApiWithCode("invalid staticToken",errorCodesEnum.invalid_staticToken);
    end

    ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken start, staticToken ["..staticToken.."], currentIpAddress ["..currentIpAddress.."], innerApiToken ["..innerApiToken.."]");


    if (staticToken ~= innerApiToken) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken innerApiToken [" .. innerApiToken .. "] not equals staticToken [" .. staticToken .. "]");
        comm.error4OpenApiWithCode("invalid staticToken",errorCodesEnum.invalid_staticToken);
    end


    --[[use key从缓存获取 ipWhiteList ]]
    local redisKey = constants.IP_ADDRESS_WHITE_LIST;
    ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken use key [" .. redisKey .. "] get ipWhiteList from redis");

    local isSuccess, ipWhiteListCashe, errReturn = redisUtil.getCache(redisKey);

    if not isSuccess then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken use key [" .. redisKey .. "] get ipWhiteList from redis, failure");
        comm.errorStatusCommon("openAPI get ipWhiteList from redis, by key [" .. redisKey .. "] error, error message:" .. errReturn);
    end

    --[[ipWhiteList 结果转json]]
    local ipWhiteListJson = jsonUtil.decode(ipWhiteListCashe);
    if (ipWhiteListJson == nil or ipWhiteListJson == ngx.null) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken ipWhiteListJson is empty");
        comm.error4OpenApiWithCode("invalid staticToken",errorCodesEnum.invalid_staticToken);
    end

    local isWtite = false;

    for _,val in pairs(ipWhiteListJson) do
        local ipAddress = val.ipAddress;
        if (stringUtil.isNotBlank(ipAddress) and ipAddress == currentIpAddress) then
            isWtite = true;
        end
    end

    if(not isWtite) then
        ngx.log(ngx.DEBUG, "openAPI checkIpWhtieListAndToken currentIpAddress ["..currentIpAddress.."] is not in allow list");
        comm.error4OpenApiWithCode("ipaddress is not allow",errorCodesEnum.invalid_staticToken);
    end


end


local function setUagReqHeader()
    local appid = ngx.ctx.appid;
    local accessToken = ngx.ctx.accessToken;
    local tenantId = ngx.ctx.tenantId;
    local uagAuthUserId = ngx.ctx.uagAuthUserId;
    local customerId = ngx.ctx.customerId;
    local deviceToken = ngx.ctx.deviceToken;

    --[[将 uagAuthUserId 设置到请求头部]]
    if (stringUtil.isNotBlank(uagAuthUserId)) then
        webUtil.setReqHeader(constants.HEADER_UAG_AUTH_USER_ID_KEY,uagAuthUserId);
        ngx.log(ngx.INFO, "openAPI setUagReqHeader header[uagAuthUserId] is [uagAuthUserId = "..webUtil.getReqHeader(constants.HEADER_UAG_AUTH_USER_ID_KEY).."]");
    end

    --[[将 customerId 设置到请求头部]]
    if (stringUtil.isNotBlank(customerId)) then
        webUtil.setReqHeader(constants.HEADER_UAG_AUTH_USER_ID_KEY,customerId);
        ngx.log(ngx.INFO, "openAPI setUagReqHeader header[customerId] is [customerId = "..webUtil.getReqHeader(constants.HEADER_UAG_AUTH_USER_ID_KEY).."]");
    end

    ngx.log(ngx.DEBUG, "setUagReqHeader ngx.ctx.deviceToken = ["..deviceToken.."]");
    --[[将 customerId 设置到请求头部]]
    if (stringUtil.isNotBlank(deviceToken)) then
        webUtil.setReqHeader(constants.HEADER_UAG_AUTH_DEVICE_TOKEN,deviceToken);
        ngx.log(ngx.INFO, "openAPI setUagReqHeader header[deviceToken] is [deviceToken = "..webUtil.getReqHeader(constants.HEADER_UAG_AUTH_DEVICE_TOKEN).."]");
    end

    --[[将 appid 设置到请求头部]]
    webUtil.setReqHeader(constants.HEADER_APPID_KEY,appid);
    ngx.log(ngx.INFO, "openAPI setUagReqHeader header[appId] is [appId = "..webUtil.getReqHeader(constants.HEADER_APPID_KEY).."]");

    --[[将 accessToken 设置到请求头部]]
    webUtil.setReqHeader(constants.HEADER_ACCESSTOKEN_KEY,accessToken);
    ngx.log(ngx.INFO, "openAPI setUagReqHeader header[accessToken] is [accessToken = "..webUtil.getReqHeader(constants.HEADER_ACCESSTOKEN_KEY).."]");

    if (stringUtil.isBlank(tenantId)) then
        --[[将 tenantId 设置到请求头部]]
        webUtil.setReqHeader(constants.HEADER_TENANT_ID_KEY,tenantId);
        ngx.log(ngx.INFO, "openAPI setUagReqHeader header[tenantId] is [accessToken = "..webUtil.getReqHeader(constants.HEADER_TENANT_ID_KEY).."]");
    end

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
local appInfoJson = checkAppIdStatus();

--[[oauth授权码校验规则]]
if (ngx.ctx.authType == constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE) then
    --[[执行AccessToken校验]]
    checkAccessToken(accessToken,constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE);
elseif(ngx.ctx.authType == constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE)then
    --[[oauth私有授权码校验规则]]
    local accessTokenCasheJson = checkAccessToken(accessToken,constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE);
    checkHybridTokenLoginStatus(accessTokenCasheJson,appId);
elseif(ngx.ctx.authType == constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE_AVOID_LOGIN)then
    --[[oauth私有授权码校验规则]]
    local accessTokenCasheJson = checkAccessToken(accessToken,constants.OPENAPI_APP_AUTH_TYPE_OAUTH_CODE_PRIVATE);
    checkAvoidLoginTokenCustomerId(accessTokenCasheJson,appId);
else
    --[[ip+token校验规则]]
    checkIpWhtieListAndToken(appInfoJson);
end
--[[检查uri是否合法 返回apiId]]
local apiId,realPath,hostPath,deployType = checkApipath();
--[[通过appId获取对应绑定的apiIds]]
local apiIds = getApiIdsByAppId();
--[[检查当前uri 和 application 的关联关系]]
checkAppApiRelation(apiIds,apiId);
--[[记录访问信息 一定要放置在这]]
recordInformation(realPath);
--[[将请求url上参数拼接到proxyPass]]
realPath = setReqParam2ProxyPass(realPath);
--[[设置uri的实际访问地址]]
setApiAddressProxyPass(realPath,hostPath,deployType);
--[[设置uag校验后的头部信息]]
setUagReqHeader();
ngx.log(ngx.DEBUG, "proxypath info ["..ngx.var.proxyPass.."]");

