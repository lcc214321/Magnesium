package net.onebean.api.adapter.enumModel;

public enum PlatformTypeEnum {

    //0:连接 1:断开
    ANDROID("0", "android"),
    IOS("1", "ios"),
    OTHER("2", "other"),
    ;

    PlatformTypeEnum() {
    }

    private PlatformTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (PlatformTypeEnum s : PlatformTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (PlatformTypeEnum s : PlatformTypeEnum.values()) {
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
