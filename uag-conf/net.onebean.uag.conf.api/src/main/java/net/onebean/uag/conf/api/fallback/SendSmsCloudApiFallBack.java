package net.onebean.uag.conf.api.fallback;

import net.onebean.core.base.BaseResponse;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import net.onebean.uag.conf.api.service.SendSmsCloudApi;

public class SendSmsCloudApiFallBack implements SendSmsCloudApi {

    @Override
    public BaseResponse<Boolean> setLoginSmsCheckCache(SendLoginSmsReq request) {
        BaseResponse<Boolean> resp = new BaseResponse<>();
        resp.setErrCode("999");
        resp.setErrMsg("fall back");
        return resp;
    }
}
