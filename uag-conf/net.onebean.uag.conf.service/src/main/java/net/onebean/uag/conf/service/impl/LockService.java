package net.onebean.uag.conf.service.impl;


import net.onebean.core.error.BusinessException;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 0neBean
 *         分布式锁操作类
 */
@Service
public class LockService {

    @Autowired
    private RedissonClient redisson;

    /**
     * 获取公平锁
     *
     * @return
     */
    public RLock getFairLock() {
        return redisson.getFairLock("FILE_GENERATION_LOCK");
    }

    /**
     * 锁定
     *
     * @param lock
     * @throws InterruptedException
     */
    public void lock(RLock lock) throws InterruptedException {
        boolean res = lock.tryLock(180, 120, TimeUnit.SECONDS);
        if (!res) {
            throw new BusinessException(ErrorCodesEnum.GET_REDIS_LOCK_ERR.code(), ErrorCodesEnum.GET_REDIS_LOCK_ERR.msg());
        }
    }

    /**
     * 解锁
     *
     * @param lock
     */
    public void unLock(RLock lock) {
        if (lock != null) {
            lock.unlock();
        }
    }
}
