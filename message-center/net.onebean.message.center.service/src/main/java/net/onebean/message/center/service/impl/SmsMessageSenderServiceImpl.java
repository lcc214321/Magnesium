package net.onebean.message.center.service.impl;

import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.service.PushedMessageRecordService;
import net.onebean.message.center.service.SmsMessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsMessageSenderServiceImpl implements SmsMessageSenderService {

    private final static Logger logger = LoggerFactory.getLogger(SmsMessageSenderServiceImpl.class);


    @Autowired
    private PushedMessageRecordService pushedMessageRecordService;

    @Override
    public Boolean sendSmsMsgByChannel1(SendSmsMsgReq req) {
        logger.info("请实现 sendSmsMsgByChannel1 发送方法");
        return true;
    }


    @Override
    public Boolean sendSmsMsgByChannel2(SendSmsMsgReq req) {
        logger.info("请实现 sendSmsMsgByChannel2 发送方法");
        return true;
    }

}

