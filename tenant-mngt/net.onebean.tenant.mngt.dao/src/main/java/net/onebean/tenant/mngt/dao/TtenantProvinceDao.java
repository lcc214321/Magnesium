package net.onebean.tenant.mngt.dao;


import net.onebean.core.BaseDao;
import net.onebean.tenant.mngt.model.TtenantProvince;

/**
* @author 0neBean
* @description 省份 Dao
* @date 2019-01-11 20:54:32
*/
public interface TtenantProvinceDao extends BaseDao<TtenantProvince> {
    /**
     * 获取最大排序值
     * @return
     */
    Integer findMaxCode();
}