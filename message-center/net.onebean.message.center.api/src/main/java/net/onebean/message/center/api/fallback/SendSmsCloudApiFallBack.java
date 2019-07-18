package net.onebean.message.center.api.fallback;

import net.onebean.core.BaseResponse;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.api.service.SendSmsCloudApi;

public class SendSmsCloudApiFallBack implements SendSmsCloudApi {

    @Override
    public BaseResponse sendSmsMsg(SendSmsMsgReq request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }
}
