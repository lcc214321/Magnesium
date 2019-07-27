package net.onebean.tenant.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.dao.TtenantProvinceDao;
import net.onebean.tenant.mngt.model.TtenantProvince;
import net.onebean.tenant.mngt.service.TtenantProvinceService;
import net.onebean.tenant.mngt.vo.CascaderProvinceVo;
import net.onebean.tenant.mngt.vo.FindListTtenantProvinceReq;
import net.onebean.tenant.mngt.vo.TtenantProvinceVo;
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
* @description 省份 serviceImpl
* @date 2019-01-11 20:54:32
*/
@Service
public class TtenantProvinceServiceImpl extends BaseBiz<TtenantProvince, TtenantProvinceDao> implements TtenantProvinceService{

    private final static Logger logger = LoggerFactory.getLogger(TtenantProvinceServiceImpl.class);
    private final static String CASCADER_MODEL_TYPE = "province";

    @Override
    public List<TtenantProvinceVo> findFindListTtenantProvinceResp(FindListTtenantProvinceReq req, Pagination page, Sort sort) {

        String provinceName = Optional.ofNullable(req).map(FindListTtenantProvinceReq::getProvinceName).orElse(null);
        String provinceCode = Optional.ofNullable(req).map(FindListTtenantProvinceReq::getProvinceCode).orElse(null);
        List<Condition> param = new ArrayList<>();

        if (StringUtils.isNotEmpty(provinceName)){
            Condition condition = Condition.parseModelCondition("provinceName@string@like");
            condition.setValue(provinceName);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(provinceCode)){
            Condition condition = Condition.parseModelCondition("provinceCode@string@eq");
            condition.setValue(provinceCode);
            param.add(condition);
        }


        List<TtenantProvince> list = this.find(page,param,sort);
        logger.debug("TtenantProvinceServiceImpl findFindListTtenantProvinceResp list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if(CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }
        List<TtenantProvinceVo> res = JSON.parseArray(JSON.toJSONString(list), TtenantProvinceVo.class);
        if(CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public String findProvinceNameByCode(String code) {
        List<Condition> paramList = new ArrayList<>();
        Condition condition = Condition.parseModelCondition("provinceCode@int@eq");
        condition.setValue(code);
        paramList.add(condition);
        List<TtenantProvince> list = this.find(null,paramList,new Sort(Sort.DESC,"pro_sort"));
        if (CollectionUtil.isEmpty(list) || list.size() > 1){
            return null;
        }
        TtenantProvince province = list.get(0);
        return Optional.of(province).map(TtenantProvince::getProvinceName).orElse(null);
    }


    @Override
    public TtenantProvinceVo findVoById(String id) {
        TtenantProvince TtenantProvince = this.findById(id);
        if (null == TtenantProvince){
            throw  new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(),ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        TtenantProvinceVo resp = new TtenantProvinceVo();
        try {
            BeanUtils.copyProperties(resp,TtenantProvince);
        } catch (Exception e) {
            logger.debug("TtenantProvinceServiceImpl findVoById resp create err = ",e);
            throw  new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("TtenantProvinceServiceImpl findVoById res = ",JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue));
        return resp;
    }

    @Override
    public Integer findMaxSort() {
        return baseDao.findMaxCode();
    }

    @Override
    public List<CascaderProvinceVo> findAllForCascader() {
        List<TtenantProvince> list = this.findAll();
        List<CascaderProvinceVo> res = new ArrayList<>();
        for (TtenantProvince province : list) {
            CascaderProvinceVo temp = new CascaderProvinceVo();
            temp.setValue(province.getProvinceCode());
            temp.setLabel(province.getProvinceName());
            temp.setType(CASCADER_MODEL_TYPE);
            temp.setLoading(false);
            res.add(temp);
        }
        return res;
    }
}