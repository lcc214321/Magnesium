package net.onebean.uag.conf.api.service;

import net.onebean.common.model.BaseResponse;
import net.onebean.uag.conf.api.fallback.RsSalesSendSmsCloudApiFallBack;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "uag-conf" ,fallback = RsSalesSendSmsCloudApiFallBack.class)
public interface RsSalesSendSmsCloudApi {

    @PostMapping(value = "/RsSalesSendSmsCloud/setLoginSmsCheckCache",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> setLoginSmsCheckCache(@RequestBody SendLoginSmsReq request);
}
