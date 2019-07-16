--[[
  	json工具lua
   
 	User: 0neBean
 	Date: 2019/01/16
--]]
local cjson = require("cjson.safe");
cjson.encode_empty_table_as_object(false); 

local jsonUtil = {};

-- 编码
-- 比如table转换成字符串
function jsonUtil.encode(data)
    return cjson.encode(data);
end

-- 解码
-- 比如字符串解析成table
function jsonUtil.decode(data)
    return cjson.decode(data);
end

return jsonUtil;
