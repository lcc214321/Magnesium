package net.onebean.uag.conf.api.fallback;

import net.onebean.common.model.BaseResponse;
import net.onebean.uag.conf.api.service.NginxInfoCloudApi;

public class NginxInfoCloudApiFallBack implements NginxInfoCloudApi {
    @Override
    public BaseResponse<Boolean> syncNginxConf() {
        BaseResponse<Boolean> resp = new BaseResponse<>();
        resp.setErrCode("999");
        resp.setErrMsg("fall back");
        return resp;
    }
}


