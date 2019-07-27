package net.onebean.user.mngt.action.auth.cloud;

import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.user.mngt.api.model.UagLoginInfo;
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
@RequestMapping("cloud/userAuth")
public class UserAuthCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UserAuthCloudController.class);


    @Autowired
    private UagUserInfoService uagUserInfoService;


    @SuppressWarnings("unchecked")
    @PostMapping(value = "checkUagLoginInfo",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> checkUagLoginInfo(@RequestBody @Validated CheckUagLoginStatusReq req, BindingResult result) {
        logger.info("checkUagLoginInfo method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.checkUagLoginStatus(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("checkUagLoginInfo method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("checkUagLoginInfo method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "uagLogOut",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> uagLogOut(@RequestBody @Validated CheckUagLoginStatusReq req, BindingResult result) {
        logger.info("uagLogOut method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.uagLogOut(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("uagLogOut method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("uagLogOut method catch Exception e = ",e);
        }
        return response;
    }
}
