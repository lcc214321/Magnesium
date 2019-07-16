package net.onebean.server.mngt.api.service;

import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import net.onebean.server.mngt.api.fallback.NginxInfoApiFallBack;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "server-mngt",fallback = NginxInfoApiFallBack.class)
public interface NginxInfoApi {

    @PostMapping(value = "/cloud/nginxInfo/findSyncNginxNode",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<NginxNodeSyncVo> findSyncNginxNode();


}
