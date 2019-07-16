package net.onebean.server.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.server.mngt.model.UpSteamNode;
import net.onebean.server.mngt.vo.UpSteamNodeAddReq;
import net.onebean.server.mngt.vo.UpSteamNodeModifyReq;
import net.onebean.server.mngt.vo.UpSteamNodeVo;

import java.util.List;


/**
* @author 0neBean
* @description upsteam node info service
* @date 2019-03-01 15:25:32
*/

public interface UpSteamNodeService extends IBaseBiz<UpSteamNode> {
    List<UpSteamNodeVo> findUpSteamNodeVo(UpSteamNodeAddReq param, Pagination page, Sort sort);

    UpSteamNodeVo findVoById(Object id);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(UpSteamNodeAddReq request);
    /**
     * 更新
     * @param request req
     * @return id
     */
    Integer update(UpSteamNodeModifyReq request);
    /**
     * 是否重复
     * @param physicalPath path
     * @return bool
     */
    Boolean isRepeatByPhysicalPath(String physicalPath,Object id);
    /**
     * 查找同步节点
     * @return list
     */
    List<UpSteamSyncNodeVo> findSyncNode();
}