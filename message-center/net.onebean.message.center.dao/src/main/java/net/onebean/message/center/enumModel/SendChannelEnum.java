package net.onebean.message.center.enumModel;

public enum SendChannelEnum {


    Channel_1("1", "通道1"),
    Channel_2("2", "通道2")
    ;

    SendChannelEnum() {
    }

    private SendChannelEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (SendChannelEnum s : SendChannelEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (SendChannelEnum s : SendChannelEnum.values()) {
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
