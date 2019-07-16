package net.onebean.api.adapter.action.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.adapter.common.ErrorCodesEnum;
import net.onebean.api.adapter.service.AccessTokenService;
import net.onebean.api.adapter.service.SendSmsService;
import net.onebean.api.adapter.vo.GetAccTokenRequest;
import net.onebean.api.adapter.vo.GetAccessTokenResponse;
import net.onebean.api.adapter.vo.SendSalesLoginSmsReq;
import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.BaseResponse;
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
@RequestMapping("/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private  AccessTokenService accessTokenService;
    @Autowired
    private SendSmsService sendSmsService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/getAccessToken",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<GetAccessTokenResponse> getAccessToken(@RequestBody @Validated GetAccTokenRequest request, BindingResult result){
        BaseResponse<GetAccessTokenResponse> response = new BaseResponse<>();
        logger.info("AuthController getToken method access"+DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(accessTokenService.getAccessToken(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("AuthController getToken method BusinessException ex = ", e.getMsg());
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("AuthController getToken method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "/sendLoginSms", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> sendLoginSms(@RequestBody @Validated SendSalesLoginSmsReq request, BindingResult result) {
        logger.info("SalesAuthController sendLoginSms method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
        }
            logger.debug("SalesAuthController sendLoginSms method target = " + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(sendSmsService.sendLoginSms(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("SalesAuthController sendLoginSms method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("SalesAuthController sendLoginSms method catch Exception e = ", e);
        }
        return response;
    }



}
