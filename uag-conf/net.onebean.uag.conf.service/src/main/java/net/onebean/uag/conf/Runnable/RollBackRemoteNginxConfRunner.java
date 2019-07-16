package net.onebean.uag.conf.Runnable;

import net.onebean.component.SpringUtil;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.uag.conf.service.UpgradeNginxConfService;
import net.onebean.uag.conf.service.impl.UpgradeNginxConfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author 0neBean
 * 还原单台 nginx Runnable
 */
public class RollBackRemoteNginxConfRunner implements Runnable {

    @Autowired
    private UpgradeNginxConfService nginxConfUpDateService;

    private CountDownLatch latch;
    private NginxNodeSyncVo nginxInfo;
    private List<String> coverEntities;
    private List<String> removeEntities;
    private String remoteBackupPath;
    private Set<String> flagSet;


    public RollBackRemoteNginxConfRunner(CountDownLatch latch,NginxNodeSyncVo nginxInfo, List<String> coverEntities,List<String> removeEntities, String remoteBackupPath, Set<String> flagSet) {
        super();
        this.latch = latch;
        this.nginxInfo = nginxInfo;
        this.coverEntities = coverEntities;
        this.removeEntities = removeEntities;
        this.remoteBackupPath = remoteBackupPath;
        this.flagSet = flagSet;
    }

    @Override
    public void run() {
        try {
            nginxConfUpDateService = SpringUtil.getBean(UpgradeNginxConfServiceImpl.class);
            nginxConfUpDateService.rollBackRemoteNginxConf(nginxInfo, coverEntities, removeEntities, remoteBackupPath);
            flagSet.add("1");
        } catch (Exception e) {
            flagSet.add("0");
        } finally {
            latch.countDown();
        }
    }

}