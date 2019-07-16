package net.onebean.uag.conf.service.impl;

import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import net.onebean.component.jsch.remote.JschsFactory;
import net.onebean.component.jsch.remote.ShellsCommand;
import net.onebean.core.form.Parse;
import net.onebean.server.mngt.api.model.AccessAuthTypeEnum;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.server.mngt.api.service.NginxInfoApi;
import net.onebean.uag.conf.Runnable.NginxReloadRunner;
import net.onebean.uag.conf.Runnable.RollBackRemoteNginxConfRunner;
import net.onebean.uag.conf.Runnable.UpdateSingleRemoteNginxConfRunner;
import net.onebean.uag.conf.common.ConfPathHelper;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.uag.conf.common.RunnerExecStatusEnum;
import net.onebean.uag.conf.service.UpgradeNginxConfService;
import net.onebean.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service
public class UpgradeNginxConfServiceImpl implements UpgradeNginxConfService {

    private final static Logger logger = LoggerFactory.getLogger(UpgradeNginxConfServiceImpl.class);
    private final static Integer CONNECT_LINUX_TIME_OUT = 0;
    private final static Boolean IS_SYNC_UPDATE_NGINX = false;

    @Autowired
    private NginxInfoApi nginxInfoApi;


    // 正在进行中的reload、准备reload状态，key为host
    public final static Map<String, Boolean> reloadStatus = new ConcurrentHashMap<>();

    public ShellsCommand getShellsCommand(NginxNodeSyncVo nginxNodeSyncVo) {
        /*初始化配置*/
        JschsFactory.JschConfig config = new JschsFactory.JschConfig();
        config.setHost(nginxNodeSyncVo.getIpAddress());
        config.setUser(nginxNodeSyncVo.getAccessUser());
        /*根据授权方式选择公私钥模式或密码*/
        String accessAuthType = Optional.of(nginxNodeSyncVo).map(NginxNodeSyncVo::getAccessAuthType).orElse("");
        if (StringUtils.isEmpty(accessAuthType)) {
            throw new BusinessException(ErrorCodesEnum.GET_DATE_ERR.code(), ErrorCodesEnum.GET_DATE_ERR.msg() + " filed of accessAuthType is empty");
        }
        if (accessAuthType.equals(AccessAuthTypeEnum.RSA.getKey())) {
            config.setRsaPath(nginxNodeSyncVo.getAccessRsaPath());
        } else {
            config.setPassword(nginxNodeSyncVo.getAccessPassword());
        }
        config.setPort(Parse.toInt(nginxNodeSyncVo.getAccessPort()));
        config.setTimeout(CONNECT_LINUX_TIME_OUT);
        ShellsCommand shellsCommand = new ShellsCommand();
        shellsCommand.setConfig(config);
        return shellsCommand;
    }

