package net.onebean.app.mngt.api.model.constant;

/**
 * app状态枚举
 * @author 0neBean
 */
public enum LoginTypeEnum {


    //登录模式 0:通用验证码登录 1:账号密码登录
    SMS_LOGIN_REGISTER_API("0", "通用验证码登录"),
    PASSWORD_API("1", "账号密码登录"),
    SMS_API("2", "账号密码登录"),
    PASSWORD_SSO("3", "账号密码登录"),
    SMS_SSO("4", "账号密码登录"),
            ;

    LoginTypeEnum() {
    }

    private LoginTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (LoginTypeEnum s : LoginTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (LoginTypeEnum s : LoginTypeEnum.values()) {
            if (s.getKey().equals(key)) {
                return s.getValue();
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
