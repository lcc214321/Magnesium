package net.onebean.uag.conf.action.rsLoginInfo.cloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.uag.conf.service.CacheRsSalesLoginInfoService;
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
@RequestMapping("sendSmsCloudController")
public class SendSmsCloudController {

    private final static Logger logger = LoggerFactory.getLogger(SendSmsCloudController.class);

    @Autowired
    private CacheRsSalesLoginInfoService cacheRsSalesLoginInfoService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "setLoginSmsCheckCache",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> setLoginSmsCheckCache(@RequestBody @Validated SendLoginSmsReq request, BindingResult result) {
        logger.info("SalesAuthController sendLoginSms method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("SalesAuthController sendLoginSms method target = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(cacheRsSalesLoginInfoService.cacheSmsInfo(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("SalesAuthController sendLoginSms method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("SalesAuthController sendLoginSms method catch Exception e = ",e);
        }
        return response;
    }
}
