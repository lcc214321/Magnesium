package net.onebean.server.mngt.api.service;

import net.onebean.core.BaseResponse;
import net.onebean.server.mngt.api.fallback.ApiInfoApiFallBack;
import net.onebean.server.mngt.api.model.AppApiRelationSyncResqVo;
import net.onebean.server.mngt.api.model.AppApiRelationshipReq;
import net.onebean.server.mngt.api.model.AppApiRelationshipResp;
import net.onebean.server.mngt.api.model.FindApiByAppIdReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server-mngt",fallback = ApiInfoApiFallBack.class)
public interface ApiInfoApi {

    @PostMapping(value = "/cloud/apiInfo/findAppApiRelationshipByServerIdAndAppId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(@RequestBody @Validated AppApiRelationshipReq request);

    @PostMapping(value = "/cloud/apiInfo/findApiByAppId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppApiRelationshipResp> findApiByAppId(@RequestBody @Validated FindApiByAppIdReq request);

    @PostMapping(value = "/cloud/apiInfo/findSyncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppApiRelationSyncResqVo> findSyncAppApiRelationship();
}
