package net.onebean.uag.conf.service;

import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.uag.conf.vo.ConfResult;

import java.util.List;

/**
 * @author 0neBean
 */
public interface ManualUpdateServerNodeService {
    /**
     * 更新所有nginx 节点的 upsteam 信息
     */
    Boolean updateAllNginxUpSteamConf();

    /**
     * 生成 upsteam.conf 文件 并返回相应的记录
     * @param  upSteamNodeVos 所有的upsteam节点
     * @return ConfResult
     */
    ConfResult generateUpstream(List<UpSteamSyncNodeVo> upSteamNodeVos);

    /**
     * 查找所有upsteam节点
     * @return list
     */
    List<UpSteamSyncNodeVo> getAllUpSteamNode();
}
