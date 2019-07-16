package net.onebean.server.mngt.api.model;

public enum  AccessAuthTypeEnum {


    //访问授权方式 (0: 密码模式,1:公私钥模式)
    PASSWORD("0", "密码模式"),
    RSA("1", "公私钥模式"),
            ;

    AccessAuthTypeEnum() {
    }

    private AccessAuthTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (AccessAuthTypeEnum s : AccessAuthTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (AccessAuthTypeEnum s : AccessAuthTypeEnum.values()) {
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
