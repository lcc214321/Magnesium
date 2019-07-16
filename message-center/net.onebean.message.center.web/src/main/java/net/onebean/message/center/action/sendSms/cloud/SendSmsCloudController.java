package net.onebean.message.center.action.sendSms.cloud;

import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.BaseResponse;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.common.ErrorCodesEnum;
import net.onebean.message.center.service.SmsMessageSenderService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendSmsCloudController {


    private final static Logger logger = LoggerFactory.getLogger(SendSmsCloudController.class);

    @Autowired
    private SmsMessageSenderService smsMessageSenderService;

    @PostMapping(value = "/sendSmsMsg",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse sendSmsMsg(@RequestBody @Validated SendSmsMsgReq request, BindingResult result){
        BaseResponse response = new BaseResponse();
        logger.info("SendSmsCloudController sendSmsMsgByChannel1 method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(smsMessageSenderService.sendSmsMsgByChannel1(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("SendSmsCloudController sendSmsMsgByChannel1 method BusinessException ex = ", e.getMsg());
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("SendSmsCloudController sendSmsMsgByChannel1 method catch Exception e = ",e);
        }
        return response;
    }

}
