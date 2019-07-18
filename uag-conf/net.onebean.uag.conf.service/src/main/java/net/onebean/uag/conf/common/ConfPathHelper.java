package net.onebean.uag.conf.common;

import net.onebean.core.error.BusinessException;
import net.onebean.util.DateUtils;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;

import java.io.File;
import java.nio.file.Paths;

public class ConfPathHelper {

    private final static String UAG_LOCAL_PATN_KEY = "uag.local.relative.path";
    private final static String UAG_BACKUP_PATN_KEY = "uag.remote.backup.path";
    private final static String UAG_DELETE_PATN_KEY = "uag.remote.delete.path";

    /**
     * 获取conf.d文件夹路径,绝对路径
     */
    public static String getLocalConfDir() {
        return Paths.get(ConfPathHelper.getLocalBasePath(), "conf.d").toFile().getAbsolutePath();
    }

    /**
     * 获取远程删除路径
     */
    public static String getRemoteDeletePath() {
        String deletePath = PropUtil.getInstance().getConfig(UAG_DELETE_PATN_KEY, PropUtil.DEFLAULT_NAME_SPACE);
        if (StringUtils.isEmpty(deletePath)) {
            throw new BusinessException(ErrorCodesEnum.READ_APOLLO_ERROR.code(), ErrorCodesEnum.READ_APOLLO_ERROR.msg());
        }
        return deletePath;
    }


    /**
     * 返回产生conf文件的根路径
     */
    public static String getLocalBasePath() {
        String uagPath = PropUtil.getInstance().getConfig(UAG_LOCAL_PATN_KEY, PropUtil.DEFLAULT_NAME_SPACE);
        if (StringUtils.isEmpty(uagPath)) {
            throw new BusinessException(ErrorCodesEnum.VALUE_CAN_NOT_BE_EMPTY_ERR.code(), ErrorCodesEnum.VALUE_CAN_NOT_BE_EMPTY_ERR.msg());
        }
        String localBasePath = StringUtils.toStrTrim(uagPath);
        if (localBasePath.endsWith("/")) {
            localBasePath = localBasePath.substring(0, localBasePath.length() - 1);
        }
        return localBasePath;
    }

    /**
     * 本地tar路径，绝对路径
     */
    public static String getLocalTarFilePath() {
        return Paths.get(ConfPathHelper.getLocalBasePath(), "config.tar.gz").toFile().getAbsolutePath();
    }

    /**
     * 获取log日志的路径，若存在则返回绝对路径，若不存在则返回null
     */
    public static String getLocalLogPath() {
        File logFile = Paths.get(ConfPathHelper.getLocalBasePath(), "log").toFile();
        return logFile.exists() ? logFile.getAbsolutePath() : null;
    }


    /*获取服务器上相对路径*/
    public static String getEcsRelativePath(String path) {
        String localBasePath = StringUtils.toStrTrim(getLocalBasePath());
        // 此处为进行异常检查，是因为若path长度小于localBasePath，则程序肯定有问题
        return path.substring(localBasePath.length() + 3);
    }

    /**
     * 远端nginx的备份路径
     */
    public static String getRemoteBackupPath() {
        String remoteBackupDir = PropUtil.getInstance().getConfig(UAG_BACKUP_PATN_KEY, PropUtil.DEFLAULT_NAME_SPACE);
        if (StringUtils.isEmpty(remoteBackupDir)) {
            throw new BusinessException(ErrorCodesEnum.READ_APOLLO_EMPTY_VALUE.code(), ErrorCodesEnum.READ_APOLLO_EMPTY_VALUE.msg());
        }
        remoteBackupDir = StringUtils.toStrTrim(remoteBackupDir);
        if (remoteBackupDir.endsWith("/")) {
            remoteBackupDir = remoteBackupDir.substring(0, remoteBackupDir.length() - 1);
        }
        return remoteBackupDir + "/" + DateUtils.getDetailTime();
    }
}
