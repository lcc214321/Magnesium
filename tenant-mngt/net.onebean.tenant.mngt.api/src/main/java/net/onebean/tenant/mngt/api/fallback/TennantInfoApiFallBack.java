package net.onebean.tenant.mngt.api.fallback;

import net.onebean.core.BaseResponse;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoBatchSyncFlagReq;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoStatusReq;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import net.onebean.tenant.mngt.api.service.TennantInfoApi;
import org.springframework.stereotype.Component;

@Component
public class TennantInfoApiFallBack implements TennantInfoApi {

    @Override
    public BaseResponse<FindTtenantInfoVo> findTenantInfoByTenantId(String id) {
        BaseResponse<FindTtenantInfoVo> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        return baseResponse;
    }

    @Override
    public BaseResponse<TenantCityInfoVo> findTenantCityInfoByTenantId(String id) {
        BaseResponse<TenantCityInfoVo> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        return baseResponse;
    }

    @Override
    public BaseResponse updateBatchSyncFlag(ModifyTtenantInfoBatchSyncFlagReq req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrCode("999");
        return baseResponse;
    }

    @Override
    public BaseResponse updateStatus(ModifyTtenantInfoStatusReq req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrCode("999");
        return baseResponse;
    }

}
