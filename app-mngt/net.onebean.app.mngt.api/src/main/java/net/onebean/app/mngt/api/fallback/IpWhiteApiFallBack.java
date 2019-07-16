package net.onebean.app.mngt.api.fallback;

import net.onebean.app.mngt.api.model.IpWhiteListVo;
import net.onebean.app.mngt.api.service.IpWhiteApi;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;

public class IpWhiteApiFallBack implements IpWhiteApi {

    @Override
    public BasePaginationResponse<IpWhiteListVo> find() {
        BasePaginationResponse<IpWhiteListVo> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }
}
