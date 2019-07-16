package net.onebean.tenant.mngt.api.service;

import net.onebean.common.model.BaseResponse;
import net.onebean.tenant.mngt.api.fallback.TennantInfoApiFallBack;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoBatchSyncFlagReq;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoStatusReq;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 租户信息相关api
 * @author 0neBean
 */
@FeignClient(name = "tenant-mngt",fallback = TennantInfoApiFallBack.class)
public interface TennantInfoApi {

    /**
     * 根据租户ID获取租户信息
     * @param id 租户ID
     * @return 租户VO
     */
    @PostMapping(value = "/cloud/tenant/findTenantInfoByTenantId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<FindTtenantInfoVo> findTenantInfoByTenantId(@RequestParam("tenantId")String id);

    /**
     * 根据租户ID获取租户及城市信息
     * @param id 租户ID
     * @return 租户城市信息VO
     */
    @PostMapping(value = "/cloud/tenant/findTenantCityInfoByTenantId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<TenantCityInfoVo> findTenantCityInfoByTenantId(@RequestParam("tenantId")String id);
    /**
     * 批量更新 同步标识
     * @param req 参数体
     * @return resp
     */
    @PostMapping(value = "/cloud/tenant/updateBatchSyncFlag",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse updateBatchSyncFlag(@RequestBody @Validated ModifyTtenantInfoBatchSyncFlagReq req);
    /**
     * 更新租户status 用于初始化账户后修改账户状态
     * @param req 参数
     * @return resp
     */
    @PostMapping(value = "/cloud/tenant/updateStatus",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse updateStatus(@RequestBody @Validated ModifyTtenantInfoStatusReq req);

}
