package net.onebean.app.mngt.dao;

import net.onebean.app.mngt.api.model.AppInfoSyncCloudResp;
import net.onebean.app.mngt.api.model.FindBasicMenuListResp;
import net.onebean.app.mngt.api.model.FindUagUserAppListResp;
import net.onebean.app.mngt.model.AppInfo;
import net.onebean.core.BaseDao;

import java.util.List;

/**
* @author 0neBean
* @description 应用信息 Dao
* @date 2019-01-03 16:14:09
*/
public interface AppInfoDao extends BaseDao<AppInfo> {
    /**
     * 查找绑定了接口的app信息
     * @return list
     */
    List<AppInfoSyncCloudResp>findBindAppInfo();

    List<FindBasicMenuListResp> findBasicMenuList();

    List<FindUagUserAppListResp> findUagUserAppList();

}