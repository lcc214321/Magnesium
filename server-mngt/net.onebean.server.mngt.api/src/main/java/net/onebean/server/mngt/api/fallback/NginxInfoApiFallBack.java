package net.onebean.server.mngt.api.fallback;

import net.onebean.core.BasePaginationResponse;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.server.mngt.api.service.NginxInfoApi;

public class NginxInfoApiFallBack implements NginxInfoApi {

    @Override
    public BasePaginationResponse<NginxNodeSyncVo> findSyncNginxNode() {
        BasePaginationResponse<NginxNodeSyncVo> response = new BasePaginationResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }
}
