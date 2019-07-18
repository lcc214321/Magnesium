package net.onebean.message.center.common;

/**
 * 消息类型
 */
public enum MsgTypeEnum {

    MSG_TYPE_MSG("1", "验证码"),
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
