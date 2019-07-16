package net.onebean.uag.conf.Runnable;

import net.onebean.component.SpringUtil;
import net.onebean.component.jsch.remote.ShellsCommand;
import net.onebean.core.form.Parse;
import net.onebean.uag.conf.service.UpgradeNginxConfService;
import net.onebean.uag.conf.service.impl.LockService;
import net.onebean.uag.conf.service.impl.UpgradeNginxConfServiceImpl;
import net.onebean.util.PropUtil;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 0neBean
 * reload nginx runner
 */
public class NginxReloadRunner implements Runnable {

    private LockService lockService;
    private UpgradeNginxConfService nginxConfUpDateService;

    private final static Logger logger = LoggerFactory.getLogger(NginxReloadRunner.class);
    private final static String NGINX_LOAD_POST_PONE_KEY = "nginx.load.post.pone";

    // 操作linux的shell工具
    private ShellsCommand shellsCommand;

    public NginxReloadRunner(ShellsCommand shellsCommand) {
        super();
        this.shellsCommand = shellsCommand;
    }

    public void run() {
        try {
            // 睡眠一段时间
            int postpone = Parse.toInt(PropUtil.getInstance().getConfig(NGINX_LOAD_POST_PONE_KEY,PropUtil.DEFLAULT_NAME_SPACE));
            if (postpone == 0) {
                postpone=10000;
            }
            Thread.sleep(postpone);
        } catch (Exception e1) {
            logger.warn("线程sleep时候竟然报异常了", e1);
        }

        String host = shellsCommand.getConfig().getHost();
        lockService = SpringUtil.getBean(LockService.class);
        RLock lock = lockService.getFairLock();
        try {
            lockService.lock(lock);
            UpgradeNginxConfServiceImpl.reloadStatus.put(host, true);
            shellsCommand.exec("/usr/local/openresty/nginx/sbin/nginx -s reload");
            logger.info("reload nginx succeeded. Remote nginx server is {}", host);
        } catch (Exception e) {
            logger.error("reload nginx failed. Remote nginx server is " + host, e);
        } finally {
            UpgradeNginxConfServiceImpl.reloadStatus.put(host, false);
            lockService.unLock(lock);
        }
    }
}