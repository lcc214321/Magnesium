package net.onebean.server.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.server.mngt.api.model.ApiInfoCloudVo;
import net.onebean.server.mngt.api.model.FindServerByNameReq;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.dao.ServerInfoDao;
import net.onebean.server.mngt.model.ServerInfo;
import net.onebean.server.mngt.provider.mq.DevopsUpdateNginxUpSteamNodeSender;
import net.onebean.server.mngt.provider.mq.DevopsUpdateServerApiSender;
import net.onebean.server.mngt.service.ApiInfoService;
import net.onebean.server.mngt.service.ServerInfoService;
import net.onebean.server.mngt.vo.ServerInfoAddReq;
import net.onebean.server.mngt.vo.ServerInfoModifyReq;
import net.onebean.server.mngt.vo.ServerInfoVo;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @author 0neBean
* @description server info serviceImpl
* @date 2019-01-21 18:05:28
*/
@Service
public class ServerInfoServiceImpl extends BaseBiz<ServerInfo, ServerInfoDao> implements ServerInfoService{

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoServiceImpl.class);

    @Autowired
    private DevopsUpdateServerApiSender devopsUpdateServerApiSender;
    @Autowired
    private DevopsUpdateNginxUpSteamNodeSender  nginxUpSteamNodeSender;
    @Autowired
    private ApiInfoService apiInfoService;

    @Override
    public List<ServerInfoVo> findServerInfo(ServerInfoAddReq req, Pagination pagination, Sort sort) {

        String serverName = Optional.ofNullable(req).map(ServerInfoAddReq::getServerName).orElse(null);
        String deployType = Optional.ofNullable(req).map(ServerInfoAddReq::getDeployType).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(serverName)){
            Condition condition = Condition.parseModelCondition("serverName@string@like");
            condition.setValue(serverName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(deployType)){
            Condition condition = Condition.parseModelCondition("deployType@string@eq");
            condition.setValue(deployType);
            paramList.add(condition);
        }

        List<ServerInfo> list = this.find(pagination,paramList,sort);
        logger.debug("ServerInfoServiceImpl method findServerInfo list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<ServerInfoVo> res = JSON.parseArray(JSON.toJSONString(list), ServerInfoVo.class);
        logger.debug("ServerInfoServiceImpl method findServerInfo res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public ServerInfoVo findVoById(Object id) {
        ServerInfo serverInfo = this.findById(id);
        ServerInfoVo serverInfoVo = new ServerInfoVo();
        if (null != serverInfo){
            try {
                BeanUtils.copyProperties(serverInfoVo,serverInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg()+"bean of serverInfoVo");
            }
        }else{
            return null;
        }
        return serverInfoVo;
    }

    @Override
    public List<ServerInfoVo> findServerInfo(FindServerByNameReq req) {
        String serverName = Optional.ofNullable(req).map(FindServerByNameReq::getServerName).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(serverName)){
            Condition condition = Condition.parseModelCondition("serverName@string@like");
            condition.setValue(serverName);
            paramList.add(condition);
        }else{
            return Collections.emptyList();
        }

        List<ServerInfo> list = this.find(null,paramList,null);
        logger.debug("ServerInfoServiceImpl method findServerInfo list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<ServerInfoVo> res = JSON.parseArray(JSON.toJSONString(list), ServerInfoVo.class);
        logger.debug("ServerInfoServiceImpl method findServerInfo res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public Boolean syncAppApiRelationship() {
        return devopsUpdateServerApiSender.send();
    }


    @Override
    public Integer deleteById(Object id) {
        String bindAppName = baseDao.findBindAppNameByServerId(Parse.toLong(id));
        if (StringUtils.isNotEmpty(bindAppName)){
            throw new BusinessException(ErrorCodesEnum.DATA_BINDING_ERR.code(),ErrorCodesEnum.DATA_BINDING_ERR.msg()+" binding app of "+bindAppName);
        }
        return super.deleteById(id);
    }

    @Override
    public Boolean deleteServerInfo(ServerInfoModifyReq request) {
        String id = Optional.ofNullable(request).map(ServerInfoModifyReq::getId).orElse(null);
        this.deleteById(id);
        List<Long> ids = apiInfoService.findApiByServerId(id).stream().map(ApiInfoCloudVo::getId).map(a -> Parse.toLong(a)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(ids)){
            apiInfoService.deleteByIds(ids);
        }
        return true;
    }
}