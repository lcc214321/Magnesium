package net.onebean.tenant.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoBatchSyncFlagReq;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import net.onebean.tenant.mngt.api.model.TenantInfoSyncVo;
import net.onebean.tenant.mngt.biz.model.TtenantInfoModifyResp;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.dao.TtenantInfoDao;
import net.onebean.tenant.mngt.model.TtenantInfo;
import net.onebean.tenant.mngt.provider.mq.DevopsInitTenantAccountSender;
import net.onebean.tenant.mngt.provider.mq.DevopsSyncTenantAccountSender;
import net.onebean.tenant.mngt.service.TtenantInfoService;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author 0neBean
* @description 租户信息 serviceImpl
* @date 2019-01-11 20:56:12
*/
@Service
public class TtenantInfoServiceImpl extends BaseBiz<TtenantInfo, TtenantInfoDao> implements TtenantInfoService{

    private final static Logger logger = LoggerFactory.getLogger(TtenantInfoServiceImpl.class);
    @Autowired
    private DevopsInitTenantAccountSender devopsInitAccountSender;

    @Autowired
    private DevopsSyncTenantAccountSender syncTenantAccountSender;

    @Autowired
    private TtenantInfoDao ttenantInfoDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<TtenantInfoModifyResp> findListByVo(FindTtenantInfoVo req, Pagination page, Sort sort) {
        String id = Optional.ofNullable(req).map(FindTtenantInfoVo::getId).orElse(null);
        String email = Optional.ofNullable(req).map(FindTtenantInfoVo::getEmail).orElse(null);
        String tenantName = Optional.ofNullable(req).map(FindTtenantInfoVo::getTenantName).orElse(null);
        String principal = Optional.ofNullable(req).map(FindTtenantInfoVo::getPrincipal).orElse(null);
        String mobile = Optional.ofNullable(req).map(FindTtenantInfoVo::getMobile).orElse(null);
        String idCardNumber = Optional.ofNullable(req).map(FindTtenantInfoVo::getIdCardNumber).orElse(null);
        String corporateBankAccount = Optional.ofNullable(req).map(FindTtenantInfoVo::getCorporateBankAccount).orElse(null);
        String corporateAccountName = Optional.ofNullable(req).map(FindTtenantInfoVo::getCorporateAccountName).orElse(null);
        String bankType = Optional.ofNullable(req).map(FindTtenantInfoVo::getBankType).orElse(null);
        String agreeGuaranteeCash = Optional.ofNullable(req).map(FindTtenantInfoVo::getAgreeGuaranteeCash).orElse(null);
        BigDecimal guaranteeCash = Optional.ofNullable(req).map(FindTtenantInfoVo::getGuaranteeCash).orElse(null);
        Integer provinceCode = Optional.ofNullable(req).map(FindTtenantInfoVo::getProvinceCode).orElse(null);
        Integer cityCode = Optional.ofNullable(req).map(FindTtenantInfoVo::getCityCode).orElse(null);
        String isSubsidiaryCompany = Optional.ofNullable(req).map(FindTtenantInfoVo::getId).orElse(null);
        List<Condition> param = new ArrayList<>();

        if (StringUtils.isNotEmpty(id)){
            Condition condition = Condition.parseModelCondition("id@int@eq");
            condition.setValue(id);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(email)){
            Condition condition = Condition.parseModelCondition("email@string@eq");
            condition.setValue(email);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(tenantName)){
            Condition condition = Condition.parseModelCondition("tenantName@string@like");
            condition.setValue(tenantName);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(principal)){
            Condition condition = Condition.parseModelCondition("principal@string@like");
            condition.setValue(principal);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(mobile)){
            Condition condition = Condition.parseModelCondition("mobile@string@eq");
            condition.setValue(mobile);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(idCardNumber)){
            Condition condition = Condition.parseModelCondition("idCardNumber@string@eq");
            condition.setValue(idCardNumber);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(corporateBankAccount)){
            Condition condition = Condition.parseModelCondition("corporateBankAccount@string@eq");
            condition.setValue(corporateBankAccount);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(corporateAccountName)){
            Condition condition = Condition.parseModelCondition("corporateAccountName@string@like");
            condition.setValue(corporateAccountName);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(bankType)){
            Condition condition = Condition.parseModelCondition("bankType@string@eq");
            condition.setValue(bankType);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(agreeGuaranteeCash)){
            Condition condition = Condition.parseModelCondition("agreeGuaranteeCash@string@eq");
            condition.setValue(agreeGuaranteeCash);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(guaranteeCash)){
            Condition condition = Condition.parseModelCondition("mobile@string@eq");
            condition.setValue(guaranteeCash);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(provinceCode)){
            Condition condition = Condition.parseModelCondition("provinceCode@int@eq");
            condition.setValue(provinceCode);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(cityCode)){
            Condition condition = Condition.parseModelCondition("cityCode@int@eq");
            condition.setValue(cityCode);
            param.add(condition);
        }

        if (StringUtils.isNotEmpty(isSubsidiaryCompany)){
            Condition condition = Condition.parseModelCondition("isSubsidiaryCompany@string@eq");
            condition.setValue(isSubsidiaryCompany);
            param.add(condition);
        }

        List<TtenantInfo> list = this.find(page,param,sort);
        logger.debug("TtenantInfoServiceImpl findListByVo list = ",JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            list = Collections.EMPTY_LIST;
        }
        List<TtenantInfoModifyResp> res = JSON.parseArray(JSON.toJSONString(list), TtenantInfoModifyResp.class);
        logger.debug("TtenantInfoServiceImpl findListByVo res = ",JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        return res;
    }

    @Override
    public FindTtenantInfoVo findVoById(String id) {
        TtenantInfo ttenantInfo = this.findById(id);
        if (null == ttenantInfo){
            throw  new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(),ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        FindTtenantInfoVo resp = new FindTtenantInfoVo();
        try {
            BeanUtils.copyProperties(resp,ttenantInfo);
        } catch (Exception e) {
            logger.debug("TtenantInfoServiceImpl findListByVo resp create err = ",e);
            throw  new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("TtenantInfoServiceImpl findListByVo res = ",JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue));
        return resp;
    }

    @Override
    public TenantCityInfoVo findTenantCityInfoByTenantId(String tenantId) {
        return baseDao.findTenantCityInfoByTenantId(tenantId);
    }

    @Override
    public Boolean sendInitAccountMsg(String id) {
        try {
            FindTtenantInfoVo vo = this.findVoById(id);
            devopsInitAccountSender.send(vo);
        } catch (Exception e) {
            logger.error("sendInitAccountMsg get error = ",e);
            throw new BusinessException(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return true;
    }

    @Override
    public Boolean sync() {
        try {
            List<TenantInfoSyncVo> unSync = baseDao.findByUnSyncStatus();
            syncTenantAccountSender.send(unSync);
        } catch (Exception e) {
            logger.error("sync get error = ",e);
            throw new BusinessException(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return true;
    }

    @Override
    public Integer updateBatchSyncFlag(ModifyTtenantInfoBatchSyncFlagReq req) {
        String ids = Optional.ofNullable(req).map(ModifyTtenantInfoBatchSyncFlagReq::getIds).orElse("");
        if(StringUtils.isEmpty(ids)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }
        TtenantInfo target = new TtenantInfo();
        try {
            BeanUtils.copyProperties(target,req);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("updateBatchSyncFlag updateBatch method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
        String[] idsArr = ids.split(",");
        List<Long> idsList = new ArrayList<>();
        for (String s : idsArr) {
            idsList.add(Parse.toLong(s));
        }
        if (CollectionUtil.isEmpty(idsList)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        return this.updateBatch(target,idsList);
    }

}