    public String backupRemoteNginxConf(ShellsCommand shellsCommand, String backupPath) {
        /*执行以下命令 生成备份目录 拷贝远程base目录下conf到备份目录*/
        logger.info("Backup remote nginx conf start. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        String destinationPath = backupPath + "/conf.d";
        try {
            shellsCommand.exec("mkdir -p " + destinationPath + ";cp -rf " + ConfPathHelper.getRemoteBasePath() + "/conf.d " + backupPath);
        } catch (BusinessException e) { // 此备份异常暂时只捕获打印日志
            logger.warn("backup remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost(), e);
        }
        logger.info("Backup remote nginx conf finish. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        return backupPath;
    }

    public void deleteRemoteNginxConf(ShellsCommand shellsCommand, List<String> removeEntities) {
        /*该删除操作只是按照时间创建temp文件夹  将文件归置到该目录下*/
        if (CollectionUtil.isEmpty(removeEntities)) {
            logger.warn("Delete remote nginx conf method ,the param removeEntities is none.");
            return;
        }
        // remove
        String tmp = ConfPathHelper.getRemoteDeletePath()+"/" + DateUtils.getDetailTime();
        int i = 0;
        StringBuilder removePaths = new StringBuilder();
        Iterator<String> iterator = removeEntities.iterator();
        if (iterator.hasNext()) {
            String tmpBak = tmp + "/" + i;
            removePaths.append("mkdir -p " + tmpBak).append(" ; mv -f ").append(ConfPathHelper.getRemoteBasePath()).append("/").append(iterator.next()).append(" ").append(tmpBak);
            while (iterator.hasNext()) {
                i++;
                tmpBak = tmp + "/" + i;
                removePaths.append(" ; mkdir -p " + tmpBak).append(" ; mv -f ").append(ConfPathHelper.getRemoteBasePath()).append("/").append(iterator.next()).append(" ").append(tmpBak);
            }
        }
        try {
            shellsCommand.exec(removePaths.toString());
        } catch (BusinessException e) { // 此删除异常暂时只捕获打印日志
            logger.warn("delete remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost() + ",command is " + removePaths, e);
        }
    }

    public void reloadNginx(ShellsCommand shellsCommand, String remoteBackupPath, boolean isSync) throws BusinessException {
        /*reload nginx 向上抛出业务异常*/
        reloadNginx(shellsCommand, isSync);
        try {
            /*reload成功后，把刚才备份的文件夹删除*/
            //todo 文件夹名称配置化
            shellsCommand.exec("mv " + remoteBackupPath +" "+ ConfPathHelper.getRemoteDeletePath());
        } catch (BusinessException e) { // 此删除异常暂时只捕获打印日志
            logger.warn("remove remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost(), e);
        }
    }

    public void reloadNginx(ShellsCommand shellsCommand, boolean isSync) throws BusinessException {
        String host = shellsCommand.getConfig().getHost();

        shellsCommand.exec("/usr/local/openresty/nginx/sbin/nginx -t");

        /*当需要同步reload时候*/
        if (isSync) {
            shellsCommand.exec("/usr/local/openresty/nginx/sbin/nginx -s reload");
            return;
        }

        logger.info("check nginx succeeded. Remote nginx server is {}", host);

        /*如果reloadStatus中对应的host中存在准备reload状态时候*/
        if (reloadStatus.get(host) != null && reloadStatus.get(host)) {
            /*此时直接返回，因为不需要再次reload的了*/
            return;
        }
        reloadStatus.put(host, true);
        /*使用线程的方式进行nginx的reload，主要目的是节省时间，提高反应速度*/
        TaskExecutors.UAGTHREADPOOL.execute(new NginxReloadRunner(shellsCommand));
    }


    public void rollBackAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, List<NginxNodeSyncVo> nginxInfos, String backupPath) throws InterruptedException {
        /*用线程的方式回滚所有nginx机器配置*/
        CountDownLatch rollBackLatch = new CountDownLatch(nginxInfos.size());
        Set<String> rollBackFlagSet = new HashSet<>();
        for (NginxNodeSyncVo nginxInfo : nginxInfos) {
            RollBackRemoteNginxConfRunner runner = new RollBackRemoteNginxConfRunner(rollBackLatch, nginxInfo, coverFiles, removeFiles, backupPath, rollBackFlagSet);
            TaskExecutors.UAGTHREADPOOL.execute(runner);
        }
        rollBackLatch.await();

        if (rollBackFlagSet.contains(RunnerExecStatusEnum.FAILURE.getKey())) {
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.msg());
        }
    }

    public void rollBackRemoteNginxConf(NginxNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String remoteBackupPath) {
        ShellsCommand shellsCommand = getShellsCommand(nginxInfo);
        logger.info("Roll back remote nginx conf start. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        //将涉及到更新的目录删除，然后将备份目录中的相关目录覆盖过来,然后再将删除文件全部还原回来
        String removeTmpPath = ConfPathHelper.getRemoteDeletePath()+"/" + DateUtils.getDetailTime();
        StringBuilder removeCommand = new StringBuilder();
        StringBuilder copyCommand = new StringBuilder();
        int i = 0;

          if (null != coverEntities) {
            for (String coverEntity : coverEntities) {
                if (coverEntity.contains("*")) {
                    throw new BusinessException(ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.code(), ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.msg());
                }
                String fullPathEntity;
                if (coverEntity.startsWith("/")) {
                    fullPathEntity = ConfPathHelper.getRemoteBasePath() + coverEntity;
                } else {
                    fullPathEntity = ConfPathHelper.getRemoteBasePath() + "/" + coverEntity;
                }
                String subRemoveTmpPath = removeTmpPath + "/" + i++;
                removeCommand.append("mkdir -p ").append(subRemoveTmpPath).append(";").append("mv ").append(fullPathEntity).append(" ").append(subRemoveTmpPath).append(";");
                copyCommand.append("mkdir ").append(IOUtils.getPathOfFile(fullPathEntity)).append(";");
                copyCommand.append("cp -rf ").append(remoteBackupPath).append("/").append(coverEntity).append(" ").append(fullPathEntity).append(";");
            }
        }
        if (null != removeEntities) {
            for (String removeEntity : removeEntities) {
                if (removeEntity.contains("*")) {
                    throw new BusinessException(ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.code(), ErrorCodesEnum.INVALID_OPERATION_LINUX_PATH.msg());
                }
                String fullPathEntity;
                if (removeEntity.startsWith("/")) {
                    fullPathEntity = ConfPathHelper.getRemoteBasePath() + removeEntity;
                } else {
                    fullPathEntity = ConfPathHelper.getRemoteBasePath() + "/" + removeEntity;
                }
                copyCommand.append("mkdir -p ").append(IOUtils.getPathOfFile(fullPathEntity)).append(";");
                // remove destination entity first
                copyCommand.append("mv ").append(fullPathEntity).append(" ").append(removeTmpPath).append("/").append(i++).append(";");
                copyCommand.append("cp -rf ").append(remoteBackupPath).append("/").append(removeEntity).append(" ").append(fullPathEntity).append(";");
            }
        }
        String finalCommand = removeCommand.append(copyCommand.toString()).toString().replace("\\", "/");
        try {
            shellsCommand.exec(finalCommand);
        } catch (BusinessException e) { // 此回滚异常暂时只捕获打印日志
            logger.warn("Roll back remote nginx conf exception. Remote nginx server is " + shellsCommand.getConfig().getHost() + ",command is " + finalCommand, e);
        }
        try {
            reloadNginx(shellsCommand, IS_SYNC_UPDATE_NGINX);
            logger.info("Roll back remote nginx conf finish. Remote nginx server is {}", shellsCommand.getConfig().getHost());
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg());
        }
    }


    @Override
    public void updateSingleRemoteNginxConf(NginxNodeSyncVo nginxInfo, List<String> coverEntities, List<String> removeEntities, String unifiedRemoteBackupPath, boolean isSync) {
        /*获取shell 命令行*/
        ShellsCommand remoteNginxShells = getShellsCommand(nginxInfo);
        logger.info("Update single remote nginx server , nginx ip is {}", remoteNginxShells.getConfig().getHost());
        String remoteBackupPath = null;
        try {
            /*先备份远程nginx配置*/
            remoteBackupPath = backupRemoteNginxConf(remoteNginxShells, unifiedRemoteBackupPath);

            /*删除必要的条目*/
            if (CollectionUtil.isNotEmpty(removeEntities)) {
                deleteRemoteNginxConf(remoteNginxShells, removeEntities);
            }

            /*remote push  将本地打包文件推送到远程nginx上*/
            logger.info("Pushing local files to nginx server , nginx ip is {}", remoteNginxShells.getConfig().getHost());
            remoteNginxShells.scp(ConfPathHelper.getLocalTarFilePath(), ConfPathHelper.getRemoteBasePath());

            /*使用最新的配置进行覆盖*/
            remoteNginxShells.exec("tar xf " + ConfPathHelper.getRemoteBasePath() + "/config.tar.gz -C " + ConfPathHelper.getRemoteBasePath());
            /*校验nginx和重新reload*/
            reloadNginx(remoteNginxShells, remoteBackupPath, isSync);
        } catch (Exception e) {
            logger.error("updateSingleRemoteNginxConf get error = ",e);
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg() + "update single remote nginx conf error." + e.getMessage());
        }
    }

    /**
     * 将本地文件进行打包处理
     */
    private void compressConfig() {
        /*删除本地压缩文件的目录*/
        IOUtils.deleteFile(ConfPathHelper.getLocalTarFilePath());
        List<String> paths = new ArrayList<>();
        paths.add(ConfPathHelper.getLocalConfDir());
        /*若存在日志路径添加到打包名单中*/
        String logPath = ConfPathHelper.getLocalLogPath();
        if (null != logPath) {
            paths.add(logPath);
        }
        /*打包文件 指定文件和目标路径*/
        TarFileUtil.compress(paths, ConfPathHelper.getLocalTarFilePath());
    }

    @Override
    public void updateAllRemoteNginxConf(List<String> coverFiles, List<String> removeFiles, boolean isSync) {

        try {
            /*将本地文件打包成压缩文件*/
            compressConfig();
            /*获取所有需要更新的nginx服务器*/
            List<NginxNodeSyncVo> nginxInfos = findAllNginxSyncNode();
            /*逻辑上至少有一台nginx 程序才成立,否则报错*/
            if (CollectionUtil.isEmpty(nginxInfos)) {
                throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
            }

            /*所有远程nginx机器上备份的目录*/
            String remoteBackupPath = ConfPathHelper.getRemoteBackupPath();

            /*用线程的方式更新每一台机器的nginx*/
            CountDownLatch updateLatch = new CountDownLatch(nginxInfos.size());
            Set<String> updateFlagSet = new HashSet<>();
            for (NginxNodeSyncVo nginxInfo : nginxInfos) {
                UpdateSingleRemoteNginxConfRunner runner = new UpdateSingleRemoteNginxConfRunner(updateLatch, nginxInfo, coverFiles, removeFiles, updateFlagSet, remoteBackupPath, isSync);
                TaskExecutors.UAGTHREADPOOL.execute(runner);
            }
            updateLatch.await();

            /*如果出现失败的情况 进行回滚*/
            if (updateFlagSet.contains(RunnerExecStatusEnum.FAILURE.getKey())) {
                rollBackAllRemoteNginxConf(coverFiles, removeFiles, nginxInfos, remoteBackupPath);
                throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_UN_ROLL_BACK_ERROR.msg());
            }
        }catch (BusinessException e) {
            /*捕获业务异常 主要用于捕获 回滚失败异常*/
            throw new BusinessException(e.getCode(),e.getMsg());
        } catch (Exception e) {
            /*更新失败 回滚成功异常*/
            throw new BusinessException(ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.code(), ErrorCodesEnum.UPDATE_NGINX_SAFE_ROLL_BACK_ERROR.msg());
        } finally {
            /*删除本地conf 文件*/
            IOUtils.deleteDir(ConfPathHelper.getLocalBasePath());
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<NginxNodeSyncVo> findAllNginxSyncNode() {
        BasePaginationResponse<NginxNodeSyncVo> resp = nginxInfoApi.findSyncNginxNode();
        List<NginxNodeSyncVo> list = Optional.ofNullable(resp).map(BasePaginationResponse::getDatas).orElse(null);
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        } else {
            logger.warn("findAllNginxSyncNode findSyncNginxNode res is empty");
            return Collections.EMPTY_LIST;
        }
    }
}
