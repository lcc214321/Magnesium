package net.onebean.server.mngt.api.service;

import net.onebean.core.base.BasePaginationResponse;
import net.onebean.server.mngt.api.fallback.ServerInfoApiFallBack;
import net.onebean.server.mngt.api.model.FindServerByNameReq;
import net.onebean.server.mngt.api.model.ServerBasicInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server-mngt",fallback = ServerInfoApiFallBack.class)
public interface ServerInfoApi {

    @PostMapping(value = "/cloud/serverInfo/findServerByName",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<ServerBasicInfo> findServerByName(@RequestBody @Validated FindServerByNameReq request);
}
