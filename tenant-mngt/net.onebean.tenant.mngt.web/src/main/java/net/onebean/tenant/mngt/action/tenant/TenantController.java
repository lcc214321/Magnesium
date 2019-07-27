package net.onebean.tenant.mngt.action.tenant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.Json.EnableEnumDeserialize;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoReq;
import net.onebean.tenant.mngt.biz.model.TtenantInfoModifyResp;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.model.TtenantInfo;
import net.onebean.tenant.mngt.service.TtenantInfoService;
import net.onebean.tenant.mngt.vo.AddTtenantInfoReq;
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
@RequestMapping("/tenantInfo")
public class TenantController {

    private final static Logger logger = LoggerFactory.getLogger(TenantController.class);
    private final static String unSyncType = "0";

    @Autowired
    private TtenantInfoService ttenantInfoService;


    @UagOperationLog(description = "添加租户信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated AddTtenantInfoReq req, BindingResult result){
        logger.info("TenantController add method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("TenantController add method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantInfo target = new TtenantInfo();
            BeanUtils.copyProperties(target,req);
            target.setIsSync(unSyncType);
            logger.debug("TenantController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            ttenantInfoService.save(target);
            response = BaseResponse.ok(target.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @EnableEnumDeserialize
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<TtenantInfoModifyResp> find(@RequestBody @Validated BasePaginationRequest<FindTtenantInfoVo> request, BindingResult result){
        logger.info("TenantController find method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<TtenantInfoModifyResp> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            FindTtenantInfoVo req = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            response = BasePaginationResponse.ok(ttenantInfoService.findListByVo(req,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController find method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse findById(@RequestBody @Validated ModifyTtenantInfoReq req, BindingResult result){
        logger.info("TenantController findById method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("TenantController findById method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantInfoReq::getId).orElse(null);
            response = BaseResponse.ok(ttenantInfoService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController findById method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "删除租户信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse delete(@RequestBody @Validated ModifyTtenantInfoReq req, BindingResult result){
        logger.info("TenantController delete method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("TenantController delete method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantInfoReq::getId).orElse(null);
            response = BaseResponse.ok(ttenantInfoService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController delete method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "编辑租户信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse update(@RequestBody @Validated ModifyTtenantInfoReq req, BindingResult result){
        logger.info("TenantController update method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("TenantController update method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantInfo target = new TtenantInfo();
            BeanUtils.copyProperties(target,req);
            target.setIsSync(unSyncType);
            logger.debug("TenantController update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            response = BaseResponse.ok(ttenantInfoService.update(target));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController update method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController update method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "初始化租户账户信息")
    @PostMapping(value = "/initAccount",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse initAccount(@RequestBody @Validated ModifyTtenantInfoReq req, BindingResult result){
        logger.info("TenantController initAccount method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("TenantController initAccount method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(req).map(ModifyTtenantInfoReq::getId).orElse(null);
            response = BaseResponse.ok(ttenantInfoService.sendInitAccountMsg(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController initAccount method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController initAccount method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "同步租户账户下发信息")
    @PostMapping(value = "/sync",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse sync(){
        logger.info("TenantController sync method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            response = BaseResponse.ok(ttenantInfoService.sync());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("TenantController sync method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("TenantController sync method catch Exception e = ",e);
        }
        return response;
    }

}
