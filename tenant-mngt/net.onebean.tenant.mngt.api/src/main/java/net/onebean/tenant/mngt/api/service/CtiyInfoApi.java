package net.onebean.tenant.mngt.api.service;


import net.onebean.common.model.BaseResponse;
import net.onebean.tenant.mngt.api.fallback.CtiyInfoApiFallBack;
import net.onebean.tenant.mngt.api.model.FIndListTenantCityResp;
import net.onebean.tenant.mngt.api.model.FIndTenantCityByNameReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tenant-mngt",fallback = CtiyInfoApiFallBack.class)
public interface CtiyInfoApi {

    @PostMapping(value = "/cloud/city/findCityByName",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<FIndListTenantCityResp> findCityVoByName(@RequestBody FIndTenantCityByNameReq req);
}
