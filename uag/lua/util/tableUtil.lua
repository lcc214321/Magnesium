--[[
 	table工具类
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

local stringUtil = require("util.stringUtil");

local tableUtil = {};

--[[
	获取table长度,为什么不用#table、table.getn,因为他们都有坑
]]--
function tableUtil.length(tempTable)
	local length = 0;  
	if (tempTable == nil) then
		return length;
	end
	for _, _ in pairs(tempTable) do  
        length = length + 1;  
    end
    return length;  
end

-- 判断tempTable是否为空
function tableUtil.isBlank(tempTable) 
	return tableUtil.length(tempTable)<1;
end

--[[
	慎用,连接table,注意一定要数组类型的table才可以使用此方法
]]--
function tableUtil.concat(tempTable, split)
	return table.concat(tempTable, split);
end

--[[
	连接table中key，并用split进行分割，当val为nil时候，key会被忽略
	注意split一定要是字符串
]]--
function tableUtil.concatKey(tempTable, split)
	local result = "";
	for key, _ in pairs(tempTable) do
		result = result .. key .. split;
	end
	result = string.sub(result, 0, string.len(result) - string.len(split));
	return result;
end

--[[
	连接table中value，并用split进行分割，当val为nil时候会被忽略
	注意split一定要是字符串
]]--
function tableUtil.concatVal(tempTable, split)
	local result = "";
	for _, val in pairs(tempTable) do	
		if (tableUtil.isTable(val)) then
			result = result .. tableUtil.concatVal(val, split) .. split;
		else
			result = result .. val .. split;
		end
	end
	result = string.sub(result, 0, string.len(result) - string.len(split));
	return result;
end

--[[
	连接table中key和value，并用split进行分割，当val为nil时候会被忽略
	注意split一定要是字符串
]]--
function tableUtil.concatKeyVal(tempTable, split)
	local result = "";
	for key, val in pairs(tempTable) do	
		if (tableUtil.isTable(val)) then
			result = result .. tableUtil.concatKeyVal(val, split) .. split;
		else
			result = result .. stringUtil.toStringTrim(key) .. ":" .. stringUtil.toStringTrim(val) .. split;
		end
	end
	result = string.sub(result, 0, string.len(result) - string.len(split));
	return result;
end


-- 判断是否是table类型
function tableUtil.isTable(param)
	if (type(param) == "table") then
		return true;
	else
		return false;
	end
end

-- 判断是否是数组类型的table
function tableUtil.isArrayTable(tempTable)  
    if type(tempTable) ~= "table" then  
        return false;  
    end  
  
    local length = #tempTable;  
    for i, _ in pairs(tempTable) do  
        if type(i) ~= "number" then  
            return false  
        end  
          
        if i > length then  
            return false  
        end   
    end  
  
    return true   
end  

-- 判断table是否为空
function tableUtil.isEmpty(tempTable)
	if (tempTable == nil) then
		return true;
	end
	
	if (tableUtil.length(tempTable)<1) then
		return true;
	end
	
	return false;
end

-- 根据key值删除table中对应的键值对
function tableUtil.removeElementByKey(tbl, key)  
    -- 新建一个临时的table  
    local tmp ={};  
  
    -- 把每个key做一个下标，保存到临时的table中，转换成{1=a,2=c,3=b}   
    -- 组成一个有顺序的table，才能在while循环准备时使用#table  
    for i in pairs(tbl) do  
        table.insert(tmp,i);  
    end  
  
    local newTbl = {};  
    --使用while循环剔除不需要的元素  
    local i = 1  
    while i <= #tmp do  
        local val = tmp [i];  
        if val == key then  
            --如果是需要剔除则remove   
            table.remove(tmp,i);  
         else  
            --如果不是剔除，放入新的tabl中  
            newTbl[val] = tbl[val];  
            i = i + 1;  
         end  
     end  
    return newTbl  
end

return tableUtil;

