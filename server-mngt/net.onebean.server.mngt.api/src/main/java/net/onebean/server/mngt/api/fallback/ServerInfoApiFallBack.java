package net.onebean.server.mngt.api.fallback;

import net.onebean.core.base.BasePaginationResponse;
import net.onebean.server.mngt.api.model.FindServerByNameReq;
import net.onebean.server.mngt.api.model.ServerBasicInfo;
import net.onebean.server.mngt.api.service.ServerInfoApi;

public class ServerInfoApiFallBack implements ServerInfoApi {
    @Override
    public BasePaginationResponse<ServerBasicInfo> findServerByName(FindServerByNameReq request) {
        BasePaginationResponse<ServerBasicInfo> response = new BasePaginationResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return null;
    }
}
