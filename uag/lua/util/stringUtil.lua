--[[
 	string工具lua
   
 	User: 0neBean
 	Date: 2019/01/16
--]]


local stringUtil = {};

-- 去除前后空格,参数可以是任意值，但是我只会处理string类型
function stringUtil.trim(str)
	if type(str) ~= "string" or str == ngx.null then
		return str;
	end
	
    return str:gsub("^%s+", ""):gsub("%s+$", "");
end

-- nil变成""，其他类型变成string、同时trim
function stringUtil.toStringTrim(str)
	if str == nil or str == ngx.null then
    	return "";
    else 
    	str = stringUtil.trim(tostring(str));
        return str;
    end    
end

-- nil变成""，其他类型变成string、同时trim,若为空字符串则返回默认值
function stringUtil.toStringTrimDefault(str, defaultStr)
	str = stringUtil.toStringTrim(str);
	if str == "" then
    	return defaultStr;
    else 
        return str;
    end    
end

-- 判断是否为空字符串，参数只能是字符串
function stringUtil.isBlank(str)
	if str == nil or str == ngx.null then
    	return true;
    else 
    	str = stringUtil.trim(str);
        if str == "" then
        	return true;
        else 
        	return false;
        end
    end    
end

-- 判断是否为非空字符串，参数只能是字符串
function stringUtil.isNotBlank(str)
    return not stringUtil.isBlank(str);
end

-- 判断str是否以substr开头
function stringUtil.startsWith(str, substr)
	if (stringUtil.isBlank(str) or stringUtil.isBlank(substr)) then
		return false; 
	end
	
	local startIndex, endIndex = string.find(str, substr, 1, true);
	if startIndex ~= nil and startIndex == 1 then
		return true;
	else
		return false;
	end
end

-- 判断str是否以substr结尾
function stringUtil.endsWith(str, substr)
	if (stringUtil.isBlank(str) or stringUtil.isBlank(substr)) then
		return false; 
	end

	local index = string.len(str) - string.len(substr);
	local startIndex, endIndex = string.find(str, substr, index, true);
	if endIndex ~= nil and endIndex == string.len(str) then
		return true;
	else
		return false;
	end
end

-- 若endStr是str结尾则去除endStr结尾
function stringUtil.wipeoffEnd(str, endStr)
	if (stringUtil.isBlank(str) or stringUtil.isBlank(endStr)) then
		return str; 
	end
	if (not stringUtil.endsWith(str, endStr)) then
		return str;
	end

	local index = string.len(str) - string.len(endStr);
	return string.sub(str, 0, index);
end

-- 参数:待分割的字符串,分割字符
-- 返回:子串表.(含有空串)
function stringUtil.split(str, splitChar)
    local subStrTab = {};
    while (true) do
        local pos = string.find(str, splitChar);
        if (not pos) then
            subStrTab[#subStrTab + 1] = str;
            break;
        end
        local subStr = string.sub(str, 1, pos - 1);
        subStrTab[#subStrTab + 1] = subStr;
        str = string.sub(str, pos + 1, #str);
    end

    return subStrTab;
end

-- 判断str是否包含substr
function stringUtil.contains(str, substr)
	local startIndex, endIndex = string.find (str, substr, 1);
    if (startIndex ~= nil) then 
    	return true;
    else
    	return false;
    end
end

-- 产生随机字符串
function stringUtil.getRandomStr()
	-- uuid
	local uuid = require("3rd.uuid");
	uuid.seed();
    local randomStr = uuid();
    return randomStr
end

-- 编码
function stringUtil.encode(str)
	return ngx.escape_uri(str);
end

-- 解码
function stringUtil.decode(str)
	return ngx.unescape_uri(str);
end

-- 转大写
function stringUtil.upper(str)
	str = stringUtil.toStringTrim(str);
	if (stringUtil.isBlank(str)) then
		return "";
	end
		
	return string.upper(str);
end

-- 转小写
function stringUtil.lower(str)
	str = stringUtil.toStringTrim(str);
	if (stringUtil.isBlank(str)) then
		return "";
	end
	
	return string.lower(str);
end
-- 在字符串中替换,mainString为要替换的字符串， findString 为被替换的字符，replaceString 要替换的字符
function stringUtil.replaceAll(mainString, findString, replaceString)
	local startIndex, endIndex = string.find(mainString, findString, 0, true);
	if (startIndex == nil or endIndex == nil) then
		return mainString;
	end 

    mainString = string.sub(mainString, 0, startIndex-1) .. replaceString .. string.sub(mainString, endIndex+1, -1);
    
    return stringUtil.replaceAll(mainString, findString, replaceString);
end

function stringUtil.getTimeStampStr()
	return stringUtil.replaceAll(ngx.now(), ".", "");
end

return stringUtil;

