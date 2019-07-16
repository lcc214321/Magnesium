package net.onebean.server.mngt.api.fallback;

import net.onebean.common.model.BaseResponse;
import net.onebean.server.mngt.api.model.AppApiRelationSyncResqVo;
import net.onebean.server.mngt.api.model.AppApiRelationshipReq;
import net.onebean.server.mngt.api.model.AppApiRelationshipResp;
import net.onebean.server.mngt.api.model.FindApiByAppIdReq;
import net.onebean.server.mngt.api.service.ApiInfoApi;

public class ApiInfoApiFallBack implements ApiInfoApi {
    @Override
    public BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(AppApiRelationshipReq request) {
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }

    @Override
    public BaseResponse<AppApiRelationshipResp> findApiByAppId(FindApiByAppIdReq request) {
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }

    @Override
    public BaseResponse<AppApiRelationSyncResqVo> findSyncAppApiRelationship() {
        BaseResponse<AppApiRelationSyncResqVo> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }
}
