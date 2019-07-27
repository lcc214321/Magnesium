package net.onebean.server.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.server.mngt.api.model.ApiInfoCloudVo;
import net.onebean.server.mngt.api.model.AppApiRelationshipAppVo;
import net.onebean.server.mngt.api.model.UriApiRelationshipVo;
import net.onebean.server.mngt.model.ApiInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0neBean
* @description api info Dao
* @date 2019-01-22 10:59:03
*/
public interface ApiInfoDao extends BaseDao<ApiInfo> {
    /**
     * 查找未绑定的Api
     * @param appId 应用ID
     * @param serverId 服务ID
     * @return keys
     */
    List<String> findUnBindApiIds(@Param("appId")String appId, @Param("serverId")String serverId);

    /**
     * 根据服务查找API
     * @param serverId 服务ID
     * @return ApiInfoCloudVo
     */
    List<ApiInfoCloudVo> findApiByServerId(@Param("serverId")String serverId);
    /**
     * 根据应用查找API
     * @param appId 应用ID
     * @return ApiInfoCloudVo
     */
    List<ApiInfoCloudVo> findApiByAppId(@Param("appId")String appId);
    /**
     * 查找需要同步到网关的App api 对应关数据
     * @return list
     */
    List<AppApiRelationshipAppVo> findSyncAppApiRelationship();
    /**
     * 查找需要同步到网关的uri api 对应关数据
     * @return list
     */
    List<UriApiRelationshipVo> findSyncUriApiRelationship();
    /**
     * 根据apiId 查找绑定的appName
     * @param apiId 接口ID
     * @return appName
     */
    String findBindAppNameByApiId(@Param("apiId")Long apiId);
    /**
     * 代理地址去重
     * @param proxyPath 代理地址
     * @param id 主键
     * @return count
     */
    Integer countByProxyPathAndId(@Param("proxyPath")String proxyPath,@Param("id")Long id);
}