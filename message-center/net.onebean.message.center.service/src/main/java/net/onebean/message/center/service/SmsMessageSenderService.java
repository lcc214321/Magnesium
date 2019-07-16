package net.onebean.message.center.service;

import net.onebean.message.center.api.model.SendSmsMsgReq;

public interface SmsMessageSenderService {
     /**
     * 发送短信
     * @param req 参数体
     * @return bool
     */
    Boolean sendSmsMsgByChannel1(SendSmsMsgReq req);
    /**
     * 发送短信
     * @param req 参数体
     * @return bool
     */
    Boolean sendSmsMsgByChannel2(SendSmsMsgReq req);
}
