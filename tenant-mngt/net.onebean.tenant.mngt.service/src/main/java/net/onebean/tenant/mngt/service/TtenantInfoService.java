package net.onebean.tenant.mngt.service;


import net.onebean.core.base.IBaseBiz;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoBatchSyncFlagReq;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import net.onebean.tenant.mngt.biz.model.TtenantInfoModifyResp;
import net.onebean.tenant.mngt.model.TtenantInfo;

import java.util.List;


/**
* @author 0neBean
* @description 租户信息 service
* @date 2019-01-11 20:56:12
*/
public interface TtenantInfoService extends IBaseBiz<TtenantInfo> {
    /**
     * 查找租户信息
     * @param req 参数体
     * @return list
     */
    List<TtenantInfoModifyResp> findListByVo(FindTtenantInfoVo req, Pagination page, Sort sort);
    /**
     * 根据ID查找vo
     * @param id 参数体
     * @return FindTtenantInfoVo vo
     */
    FindTtenantInfoVo findVoById(String id);
    /**
     * 根据租户ID 查找租户城市信息
     * @param tenantId 租户ID
     * @return TenantCityInfoVo
     */
    TenantCityInfoVo findTenantCityInfoByTenantId(String tenantId);
    /**
     * 发送初始化账户消息
     * @param id 参数id
     */
    Boolean sendInitAccountMsg(String id);
    /**
     * 同步账号状态到rs-portal
     */
    Boolean sync();
    /**
     * 批量更新
     * @param req 参数体
     * @return int
     */
    Integer updateBatchSyncFlag(ModifyTtenantInfoBatchSyncFlagReq req);

}