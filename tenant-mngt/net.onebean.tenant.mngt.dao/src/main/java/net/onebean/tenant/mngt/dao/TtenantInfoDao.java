package net.onebean.tenant.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import net.onebean.tenant.mngt.api.model.TenantInfoSyncVo;
import net.onebean.tenant.mngt.model.TtenantInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0neBean
* @description 租户信息 Dao
* @date 2019-01-11 20:56:12
*/
public interface TtenantInfoDao extends BaseDao<TtenantInfo> {
    /**
     * 根据租户ID查找vo
     * @param tenantId 租户ID
     * @return vo
     */
    TenantCityInfoVo findTenantCityInfoByTenantId(@Param("tenantId") String tenantId);

    /**
     * 根据同步标识查找
     * @return vo
     */
    List<TenantInfoSyncVo> findByUnSyncStatus();

}