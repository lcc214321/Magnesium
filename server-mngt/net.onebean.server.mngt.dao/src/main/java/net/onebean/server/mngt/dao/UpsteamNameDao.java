package net.onebean.server.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.server.mngt.model.UpsteamName;
import org.apache.ibatis.annotations.Param;

/**
* @author 0nebean
* @description upsteam name Dao
* @date 2019-03-01 17:40:12
*/
public interface UpsteamNameDao extends BaseDao<UpsteamName> {
    /**
     * 引用计数
     * @param upsteamName name
     * @return count
     */
    Integer countUseByUpsteamName(@Param("upsteamName") String upsteamName);
}