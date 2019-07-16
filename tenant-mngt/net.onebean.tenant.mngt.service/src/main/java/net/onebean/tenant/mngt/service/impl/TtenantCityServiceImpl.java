package net.onebean.tenant.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.common.exception.BusinessException;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.api.model.FIndListTenantCityResp;
import net.onebean.tenant.mngt.api.model.FIndTenantCityByNameReq;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.dao.TtenantCityDao;
import net.onebean.tenant.mngt.model.TtenantCity;
import net.onebean.tenant.mngt.service.TtenantCityService;
import net.onebean.tenant.mngt.vo.*;
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
* @description 城市 serviceImpl
* @date 2019-01-11 20:55:32
*/
@Service
public class TtenantCityServiceImpl extends BaseBiz<TtenantCity, TtenantCityDao> implements TtenantCityService{

    private final static Logger logger = LoggerFactory.getLogger(TtenantCityServiceImpl.class);
    private final static String CASCADER_MODEL_TYPE = "city";


    @SuppressWarnings("unchecked")
    @Override
    public List<CityCascaderVo> findAllForCascader(FIndListTenantCityReq req) {
        List<Condition> paramList = new ArrayList<>();
        Condition condition = Condition.parseModelCondition("provinceCode@int@eq");
        //controller 已做过空校验
        condition.setValue(req.getProvinceCode());
        paramList.add(condition);
        List<TtenantCity> list = this.find(null,paramList,new Sort(Sort.DESC,"city_sort"));
        logger.debug("TtenantCityServiceImpl method findFIndListTenantCityRespListByProvinceId list = ",JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if(CollectionUtil.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<CityCascaderVo> res = new ArrayList<>();
        for (TtenantCity city : list) {
            CityCascaderVo temp = new CityCascaderVo();
            temp.setValue(city.getCityCode());
            temp.setLabel(city.getCityName());
            temp.setType(CASCADER_MODEL_TYPE);
            res.add(temp);
        }
        if(CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public FIndListTenantCityResp findCityVoByName(FIndTenantCityByNameReq req) {
        Condition condition = Condition.parseModelCondition("cityName@string@eq");
        String cityName = Optional.ofNullable(req).map(FIndTenantCityByNameReq::getCityName).orElse(null);
        if (StringUtils.isEmpty(cityName)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_NOT_EMPTY_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_NOT_EMPTY_ERROR.msg()+"filed is cityName");
        }
        condition.setValue(cityName);
        List<TtenantCity> list = this.find(null,condition);
        FIndListTenantCityResp target = new FIndListTenantCityResp();
        if (CollectionUtil.isNotEmpty(list)){
            try {
                BeanUtils.copyProperties(target,list.get(0));
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
            }
        }else {
            return null;
        }
        return target;
    }

    @Override
    public String findCityNameByCode(String code) {
        List<Condition> paramList = new ArrayList<>();
        Condition condition = Condition.parseModelCondition("cityCode@int@eq");
        condition.setValue(code);
        paramList.add(condition);
        List<TtenantCity> list = this.find(null,paramList,new Sort(Sort.DESC,"city_sort"));
        if (CollectionUtil.isEmpty(list) || list.size() > 1){
            return null;
        }
        TtenantCity ttenantCity = list.get(0);
        return Optional.of(ttenantCity).map(TtenantCity::getCityName).orElse(null);
    }

    @Override
    public List<TtenantCityVo> findFindListTtenantCityResp(FindTenantCityReq req, Pagination page, Sort sort) {

        String cityCode = Optional.ofNullable(req).map(FindTenantCityReq::getCityCode).orElse(null);
        String provinceCode = Optional.ofNullable(req).map(FindTenantCityReq::getProvinceCode).orElse(null);
        String cityName = Optional.ofNullable(req).map(FindTenantCityReq::getCityName).orElse(null);
        
        List<Condition> param = new ArrayList<>();

        if (StringUtils.isNotEmpty(cityCode)){
            Condition condition = Condition.parseModelCondition("cityCode@string@like");
            condition.setValue(cityCode);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(provinceCode)){
            Condition condition = Condition.parseModelCondition("provinceCode@string@eq");
            condition.setValue(provinceCode);
            param.add(condition);
        }
        if (StringUtils.isNotEmpty(cityName)){
            Condition condition = Condition.parseModelCondition("cityName@string@eq");
            condition.setValue(cityName);
            param.add(condition);
        }


        List<TtenantCity> list = this.find(page,param,sort);
        logger.debug("TtenantCityServiceImpl findFindListTtenantCityResp list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if(CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }
        List<TtenantCityVo> res = JSON.parseArray(JSON.toJSONString(list), TtenantCityVo.class);
        if(CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public TtenantCityVo findVoById(String id) {
        TtenantCity city = this.findById(id);
        if (null == city){
            throw  new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(),ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        TtenantCityVo resp = new TtenantCityVo();
        try {
            BeanUtils.copyProperties(resp,city);
        } catch (Exception e) {
            logger.debug("TtenantCityServiceImpl findVoById resp create err = ",e);
            throw  new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("TtenantCityServiceImpl findVoById res = ",JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue));
        return resp;
    }

    @Override
    public Integer findMaxCode() {
        return baseDao.findMaxCode();
    }

    @Override
    public List<FindCityForAppVo> findFindCityForAppShowList() {
        List<TtenantCity> list = this.findAll();
        if(CollectionUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(),ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        List<FindCityForAppVo> res = JSON.parseArray(JSON.toJSONString(list), FindCityForAppVo.class);
        if(CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

}