package net.onebean.app.mngt.enumModel;

/**
 * app状态枚举
 * @author 0neBean
 */
public enum AppStatusEnum {


    //0:连接 1:断开
    CONNECT("0", "连接"),
    DISCONNECT("1", "断开"),
            ;

    AppStatusEnum() {
    }

    private AppStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (AppStatusEnum s : AppStatusEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (AppStatusEnum s : AppStatusEnum.values()) {
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
