package net.onebean.app.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.app.mngt.common.ErrorCodesEnum;
import net.onebean.app.mngt.dao.IpWhiteListDao;
import net.onebean.app.mngt.model.IpWhiteList;
import net.onebean.app.mngt.service.IpWhiteListService;
import net.onebean.app.mngt.vo.IpWhiteListAddReq;
import net.onebean.app.mngt.vo.IpWhiteListQueryReq;
import net.onebean.app.mngt.api.model.IpWhiteListVo;
import net.onebean.common.exception.BusinessException;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
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
* @author 0neBeen
* @description ip白名单 serviceImpl
* @date 2019-02-21 15:48:15
*/
@Service
public class IpWhiteListServiceImpl extends BaseBiz<IpWhiteList, IpWhiteListDao> implements IpWhiteListService{

    private final static Logger logger = LoggerFactory.getLogger(IpWhiteListServiceImpl.class);

    @Override
    public List<IpWhiteListVo> findByIpWhiteListQueryReq(IpWhiteListQueryReq param, Pagination page, Sort sort) {

        String ipAddress = Optional.ofNullable(param).map(IpWhiteListQueryReq::getIpAddress).orElse(null);
        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(ipAddress)) {
            Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
            condition.setValue(ipAddress);
            paramList.add(condition);
        }
        //查出所有数据不分页
        List<IpWhiteList> list = this.find(null,paramList,sort);
        logger.debug("IpWhiteListServiceImpl method findByIpWhiteListQueryReq list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<IpWhiteListVo> res = JSON.parseArray(JSON.toJSONString(list), IpWhiteListVo.class);
        logger.debug("IpWhiteListServiceImpl method findByIpWhiteListQueryReq res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public Boolean isRepeatIpAddress(String ipAddress) {
        Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
        condition.setValue(ipAddress);
        return CollectionUtil.isNotEmpty(this.find(null,condition));
    }

    @Override
    public void save(IpWhiteList entity) {
        if (this.isRepeatIpAddress(entity.getIpAddress())){
            throw new BusinessException(ErrorCodesEnum.REPEAT_DATA_ERR.code(),ErrorCodesEnum.REPEAT_DATA_ERR.msg());
        }
        super.save(entity);
    }

    @Override
    public Boolean isRepeatByIpAddress(String ipAddress) {
        Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
        condition.setValue(ipAddress);
        return CollectionUtil.isNotEmpty(this.find(null,condition));
    }

    @Override
    public Long add(IpWhiteListAddReq request) {
        IpWhiteList dto = new IpWhiteList();

        try {
            BeanUtils.copyProperties(dto,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        String ipAddress = Optional.ofNullable(request).map(IpWhiteListAddReq::getIpAddress).orElse(null);
        if (StringUtils.isEmpty(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of ipAddress");
        }
        if (isRepeatByIpAddress(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        UagSsoUtils.setUagUserInfoByHeader(dto);
        this.save(dto);
        return dto.getId();
    }

    @Override
    public List<IpWhiteListVo> findSyncList() {
        return this.findByIpWhiteListQueryReq(new IpWhiteListQueryReq(),null,new Sort(Sort.DESC,"id"));
    }
}