package net.onebean.server.mngt.action.apiInfo.cloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.server.mngt.api.model.AppApiRelationSyncResqVo;
import net.onebean.server.mngt.api.model.AppApiRelationshipReq;
import net.onebean.server.mngt.api.model.AppApiRelationshipResp;
import net.onebean.server.mngt.api.model.FindApiByAppIdReq;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.service.ApiInfoService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/apiInfo")
public class ApiInfoCloudController {


    private final static Logger logger = LoggerFactory.getLogger(ApiInfoCloudController.class);

    @Autowired
    private ApiInfoService apiInfoService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findAppApiRelationshipByServerIdAndAppId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(@RequestBody @Validated AppApiRelationshipReq request, BindingResult result){
        logger.info("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(apiInfoService.findAppApiRelationshipByServerIdAndAppId(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findApiByAppId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppApiRelationshipResp> findApiByAppId(@RequestBody @Validated FindApiByAppIdReq request, BindingResult result){
        logger.info("ApiInfoCloudController findApiByAppId method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ApiInfoCloudController findApiByAppId method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(apiInfoService.findApiByAppId(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ApiInfoCloudController findApiByAppId method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ApiInfoCloudController findApiByAppId method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findSyncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppApiRelationSyncResqVo> findSyncAppApiRelationship(){
        logger.info("ApiInfoCloudController findSyncAppApiRelationship method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppApiRelationSyncResqVo> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(apiInfoService.findSyncAppApiRelationship());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ApiInfoCloudController findSyncAppApiRelationship method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ApiInfoCloudController findSyncAppApiRelationship method catch Exception e = ",e);
        }
        return response;
    }


}
