package net.onebean.server.mngt.api.service;

import net.onebean.core.BasePaginationResponse;
import net.onebean.server.mngt.api.fallback.UpSteamInfoApiFallBack;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "server-mngt",fallback = UpSteamInfoApiFallBack.class)
public interface UpSteamInfoApi {

    @PostMapping(value = "/cloud/upSteamNodeInfo/findSyncUpSteamNode",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UpSteamSyncNodeVo> findSyncUpSteamNode();


}
