package net.onebean.message.center.common;

/**
 * 验证码类型
 */
public enum MsgypeEnum {

    MSG_TYPE_MSG("0", "短信"),
   ;



    private String code;
    private String type;

    MsgypeEnum(String code, String type) {
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
