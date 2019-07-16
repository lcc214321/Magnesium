package net.onebean.tenant.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.tenant.mngt.model.TtenantProvince;
import net.onebean.tenant.mngt.vo.FindListTtenantProvinceReq;
import net.onebean.tenant.mngt.vo.TtenantProvinceVo;
import net.onebean.tenant.mngt.vo.CascaderProvinceVo;

import java.util.List;


/**
* @author 0neBean
* @description 省份 service
* @date 2019-01-11 20:54:32
*/

public interface TtenantProvinceService extends IBaseBiz<TtenantProvince> {
    /**
     * 查询省份信息
     * @param req 参数体
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<TtenantProvinceVo > findFindListTtenantProvinceResp(FindListTtenantProvinceReq req, Pagination page, Sort sort);
    /**
     * 根据城市code 查找城市名
     * @param code 代码
     * @return 城市名
     */
    String findProvinceNameByCode(String code);
    /**
     * 查找vo
     * @param id 主键
     * @return vo
     */
    TtenantProvinceVo findVoById(String id);
    /**
     * 查询最大排序值
     * @return 排序值
     */
    Integer findMaxSort();
    /**
     * 级联选择器数据
     * @return list
     */
    List<CascaderProvinceVo> findAllForCascader();
}