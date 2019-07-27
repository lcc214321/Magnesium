package net.onebean.user.mngt.dao;


import net.onebean.core.base.BaseSplitDao;
import net.onebean.user.mngt.model.UagUserInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author 0neBean
* @description 用户信息 Dao
* @date 2019-06-04 14:03:47
*/
public interface UagUserInfoDao extends BaseSplitDao<UagUserInfo> {
    void InitUagAccountTable(@Param("sql") String sql);
}