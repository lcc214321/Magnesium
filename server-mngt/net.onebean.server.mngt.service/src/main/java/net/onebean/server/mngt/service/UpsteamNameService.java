package net.onebean.server.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.model.UpsteamName;
import net.onebean.server.mngt.vo.UpsteamNameAddReq;
import net.onebean.server.mngt.vo.UpsteamNameModifyReq;
import net.onebean.server.mngt.vo.UpsteamNameVo;

import java.util.List;


/**
* @author 0nebean
* @description upsteam name service
* @date 2019-03-01 17:40:12
*/

public interface UpsteamNameService extends IBaseBiz<UpsteamName> {
    /**
     * find vo list
     * @param param 参数体
     * @param page 分页信息
     * @param sort 排序
     * @return list
     */
    List<UpsteamNameVo> findUpsteamNameVo(UpsteamNameAddReq param, Pagination page, Sort sort);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(UpsteamNameAddReq request);
    /**
     * 更新
     * @param request req
     * @return id
     */
    Integer update(UpsteamNameModifyReq request);
    /**
     * 删除
     * @param request req
     * @return id
     */
    Integer delete(UpsteamNameModifyReq request);
    /**
     * 是否重复
     * @param upsteamName name
     * @param id id
     * @return bool
     */
    Boolean isRepeatByUpsteamName(String upsteamName,Object id);
    /**
     * 引用计数
     * @param upsteamName name
     * @return count
     */
    Integer countUseByUpsteamName(String upsteamName);


}