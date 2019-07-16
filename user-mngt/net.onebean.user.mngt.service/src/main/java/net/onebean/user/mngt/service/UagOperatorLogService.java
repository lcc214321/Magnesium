package net.onebean.user.mngt.service;


import net.onebean.core.IBaseBiz;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.user.mngt.model.UagOperatorLog;
import net.onebean.user.mngt.vo.FindUagLogReq;
import net.onebean.user.mngt.vo.UagLogVo;

import java.util.List;


/**
* @author 0neBean
* @description 操作日志 service
* @date 2019-06-27 23:45:23
*/

public interface UagOperatorLogService extends IBaseBiz<UagOperatorLog> {

    /**
     * 查询操作日志列表
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UagLogVo> findUagLogInfo(FindUagLogReq param, Pagination page, Sort sort);
}