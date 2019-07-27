package net.onebean.app.mngt.service;

import net.onebean.app.mngt.api.model.*;
import net.onebean.app.mngt.model.AppInfo;
import net.onebean.app.mngt.vo.AppInfoQueryRequest;
import net.onebean.app.mngt.vo.AppInfoVo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author 0neBean
* @description 应用信息 service
* @date 2019-01-03 16:14:09
*/
public interface AppInfoService extends IBaseBiz<AppInfo> {
    /**
     * 根据vo中的条件查找list
     * @param param 参数
     * @param pagination 分页
     * @return list
     */
    List<AppInfoVo> findByAppInfoQueryRequest(AppInfoQueryRequest param, Pagination pagination, Sort sort);
    /**
     * 根据id查找 vo
     * @param id 主键
     * @return vo
     */
    AppInfoVo findVoById(Object id);
    /**
     * 生成应用ID及secret
     * @param appInfo 应用
     */
    void addAppInfo(AppInfo appInfo);
    /**
     * 根据appid secret 查找应用
     * @param req 参数体
     * @return APP vo
     */
    AppInfoCloudVo findByAppIdAndSecret(FindAppByAppIdAndSecretReq req);
    /**
     * 查找绑定了接口的app信息
     * @return list
     */
    List<AppInfoSyncCloudResp> findBindAppInfo();
    /**
     * 查找基础应用菜单
     * @return list
     */
    List<FindBasicMenuListResp> findBasicMenuList();
    /**
     * 查找uag 用户的 app列表
     * @return list
     */
    List<FindUagUserAppListResp> findUagUserAppList();
}