package net.onebean.app.mngt.api.service;

import net.onebean.app.mngt.api.fallback.AppInfoApiFallBack;
import net.onebean.app.mngt.api.model.*;
import net.onebean.common.model.BaseResponse;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "app-mngt",fallback = AppInfoApiFallBack.class)
public interface AppInfoApi {

    @PostMapping(value = "/cloud/appInfo/findByAppIdAndSecret",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppInfoCloudVo> findByAppIdAndSecret(@RequestBody FindAppByAppIdAndSecretReq request);

    @PostMapping(value = "/cloud/appInfo/findBindAppInfo",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<AppInfoSyncCloudResp> findBindAppInfo();


    @PostMapping(value = "/cloud/appInfo/getUagSyncData",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<UagSyncDataResp> getUagSyncData();


    @PostMapping(value = "/cloud/appInfo/findBasicMenuList",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<FindBasicMenuListResp> findBasicMenuList();

    @PostMapping(value = "/cloud/appInfo/findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList();
}
