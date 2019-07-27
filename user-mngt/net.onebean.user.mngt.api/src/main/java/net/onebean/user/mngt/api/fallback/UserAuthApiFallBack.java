package net.onebean.user.mngt.api.fallback;

import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import net.onebean.user.mngt.api.service.UserAuthApi;

public class UserAuthApiFallBack implements UserAuthApi {
    @Override
    public BaseResponse<UagLoginInfo> checkUagLoginInfo(CheckUagLoginStatusReq req) {
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }

    @Override
    public BaseResponse<Boolean> uagLogOut(CheckUagLoginStatusReq req) {
        BaseResponse<Boolean> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }
}
