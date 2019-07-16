package net.onebean.tenant.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.api.model.FIndListTenantCityResp;
import net.onebean.tenant.mngt.api.model.FIndTenantCityByNameReq;
import net.onebean.tenant.mngt.model.TtenantCity;
import net.onebean.tenant.mngt.vo.*;

import java.util.List;


/**
* @author 0neBean
* @description 城市 service
* @date 2019-01-11 20:55:32
*/

public interface TtenantCityService extends IBaseBiz<TtenantCity> {
    /**
     * 查找 CascaderProvinceVo list
     * @param req 参数
     * @return list
     */
    List<CityCascaderVo> findAllForCascader(FIndListTenantCityReq req);
    /**
     * 根据城市名获取城市信息
     * @param req 参数体
     * @return city vo
     */
    FIndListTenantCityResp findCityVoByName(FIndTenantCityByNameReq req);
    /**
     * 根据城市code 查找城市名
     * @param code 代码
     * @return 城市名
     */
    String findCityNameByCode(String code);
    /**
     * 查找城市
     * @param req 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<TtenantCityVo> findFindListTtenantCityResp(FindTenantCityReq req, Pagination page, Sort sort);
    /**
     * 根据ID 查找vo
     * @param id 主键
     * @return vo
     */
    TtenantCityVo findVoById(String id);
    /**
     * 查找最大的城市code
     * @return code
     */
    Integer findMaxCode();
    /**
     * 查找app展示城市列表
     * @return list
     */
    List<FindCityForAppVo> findFindCityForAppShowList();
}