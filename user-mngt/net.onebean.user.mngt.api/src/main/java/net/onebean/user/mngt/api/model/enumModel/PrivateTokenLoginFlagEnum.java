package net.onebean.user.mngt.api.model.enumModel;

/**
 * @author 0neBean
 */
public enum PrivateTokenLoginFlagEnum {

    OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_REGISTER("0", "注册"),
    OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN("1", "登录"),
            ;

    PrivateTokenLoginFlagEnum() {
    }

    private PrivateTokenLoginFlagEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (PrivateTokenLoginFlagEnum s : PrivateTokenLoginFlagEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (PrivateTokenLoginFlagEnum s : PrivateTokenLoginFlagEnum.values()) {
            if (s.getKey().equals(key)) {
                return s.getValue();
            }
        }
        return "";
    }

    public static String getLoginStatusComment(){
        return  "register:"
                +PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_REGISTER.getKey()
                +","
                +"login:"
                +PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey();
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
