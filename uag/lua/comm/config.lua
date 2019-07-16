--[[
   	配置功能模块,为了后续操作能方便读取配置，这里抽象成公共配置模块。
   	提示：一定要记得在nginx配置文件中配置lua_shared_dict config 10m
   	其中10m是共享内存大小，可以根据自己需要修改。
   
 	User: 0neBean
 	Date: 2019/01/16
--]]

local config = {};

-- 获取相应配置的值
function config.get(key)
    return ngx.shared.config:get(key);
end

-- 把配置的值设置到共享内存中
function config.set(key, value)
    ngx.shared.config:set(key, value);
end

return config;