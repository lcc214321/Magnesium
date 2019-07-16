package net.onebean.user.mngt.service;

import com.alibaba.fastjson.JSON;
import net.onebean.app.mngt.api.model.AppInfoSyncVo;
import net.onebean.common.exception.BusinessException;
import net.onebean.component.redis.IRedisService;
import net.onebean.user.mngt.common.CacheConstants;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 应用信息
 * @author 0neBean
 */
@Service
public class AppCacheInfoService {

    @Autowired
    private IRedisService iRedisService;

    /**
     * 获取appInfo 信息
     * @param appId 应用ID
     * @return appInfo
     */
    public AppInfoSyncVo getAppInfoFromCache(String appId){
        AppInfoSyncVo appInfo;
        try {
            String appInfoStr = Optional.ofNullable(iRedisService.hGet(CacheConstants.UagScopeKeys.APP_INFO.getValue(),appId)).map(s -> s+"").orElse("");
            if (StringUtils.isEmpty(appInfoStr)){
                throw new BusinessException(ErrorCodesEnum.GET_CACHE_ERR.code(),ErrorCodesEnum.GET_CACHE_ERR.msg()+" key = "+CacheConstants.UagScopeKeys.APP_INFO.getValue() +" hkey = "+appId);
            }
            try {
                appInfo = JSON.parseObject(appInfoStr,AppInfoSyncVo.class);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.GET_CACHE_ERR.code(),ErrorCodesEnum.GET_CACHE_ERR.msg()+" key = "+CacheConstants.UagScopeKeys.APP_INFO.getValue() +" hkey = "+appId);
        }
        return appInfo;

    }

}
