package net.onebean.user.mngt.action.auth;

import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.user.mngt.vo.PasswordLoginReq;
import net.onebean.user.mngt.vo.SmsCodeLoginRegisterReq;
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
@RequestMapping("userAuth")
public class UserAuthController {


    private final static Logger logger = LoggerFactory.getLogger(UserAuthController.class);


    @Autowired
    private UagUserInfoService uagUserInfoService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "smsCodeLoginRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeLoginRegister(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeLoginRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.smsCodeLoginRegister(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("smsCodeLoginRegister method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("smsCodeLoginRegister method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "smsCodeLogin",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeLogin(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeLogin method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.smsCodeLogin(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("smsCodeLogin method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("smsCodeLogin method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "smsCodeRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeRegister(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.smsCodeRegister(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("smsCodeRegister method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("smsCodeRegister method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "passwordLogin",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> passwordLogin(@RequestBody @Validated PasswordLoginReq req, BindingResult result){
        logger.info("passwordLogin method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.passwordLogin(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("passwordLogin method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("passwordLogin method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "passwordRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> passwordRegister(@RequestBody @Validated PasswordLoginReq req, BindingResult result){
        logger.info("passwordRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.passwordRegister(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("passwordRegister method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("passwordRegister method catch Exception e = ",e);
        }
        return response;
    }



}
