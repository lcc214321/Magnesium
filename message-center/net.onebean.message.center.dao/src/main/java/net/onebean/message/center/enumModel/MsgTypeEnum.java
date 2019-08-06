package net.onebean.message.center.enumModel;

/**
 * 消息类型
 */
public enum MsgTypeEnum {

    MSG_TYPE_SMS_CODE("1", "短信验证码"),
   ;



    private String code;
    private String type;

    MsgTypeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public String code() {
        return this.code;
    }

    public String type() {
        return this.type;
    }
}
