package net.onebean.uag.conf.api.service;

import net.onebean.common.model.BaseResponse;
import net.onebean.uag.conf.api.fallback.NginxInfoCloudApiFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "uag-conf" ,fallback = NginxInfoCloudApiFallBack.class)
public interface NginxInfoCloudApi {

    @PostMapping(value = "/cloud/nginxInfo/syncNginxConf",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> syncNginxConf();
}
