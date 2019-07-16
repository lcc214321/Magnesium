package net.onebean.server.mngt.dao;


import net.onebean.core.BaseDao;
import net.onebean.server.mngt.model.ServerInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author 0neBean
* @description server info Dao
* @date 2019-01-21 18:05:28
*/
public interface ServerInfoDao extends BaseDao<ServerInfo> {
    /**
     * 根据serverId 查找绑定的appName
     * @param serverId 服务ID
     * @return appName
     */
    String findBindAppNameByServerId(@Param("serverId")Long serverId);
}