package net.onebean.app.mngt.api.model.constant;

/**
 * 应用鉴权类型枚举
 * @author 0neBean
 */
public enum AuthTypeEnum {

    //授权模式 0:oauth授权码,1:oauth私有令牌,2:ip白名单+通信令牌,3:oauth私有免登陆令牌
    OAUTH_CODE("0", "授权码 (共享令牌)"),
    OAUTH_PRIVATE_CODE("1", "私有令牌  (一设备一令牌 需要登录)"),
    IP_AND_TOKEN("2", "IP白名单+通行令牌 (管理后台模式)"),
    AVOID_LOGIN("3", "私有免登陆令牌 (一设备一令牌 免登陆)"),
            ;

    AuthTypeEnum() {
    }

    private AuthTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (AuthTypeEnum s : AuthTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (AuthTypeEnum s : AuthTypeEnum.values()) {
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
