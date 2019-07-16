package net.onebean.tenant.mngt.api.fallback;


import net.onebean.common.model.BaseResponse;
import net.onebean.tenant.mngt.api.model.FIndListTenantCityResp;
import net.onebean.tenant.mngt.api.model.FIndTenantCityByNameReq;
import net.onebean.tenant.mngt.api.service.CtiyInfoApi;
import org.springframework.stereotype.Component;

@Component
public class CtiyInfoApiFallBack implements CtiyInfoApi {

    @Override
    public BaseResponse<FIndListTenantCityResp> findCityVoByName(FIndTenantCityByNameReq req) {
        BaseResponse<FIndListTenantCityResp> resp = new BaseResponse<>();
        resp.setErrCode("999");
        resp.setErrMsg("FallBack error");
        return resp;
    }
}
