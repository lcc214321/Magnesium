--
-- Created by IntelliJ IDEA.
-- User: 0neBean
-- Date: 2019/6/14
-- Time: 16:06
-- To change this template use File | Settings | File Templates.
--
local webUtil = require("util.webUtil");
local stringUtil = require("util.stringUtil");
local constants = require("comm.constants");

local function verifyHost()
    local host = webUtil.getHost();
    --[[判断AppId下是否包含该api的apiId]]
    if (not stringUtil.contains(constants.UAG_HOST_ARRAY, host)) then
        ngx.log(ngx.DEBUG, "verifyHost failure,current host has no permissions access, current host = ["..host.."]");
        comm.error4OpenApiWithCode("no permissions host",errorCodesEnum.invalid_host);
    end

end
--[[==============================================================================================]]

verifyHost();