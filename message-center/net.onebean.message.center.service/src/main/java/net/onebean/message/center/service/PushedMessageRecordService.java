package net.onebean.message.center.service;


import net.onebean.core.base.IBaseSplitBizManual;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.model.PushedMessageRecord;


/**
* @author 0neBean
* @description 推送记录 service
* @date 2019-07-18 16:39:04
*/

public interface PushedMessageRecordService extends IBaseSplitBizManual<PushedMessageRecord> {
    /**
     * 短信发送成功后保存记录
     *
     * @param req 参数
     * @return bool
     */
    Boolean saveSendSmsCodeRecord(SendSmsMsgReq req);

    /**
     * 检查记录是否存在
     *
     * @param appId 应用ID
     * @return bool
     */
    Boolean checkRecordExists(String appId);

    /**
     * 创建记录表
     *
     * @param appId 应用ID
     */
    void createRecordTable(String appId);

}