package net.onebean.server.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.dao.NginxNodeDao;
import net.onebean.server.mngt.model.NginxNode;
import net.onebean.server.mngt.service.NginxNodeService;
import net.onebean.server.mngt.vo.NginxNodeAddReq;
import net.onebean.server.mngt.vo.NginxNodeInfoModifyReq;
import net.onebean.server.mngt.vo.NginxNodeVo;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import net.onebean.util.UagSsoUtils;
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
* @description nginx节点管理 serviceImpl
* @date 2019-03-01 12:00:33
*/
@Service
public class NginxNodeServiceImpl extends BaseBiz<NginxNode, NginxNodeDao> implements NginxNodeService{

    private final static Logger logger = LoggerFactory.getLogger(NginxNodeServiceImpl.class);
    
    @Override
    public List<NginxNodeVo> findNginxNodeVo(NginxNodeAddReq param, Pagination page, Sort sort) {

        String ipAddress = Optional.ofNullable(param).map(NginxNodeAddReq::getIpAddress).orElse(null);
        String confPath = Optional.ofNullable(param).map(NginxNodeAddReq::getConfPath).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(ipAddress)){
            Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
            condition.setValue(ipAddress);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(confPath)){
            Condition condition = Condition.parseModelCondition("confPath@string@eq");
            condition.setValue(confPath);
            paramList.add(condition);
        }
        List<NginxNode> list = this.find(page,paramList,sort);
        logger.debug("NginxNodeServiceImpl method findNginxNode list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<NginxNodeVo> res = JSON.parseArray(JSON.toJSONString(list), NginxNodeVo.class);
        logger.debug("NginxNodeServiceImpl method findNginxNode res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public NginxNodeVo findVoById(Object id) {
        NginxNode nginxNode = this.findById(id);
        NginxNodeVo nginxNodeVo = new NginxNodeVo();
        if (null != nginxNode){
            try {
                BeanUtils.copyProperties(nginxNodeVo,nginxNode);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg()+"bean of NginxNodeVo");
            }
        }else{
            return null;
        }
        return nginxNodeVo;
    }

    @Override
    public Long add(NginxNodeAddReq request) {
        NginxNode NginxNode = new NginxNode();

        try {
            BeanUtils.copyProperties(NginxNode,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        String ipAddress = Optional.ofNullable(request).map(NginxNodeAddReq::getIpAddress).orElse(null);
        if (StringUtils.isEmpty(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of ipAddress");
        }
        if (isRepeatByIpAddressAndId(ipAddress,null)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        UagSsoUtils.setUagUserInfoByHeader(NginxNode);
        this.save(NginxNode);
        return NginxNode.getId();
    }

    @Override
    public Integer update(NginxNodeInfoModifyReq request) {
        NginxNode target = new NginxNode();
        try {
            BeanUtils.copyProperties(target,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
        String ipAddress = Optional.ofNullable(request).map(NginxNodeInfoModifyReq::getIpAddress).orElse(null);
        String id = Optional.ofNullable(request).map(NginxNodeInfoModifyReq::getId).orElse(null);
        if (StringUtils.isEmpty(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of ipAddress");
        }
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of id");
        }
        if (isRepeatByIpAddressAndId(ipAddress,id)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        UagSsoUtils.setUagUserInfoByHeader(target);
        return this.update(target);
    }

    @Override
    public Boolean isRepeatByIpAddressAndId(String ipAddress,Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(ipAddress)){
            Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
            condition.setValue(ipAddress);
            param.add(condition);
        }
        if (StringUtils.isNotEmpty(id)){
            Condition condition = Condition.parseModelCondition("id@string@nq");
            condition.setValue(id);
            param.add(condition);
        }
        return CollectionUtil.isNotEmpty(this.find(null,param));
    }

    @Override
    public List<NginxNodeSyncVo> findSyncableNode() {
        List<NginxNode> list = this.findAll();
        List<NginxNodeSyncVo> res;
        if (CollectionUtil.isNotEmpty(list)){
            try {
                res = JSON.parseArray(JSON.toJSONString(list), NginxNodeSyncVo.class);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
        }else{
            res = Collections.emptyList();
        }
        return res;
    }
}