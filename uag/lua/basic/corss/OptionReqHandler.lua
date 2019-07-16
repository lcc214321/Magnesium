--
-- Created by IntelliJ IDEA.
-- User: 0neBean
-- Date: 2019/5/15
-- Time: 16:36
-- To change this template use File | Settings | File Templates.
--

local comm = require("comm.comm");
local webUtil = require("util.webUtil");

local function handler()
    local method = webUtil.getRequestMethod();
    if (method == "OPTIONS") then
        ngx.log(ngx.DEBUG, "OptionReqHandler handler OPTIONS method return 204");
        comm.returnOptionReq();
    end
end

--[[==============================================================================================]]
handler();