package net.onebean.server.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.api.model.FindServerByNameReq;
import net.onebean.server.mngt.model.ServerInfo;
import net.onebean.server.mngt.vo.ServerInfoAddReq;
import net.onebean.server.mngt.vo.ServerInfoModifyReq;
import net.onebean.server.mngt.vo.ServerInfoVo;

import java.util.List;


/**
* @author 0neBean
* @description server info service
* @date 2019-01-21 18:05:28
*/

public interface ServerInfoService extends IBaseBiz<ServerInfo> {
    /**
     * 查询vo list
     * @param req 参数体
     * @return list
     */
    List<ServerInfoVo> findServerInfo(ServerInfoAddReq req, Pagination pagination, Sort sort);
    /**
     * 查询vo list
     * @param req 参数体
     * @return list
     */
    List<ServerInfoVo> findServerInfo(FindServerByNameReq req);
    /**
     *  根据Id 查找VO
     * @param id 主键
     * @return vo
     */
    ServerInfoVo findVoById(Object id);

    /**
     * 同步接口服务信息到缓存中
     * 这里的同步工作都在app-mngt中完成 这里只发送mq消息
     * @return bool
     */
    Boolean syncAppApiRelationship();
    /**
     * 删除服务信息
     * @param request 请求体
     * @return bool
     */
    Boolean deleteServerInfo(ServerInfoModifyReq request);
}