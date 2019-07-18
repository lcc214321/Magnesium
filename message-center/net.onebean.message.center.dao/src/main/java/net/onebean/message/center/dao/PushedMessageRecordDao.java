package net.onebean.message.center.dao;


import net.onebean.core.BaseSplitDao;
import net.onebean.message.center.model.PushedMessageRecord;
import org.apache.ibatis.annotations.Param;

/**
* @author 0neBean
* @description 推送记录 Dao
* @date 2019-07-18 16:39:04
*/
public interface PushedMessageRecordDao extends BaseSplitDao<PushedMessageRecord> {
    /**
     * 检查记录是否存在
     * @param appId 应用ID
     * @return tableName
     */
    String checkRecordExists(@Param("appId") String appId);

    /**
     * 创建记录表
     * @param sql sql
     */
    void createRecordTable(@Param("sql") String sql);
}