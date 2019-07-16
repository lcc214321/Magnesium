package net.onebean.server.mngt.dao;


import net.onebean.core.BaseDao;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.server.mngt.model.UpSteamNode;

import java.util.List;

/**
* @author 0neBean
* @description upsteam node info Dao
* @date 2019-03-01 15:25:32
*/
public interface UpSteamNodeDao extends BaseDao<UpSteamNode> {
    /**
     * 查找同步节点
     * @return list
     */
    List<UpSteamSyncNodeVo> findSyncNode();
}