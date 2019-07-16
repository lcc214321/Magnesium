package net.onebean.uag.conf.service;

public interface AppServerService {
    /**
     * 同步应用服务到uag缓存
     * @return bool
     */
    Boolean syncAppApiRelationship();
}
