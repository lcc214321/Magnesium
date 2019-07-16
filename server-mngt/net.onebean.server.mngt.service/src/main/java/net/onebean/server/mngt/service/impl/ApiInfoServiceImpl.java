package net.onebean.server.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.common.exception.BusinessException;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.server.mngt.api.model.*;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.dao.ApiInfoDao;
import net.onebean.server.mngt.model.ApiInfo;
import net.onebean.server.mngt.service.ApiInfoService;
import net.onebean.server.mngt.vo.ApiInfoFindReq;
import net.onebean.server.mngt.vo.ApiInfoVo;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author 0neBean
* @description api info serviceImpl
* @date 2019-01-22 10:59:03
*/
@Service
public class ApiInfoServiceImpl extends BaseBiz<ApiInfo, ApiInfoDao> implements ApiInfoService{

    private final static Logger logger = LoggerFactory.getLogger(ApiInfoServiceImpl.class);

    @Override
    public List<ApiInfoVo> findApiInfoVo(ApiInfoFindReq req, Pagination pagination, Sort sort) {

        String apiName = Optional.ofNullable(req).map(ApiInfoFindReq::getApiName).orElse(null);
        String apiUri = Optional.ofNullable(req).map(ApiInfoFindReq::getApiUri).orElse(null);
        String apiStatus = Optional.ofNullable(req).map(ApiInfoFindReq::getApiStatus).orElse(null);
        String proxyPath = Optional.ofNullable(req).map(ApiInfoFindReq::getProxyPath).orElse(null);
        Integer serverId = Optional.ofNullable(req).map(ApiInfoFindReq::getServerId).orElse(null);

        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(apiName)){
            Condition condition = Condition.parseModelCondition("apiName@string@like");
            condition.setValue(apiName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(apiUri)){
            Condition condition = Condition.parseModelCondition("apiUri@string@eq");
            condition.setValue(apiUri);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(proxyPath)){
            Condition condition = Condition.parseModelCondition("proxyPath@string@eq");
            condition.setValue(proxyPath);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(apiStatus)){
            Condition condition = Condition.parseModelCondition("apiStatus@string@eq");
            condition.setValue(apiStatus);
            paramList.add(condition);
        }


        if (StringUtils.isNotEmpty(serverId)){
            Condition condition = Condition.parseModelCondition("serverId@string@eq");
            condition.setValue(serverId);
            paramList.add(condition);
        }

        List<ApiInfo> list = this.find(pagination,paramList,sort);
        
        logger.debug("ApiInfoServiceImpl method findApiInfoVo list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<ApiInfoVo> res = JSON.parseArray(JSON.toJSONString(list), ApiInfoVo.class);
        logger.debug("ApiInfoServiceImpl method findApiInfoVo res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public ApiInfoVo findVoById(Object id) {
        ApiInfo apiInfo = this.findById(id);
        ApiInfoVo apiInfoVo = new ApiInfoVo();
        if (null != apiInfo){
            try {
                BeanUtils.copyProperties(apiInfoVo,apiInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg()+"bean of apiInfoVo");
            }
        }else{
            return null;
        }
        return apiInfoVo;
    }

    @Override
    public AppApiRelationshipResp findAppApiRelationshipByServerIdAndAppId(AppApiRelationshipReq request) {
         String serverId = Optional.ofNullable(request).map(AppApiRelationshipReq::getServerId).orElse(null);
         String appId = Optional.ofNullable(request).map(AppApiRelationshipReq::getAppId).orElse(null);
         AppApiRelationshipResp resp = new AppApiRelationshipResp();
        List<ApiInfoCloudVo> allApi;
        List<String> bindKeys;

         if (StringUtils.isEmpty(serverId)){
             throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of serverId");
         }

        if (StringUtils.isEmpty(appId)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of appId");
        }

        try {
            bindKeys = findUnBindApiIds(appId, serverId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.GET_DATE_ERR.code(),ErrorCodesEnum.GET_DATE_ERR.msg());
        }

        try {
            allApi = findApiByServerId(serverId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.GET_DATE_ERR.code(),ErrorCodesEnum.GET_DATE_ERR.msg());
        }
        resp.setAllApi(allApi);
        resp.setBindKeys(bindKeys);
        return resp;
    }

    @Override
    public AppApiRelationshipResp findApiByAppId(FindApiByAppIdReq request) {
        String appId = Optional.ofNullable(request).map(FindApiByAppIdReq::getAppId).orElse(null);
        AppApiRelationshipResp resp = new AppApiRelationshipResp();
        List<ApiInfoCloudVo> allApi;
        List<String> bindKeys;

        if (StringUtils.isEmpty(appId)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of appId");
        }

        try {
            allApi = baseDao.findApiByAppId(appId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.GET_DATE_ERR.code(),ErrorCodesEnum.GET_DATE_ERR.msg());
        }

        resp.setAllApi(allApi);
        resp.setBindKeys(Collections.emptyList());
        return resp;
    }

    @Override
    public List<String> findUnBindApiIds(String appId, String serverId) {
        return baseDao.findUnBindApiIds(appId, serverId);
    }

    @Override
    public List<ApiInfoCloudVo> findApiByServerId(String serverId) {
        return baseDao.findApiByServerId(serverId);
    }

    @Override
    public AppApiRelationSyncResqVo findSyncAppApiRelationship() {
        AppApiRelationSyncResqVo resp = new AppApiRelationSyncResqVo();
        List<AppApiRelationshipAppVo> appApi = baseDao.findSyncAppApiRelationship();
        logger.debug("ApiInfoServiceImpl method findSyncAppApiRelationship appApi = "+ JSON.toJSONString(appApi, SerializerFeature.WriteMapNullValue));
        List<UriApiRelationshipVo> uriApi = baseDao.findSyncUriApiRelationship();
        logger.debug("ApiInfoServiceImpl method findSyncAppApiRelationship uriApi = "+ JSON.toJSONString(uriApi, SerializerFeature.WriteMapNullValue));
        resp.setAppApiList(appApi);
        resp.setUriApiList(uriApi);
        return resp;
    }

    @Override
    public Integer deleteById(Object id) {
        String bindAppName = baseDao.findBindAppNameByApiId(Parse.toLong(id));
        if (StringUtils.isNotEmpty(bindAppName)){
            throw new BusinessException(ErrorCodesEnum.DATA_BINDING_ERR.code(),ErrorCodesEnum.DATA_BINDING_ERR.msg()+" binding app of "+bindAppName);
        }
        return super.deleteById(id);
    }

    @Override
    public void save(ApiInfo entity) {
        String proxyPath = Optional.ofNullable(entity).map(ApiInfo::getProxyPath).orElse(null);
        Long id = Optional.ofNullable(entity).map(ApiInfo::getId).orElse(null);
        if (StringUtils.isEmpty(proxyPath)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of proxyPath");
        }
        if (baseDao.countByProxyPathAndId(proxyPath,id) > 0){
            throw new BusinessException(ErrorCodesEnum.REPEART_PROXY_PATH_ERR.code(),ErrorCodesEnum.REPEART_PROXY_PATH_ERR.msg());
        }
        super.save(entity);
    }

    @Override
    public Integer update(ApiInfo entity) {
        String proxyPath = Optional.ofNullable(entity).map(ApiInfo::getProxyPath).orElse(null);
        Long id = Optional.ofNullable(entity).map(ApiInfo::getId).orElse(null);
        if (StringUtils.isEmpty(proxyPath)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of proxyPath");
        }
        if (null == id || id < 1){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"filed of id");
        }
        if (baseDao.countByProxyPathAndId(proxyPath,id) > 0){
            throw new BusinessException(ErrorCodesEnum.REPEART_PROXY_PATH_ERR.code(),ErrorCodesEnum.REPEART_PROXY_PATH_ERR.msg());
        }
        return super.update(entity);
    }
}