package net.onebean.app.mngt.service;


import net.onebean.app.mngt.model.IpWhiteList;
import net.onebean.app.mngt.vo.IpWhiteListAddReq;
import net.onebean.app.mngt.vo.IpWhiteListQueryReq;
import net.onebean.app.mngt.api.model.IpWhiteListVo;
import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;

import java.util.List;


/**
* @author 0neBeen
* @description ip白名单 service
* @date 2019-02-21 15:48:15
*/

public interface IpWhiteListService extends IBaseBiz<IpWhiteList> {
    /**
     * 根据req 查找list
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<IpWhiteListVo> findByIpWhiteListQueryReq(IpWhiteListQueryReq param, Pagination page, Sort sort);
    /**
     * 是否有改ip地址存在
     * @param ipAddress ip
     * @return int
     */
    Boolean isRepeatIpAddress(String ipAddress);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(IpWhiteListAddReq request);
    /**
     * 是否重复
     * @param ipAddress ip
     * @return bool
     */
    Boolean isRepeatByIpAddress(String ipAddress);
    /**
     * 查找需要同步的节点
     * @return list
     */
    List<IpWhiteListVo> findSyncList();
}