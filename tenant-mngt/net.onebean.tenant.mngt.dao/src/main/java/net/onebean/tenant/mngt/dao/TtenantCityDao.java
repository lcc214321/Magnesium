package net.onebean.tenant.mngt.dao;


import net.onebean.core.base.BaseDao;
import net.onebean.tenant.mngt.model.TtenantCity;

/**
* @author 0neBean
* @description 城市 Dao
* @date 2019-01-11 20:55:32
*/
public interface TtenantCityDao extends BaseDao<TtenantCity> {
    Integer findMaxCode();

}