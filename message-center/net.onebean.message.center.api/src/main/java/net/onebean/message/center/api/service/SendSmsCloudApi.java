package net.onebean.message.center.api.service;

import net.onebean.common.model.BaseResponse;
import net.onebean.message.center.api.fallback.SendSmsCloudApiFallBack;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-center",fallback = SendSmsCloudApiFallBack.class)
public interface SendSmsCloudApi {


    @PostMapping(value = "/sendSmsMsg",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse sendSmsMsg(@RequestBody @Validated SendSmsMsgReq request);
}
