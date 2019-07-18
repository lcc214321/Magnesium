package net.onebean.server.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.dao.UpSteamNodeDao;
import net.onebean.server.mngt.model.UpSteamNode;
import net.onebean.server.mngt.service.UpSteamNodeService;
import net.onebean.server.mngt.vo.UpSteamNodeAddReq;
import net.onebean.server.mngt.vo.UpSteamNodeModifyReq;
import net.onebean.server.mngt.vo.UpSteamNodeVo;
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
* @description upsteam node info serviceImpl
* @date 2019-03-01 15:25:32
*/
@Service
public class UpSteamNodeServiceImpl extends BaseBiz<UpSteamNode, UpSteamNodeDao> implements UpSteamNodeService{

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNodeServiceImpl.class);


    @Override
    public List<UpSteamNodeVo> findUpSteamNodeVo(UpSteamNodeAddReq param, Pagination page, Sort sort) {

        String nodeName = Optional.ofNullable(param).map(UpSteamNodeAddReq::getNodeName).orElse(null);
        String physicalPath = Optional.ofNullable(param).map(UpSteamNodeAddReq::getPhysicalPath).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(nodeName)){
            Condition condition = Condition.parseModelCondition("nodeName@string@like");
            condition.setValue(nodeName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(physicalPath)){
            Condition condition = Condition.parseModelCondition("physicalPath@string@eq");
            condition.setValue(physicalPath);
            paramList.add(condition);
        }

        List<UpSteamNode> list = this.find(page,paramList,sort);
        logger.debug("UpSteamNodeServiceImpl method findUpSteamNode list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<UpSteamNodeVo> res = JSON.parseArray(JSON.toJSONString(list), UpSteamNodeVo.class);
        logger.debug("UpSteamNodeServiceImpl method findUpSteamNode res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public UpSteamNodeVo findVoById(Object id) {
        UpSteamNode UpSteamNode = this.findById(id);
        UpSteamNodeVo UpSteamNodeVo = new UpSteamNodeVo();
        if (null != UpSteamNode){
            try {
                BeanUtils.copyProperties(UpSteamNodeVo,UpSteamNode);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg()+"bean of UpSteamNodeVo");
            }
        }else{
            return null;
        }
        return UpSteamNodeVo;
    }

    @Override
    public Boolean isRepeatByPhysicalPath(String physicalPath,Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(physicalPath)){
            Condition condition = Condition.parseModelCondition("physicalPath@string@eq");
            condition.setValue(physicalPath);
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
    public Long add(UpSteamNodeAddReq request) {
        UpSteamNode node = new UpSteamNode();
        try {
            BeanUtils.copyProperties(node,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        String physicalPath = Optional.ofNullable(request).map(UpSteamNodeAddReq::getPhysicalPath).orElse(null);
        if (StringUtils.isEmpty(physicalPath)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of physicalPath");
        }
        if (isRepeatByPhysicalPath(physicalPath,null)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        UagSsoUtils.setUagUserInfoByHeader(node);
        this.save(node);
        return node.getId();
    }

    @Override
    public Integer update(UpSteamNodeModifyReq request) {
        UpSteamNode target = new UpSteamNode();

        try {
            BeanUtils.copyProperties(target,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        
        logger.debug("UpSteamNodeServiceImpl update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));

        String physicalPath = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getPhysicalPath).orElse(null);
        if (StringUtils.isEmpty(physicalPath)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of physicalPath");
        }
        String id = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getId).orElse(null);
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of id");
        }
        if (isRepeatByPhysicalPath(physicalPath,id)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        UagSsoUtils.setUagUserInfoByHeader(target);
        return this.update(target);
    }

    @Override
    public List<UpSteamSyncNodeVo> findSyncNode() {
        return baseDao.findSyncNode();
    }
}