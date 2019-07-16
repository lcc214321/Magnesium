package net.onebean.tenant.mngt.action.province;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.BaseResponse;
import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.model.TtenantProvince;
import net.onebean.tenant.mngt.service.TtenantProvinceService;
import net.onebean.tenant.mngt.vo.AddTtenantProvinceReq;
import net.onebean.tenant.mngt.vo.FindListTtenantProvinceReq;
import net.onebean.tenant.mngt.vo.ModifyTtenantProvinceReq;
import net.onebean.tenant.mngt.vo.TtenantProvinceVo;
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
@RequestMapping("/provinceInfo")
public class ProvinceController {

    private final static Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private TtenantProvinceService  provinceService;


    @UagOperationLog(description = "添加省份信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated AddTtenantProvinceReq  req, BindingResult result){
        logger.info("ProvinceController add method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ProvinceController add method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantProvince target = new TtenantProvince();
            BeanUtils.copyProperties(target,req);
            target.setProvinceCode(provinceService.findMaxSort().toString());
            logger.debug("ProvinceController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            provinceService.save(target);
            response = BaseResponse.ok(target.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<TtenantProvinceVo > findLisyByProvinceId(@RequestBody BasePaginationRequest<FindListTtenantProvinceReq>  request, BindingResult result){
        logger.info("ProvinceController find method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<TtenantProvinceVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("ProvinceController find method req = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            FindListTtenantProvinceReq req = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            response = BasePaginationResponse.ok(provinceService.findFindListTtenantProvinceResp(req,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController find method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findAllForCascader",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<TtenantProvinceVo> findAllForCascader(){
        logger.info("ProvinceController findAllForCascader method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<TtenantProvinceVo> response = new BasePaginationResponse<>();
        try {
            response = BasePaginationResponse.ok(provinceService.findAllForCascader());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController findAllForCascader method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController findAllForCascader method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "编辑省份信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse update(@RequestBody @Validated ModifyTtenantProvinceReq req, BindingResult result){
        logger.info("ProvinceController update method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ProvinceController update method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantProvince target = new TtenantProvince();
            BeanUtils.copyProperties(target,req);
            logger.debug("ProvinceController update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            response = BaseResponse.ok(provinceService.update(target));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController update method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController update method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "删除省份信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse delete(@RequestBody @Validated ModifyTtenantProvinceReq req, BindingResult result){
        logger.info("ProvinceController delete method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ProvinceController delete method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantProvinceReq::getId).orElse(null);
            response = BaseResponse.ok(provinceService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse findById(@RequestBody @Validated ModifyTtenantProvinceReq req, BindingResult result){
        logger.info("ProvinceController findById method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ProvinceController findById method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantProvinceReq::getId).orElse(null);
            response = BaseResponse.ok(provinceService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ProvinceController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ProvinceController findById method catch Exception e = ",e);
        }
        return response;
    }


}
