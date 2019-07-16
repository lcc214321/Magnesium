package net.onebean.uag.conf.api.fallback;

import net.onebean.common.model.BaseResponse;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import net.onebean.uag.conf.api.service.RsSalesSendSmsCloudApi;

public class RsSalesSendSmsCloudApiFallBack implements RsSalesSendSmsCloudApi {

    @Override
    public BaseResponse setLoginSmsCheckCache(SendLoginSmsReq request) {
        BaseResponse<Boolean> resp = new BaseResponse<>();
        resp.setErrCode("999");
        resp.setErrMsg("fall back");
        return resp;
    }
}
