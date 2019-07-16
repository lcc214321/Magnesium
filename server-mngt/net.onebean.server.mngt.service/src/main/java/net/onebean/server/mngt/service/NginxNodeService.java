package net.onebean.server.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.server.mngt.model.NginxNode;
import net.onebean.server.mngt.vo.NginxNodeAddReq;
import net.onebean.server.mngt.vo.NginxNodeInfoModifyReq;
import net.onebean.server.mngt.vo.NginxNodeVo;

import java.util.List;


/**
* @author 0neBean
* @description nginx节点管理 service
* @date 2019-03-01 12:00:33
*/

public interface NginxNodeService extends IBaseBiz<NginxNode> {
    /**
     * 查找list
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<NginxNodeVo> findNginxNodeVo(NginxNodeAddReq param, Pagination page, Sort sort);
    /**
     * 查找VO
     * @param id 主键
     * @return vo
     */
    NginxNodeVo findVoById(Object id);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(NginxNodeAddReq request);
    /**
     * 更新
     * @param request req
     * @return id
     */
    Integer update(NginxNodeInfoModifyReq request);
    /**
     * 是否重复
     * @param ipAddress ip
     * @param id id
     * @return bool
     */
    Boolean isRepeatByIpAddressAndId(String ipAddress,Object id);
    /**
     * 查找需要同步的nginx节点
     * @return list
     */
    List<NginxNodeSyncVo> findSyncableNode();
}