package net.onebean.server.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.*;
import net.onebean.server.mngt.model.ApiInfo;
import net.onebean.server.mngt.vo.ApiInfoFindReq;
import net.onebean.server.mngt.vo.ApiInfoVo;

import java.util.List;


/**
* @author 0neBean
* @description api info service
* @date 2019-01-22 10:59:03
*/

public interface ApiInfoService extends IBaseBiz<ApiInfo> {
    /**
     * 查找apiInfo list
     * @param req 参数体
     * @param pagination 分页信息
     * @param sort 排序
     * @return list
     */
    List<ApiInfoVo> findApiInfoVo(ApiInfoFindReq req, Pagination pagination, Sort sort);
    /**
     * 根据ID查找VO
     * @param id 主键
     * @return vo
     */
    ApiInfoVo findVoById(Object id);
    /**
     * 查找应用对应接口 绑定和 未绑定的list
     * @param request 参数体
     * @return AppApiRelationshipResp
     */
    AppApiRelationshipResp findAppApiRelationshipByServerIdAndAppId(AppApiRelationshipReq request);
    /**
     * 根据服务id 查找api vo
     * @param serverId 服务ID
     * @return list
     */
    List<ApiInfoCloudVo> findApiByServerId(String serverId);
    /**
     * 查找未绑定的Api
     * @param appId 应用ID
     * @param serverId 服务ID
     * @return keys
     */
    List<String> findUnBindApiIds(String appId, String serverId);
    /**
     * 查找需要同步到网关的App api 对应关数据
     * @return AppApiRelationSyncResqVo
     */
    AppApiRelationSyncResqVo findSyncAppApiRelationship();
    /**
     * 查找应用对应接口 根据应用ID
     * @param request 参数体
     * @return AppApiRelationshipResp
     */
    AppApiRelationshipResp findApiByAppId(FindApiByAppIdReq request);
}