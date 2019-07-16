package net.onebean.api.adapter.service;

import net.onebean.api.adapter.vo.SendSalesLoginSmsReq;

public interface SendSmsService {
    /**
     * 发送登录验证短信
     * @param req 参数体
     * @return bool
     */
    Boolean sendLoginSms(SendSalesLoginSmsReq req);
}
