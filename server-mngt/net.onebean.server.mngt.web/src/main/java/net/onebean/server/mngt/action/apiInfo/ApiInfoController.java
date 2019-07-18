package net.onebean.server.mngt.action.apiInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Json.EnableEnumDeserialize;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.model.ApiInfo;
import net.onebean.server.mngt.service.ApiInfoService;
import net.onebean.server.mngt.vo.ApiInfoAddReq;
import net.onebean.server.mngt.vo.ApiInfoFindReq;
import net.onebean.server.mngt.vo.ApiInfoVo;
import net.onebean.server.mngt.vo.ApiModifyReq;
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
@RequestMapping("/apiInfo")
public class ApiInfoController {


    private final static Logger logger = LoggerFactory.getLogger(ApiInfoController.class);

    @Autowired
    private ApiInfoService apiInfoService;


    @UagOperationLog(description = "添加API信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated ApiInfoAddReq  request, BindingResult result){
        logger.info("add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            ApiInfo apiInfo = new ApiInfo();
            BeanUtils.copyProperties(apiInfo,request);
            UagSsoUtils.setUagUserInfoByHeader(apiInfo);
            apiInfoService.save(apiInfo);
            response = BaseResponse.ok(apiInfo.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @EnableEnumDeserialize
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<ApiInfoVo> find(@RequestBody BasePaginationRequest<ApiInfoFindReq> request){
        logger.info("find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ApiInfoVo> response = new BasePaginationResponse<>();
        try {
            ApiInfoFindReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            logger.debug("find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BasePaginationResponse.ok(apiInfoService.findApiInfoVo(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "编辑API信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse update(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            ApiInfo target = new ApiInfo();
            BeanUtils.copyProperties(target,request);
            logger.debug("update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            response = BaseResponse.ok(apiInfoService.update(target));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("update method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除API信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse delete(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(request).map(ApiModifyReq::getId).orElse(null);
            response = BaseResponse.ok(apiInfoService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<ApiInfoVo> findById(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ApiInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(request).map(ApiModifyReq::getId).orElse(null);
            response = BaseResponse.ok(apiInfoService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }




}
