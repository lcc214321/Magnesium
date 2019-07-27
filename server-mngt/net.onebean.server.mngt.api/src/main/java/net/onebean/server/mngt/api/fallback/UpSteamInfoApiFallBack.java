package net.onebean.server.mngt.api.fallback;

import net.onebean.core.base.BasePaginationResponse;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.server.mngt.api.service.UpSteamInfoApi;

public class UpSteamInfoApiFallBack implements UpSteamInfoApi {
    @Override
    public BasePaginationResponse<UpSteamSyncNodeVo> findSyncUpSteamNode() {
        BasePaginationResponse<UpSteamSyncNodeVo> response = new BasePaginationResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }
}
