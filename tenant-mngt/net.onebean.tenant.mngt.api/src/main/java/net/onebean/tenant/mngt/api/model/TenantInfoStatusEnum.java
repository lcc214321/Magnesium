package net.onebean.tenant.mngt.api.model;

/**
 * app状态枚举
 * @author 0neBean
 */
public enum TenantInfoStatusEnum {


    //注册:0, 使用:1, 冻结:2, 注销:3
    REGISTERED("0", "注册"),
    NORMAL("1", "使用"),
    FREEZE("2", "冻结"),
    CANCELLATION("3", "注销"),
            ;

    TenantInfoStatusEnum() {
    }

    private TenantInfoStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (TenantInfoStatusEnum s : TenantInfoStatusEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (TenantInfoStatusEnum s : TenantInfoStatusEnum.values()) {
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
