package net.onebean.app.mngt.api.fallback;

import net.onebean.app.mngt.api.model.*;
import net.onebean.app.mngt.api.service.AppInfoApi;
import net.onebean.common.model.BaseResponse;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import org.springframework.stereotype.Component;

@Component
public class AppInfoApiFallBack implements AppInfoApi {
    @Override
    public BaseResponse<AppInfoCloudVo> findByAppIdAndSecret(FindAppByAppIdAndSecretReq request) {
        BaseResponse<AppInfoCloudVo> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<AppInfoSyncCloudResp> findBindAppInfo() {
        BasePaginationResponse<AppInfoSyncCloudResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<UagSyncDataResp> getUagSyncData() {
        BaseResponse<UagSyncDataResp> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<FindBasicMenuListResp> findBasicMenuList() {
        BasePaginationResponse<FindBasicMenuListResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList() {
        BasePaginationResponse<FindUagUserAppListResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }
}
