package net.onebean.tenant.mngt.action.city;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.model.TtenantCity;
import net.onebean.tenant.mngt.service.TtenantCityService;
import net.onebean.tenant.mngt.vo.*;
import net.onebean.uag.log.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
import net.onebean.util.UagSsoUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cityInfo")
public class CityController {

    @Autowired
    private TtenantCityService ttenantCityService;

    private final static Logger logger = LoggerFactory.getLogger(CityController.class);


    @UagOperationLog(description = "添加城市信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse add(@RequestBody @Validated AddTtenantCityReq req, BindingResult result){
        logger.info("CityController add method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse response = new BasePaginationResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("CityController add method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantCity target = new TtenantCity();
            BeanUtils.copyProperties(target,req);
            target.setCityCode(ttenantCityService.findMaxCode().toString());
            logger.debug("CityController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            ttenantCityService.save(target);
            response = BasePaginationResponse.ok(target.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<TtenantCityVo> findLisyByProvinceId(@RequestBody BasePaginationRequest<FindTenantCityReq>  request){
        logger.info("CityController findList method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<TtenantCityVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("CityController findList method req = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(null);
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            FindTenantCityReq req = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            response = BasePaginationResponse.ok(ttenantCityService.findFindListTtenantCityResp(req,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController findList method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController findList method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findAllForCascader",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<CityCascaderVo> findAllForCascader(@RequestBody @Validated FIndListTenantCityReq req, BindingResult result){
        logger.info("CityController findList method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<CityCascaderVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("CityController findListByProvinceCode method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            response = BasePaginationResponse.ok(ttenantCityService.findAllForCascader(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController findList method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController findList method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除城市信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse delete(@RequestBody @Validated ModifyTtenantCityReq req, BindingResult result){
        logger.info("CityController delete method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse response = new BasePaginationResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("CityController delete method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantCityReq::getId).orElse(null);
            response = BasePaginationResponse.ok(ttenantCityService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse findById(@RequestBody @Validated ModifyTtenantCityReq req, BindingResult result){
        logger.info("CityController findById method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse response = new BasePaginationResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("CityController findById method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantCityReq::getId).orElse(null);
            response = BasePaginationResponse.ok(ttenantCityService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController findById method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findFindCityForAppShowList",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<FindCityForAppVo> findFindCityForAppShowList(){
        logger.info("method findFindCityForAppShowList access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<FindCityForAppVo> response = new BasePaginationResponse<>();
        try {
            response = BasePaginationResponse.ok(ttenantCityService.findFindCityForAppShowList());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("method findFindCityForAppShowList BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("method findFindCityForAppShowList catch Exception e = ",e);
        }
        return response;
    }

}
