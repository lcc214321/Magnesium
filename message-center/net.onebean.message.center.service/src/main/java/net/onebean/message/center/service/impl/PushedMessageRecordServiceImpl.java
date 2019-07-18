package net.onebean.message.center.service.impl;
import com.alibaba.fastjson.JSONObject;
import net.onebean.core.error.BusinessException;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.common.ErrorCodesEnum;
import net.onebean.message.center.common.MsgTypeEnum;
import net.onebean.util.FreeMarkerTemplateUtils;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseSplitBizManual;
import net.onebean.message.center.model.PushedMessageRecord;
import net.onebean.message.center.service.PushedMessageRecordService;
import net.onebean.message.center.dao.PushedMessageRecordDao;

import java.util.Optional;

/**
* @author 0neBean
* @description 推送记录 serviceImpl
* @date 2019-07-18 16:39:04
*/
@Service
public class PushedMessageRecordServiceImpl extends BaseSplitBizManual<PushedMessageRecord, PushedMessageRecordDao> implements PushedMessageRecordService{

    private final static Logger LOGGER = LoggerFactory.getLogger(PushedMessageRecordServiceImpl.class);
    private final static String CREATE_RECORD_TABLE_FTL_KEY = "/record/CreatePushedMessageRecordTable.ftl";

    @Override
    public Boolean saveSendSmsCodeRecord(SendSmsMsgReq req) {
        String telPhone = Optional.ofNullable(req).map(SendSmsMsgReq::getTelPhone).orElse("");
        String messageBody = Optional.ofNullable(req).map(SendSmsMsgReq::getMessageBody).orElse("");
        String appId = Optional.ofNullable(req).map(SendSmsMsgReq::getAppId).orElse("");
        if(StringUtils.isEmpty(telPhone)){
            throw new BusinessException(ErrorCodesEnum.INSERT_MSG_DATA_ERR.code(),ErrorCodesEnum.INSERT_MSG_DATA_ERR.msg()+" telPhone is empty");
        }
        if(StringUtils.isEmpty(messageBody)){
            throw new BusinessException(ErrorCodesEnum.INSERT_MSG_DATA_ERR.code(),ErrorCodesEnum.INSERT_MSG_DATA_ERR.msg()+" messageBody is empty");
        }
        if(StringUtils.isEmpty(appId)){
            throw new BusinessException(ErrorCodesEnum.INSERT_MSG_DATA_ERR.code(),ErrorCodesEnum.INSERT_MSG_DATA_ERR.msg()+" appId is empty");
        }

        //不存在表创建表
        if (!checkRecordExists(appId)){
            createRecordTable(appId);
        }
        PushedMessageRecord msgCodeRecord = new PushedMessageRecord();
        msgCodeRecord.setMessageBody(messageBody);
        msgCodeRecord.setMessageType(MsgTypeEnum.MSG_TYPE_MSG.code());
        msgCodeRecord.setReceiverAccount(telPhone);
        this.save(msgCodeRecord,appId);
        return true;
    }

    @Override
    public Boolean checkRecordExists(String appId) {
        return StringUtils.isNotEmpty(baseDao.checkRecordExists(appId));
    }

    @Override
    public void createRecordTable(String appId) {
        JSONObject param = new JSONObject();
        param.put("appId",appId);
        String sql = FreeMarkerTemplateUtils.generateStringFromFreeMarker(param,CREATE_RECORD_TABLE_FTL_KEY);
        LOGGER.info("createRecordTable sql = "+sql);
        baseDao.createRecordTable(sql);
    }
}