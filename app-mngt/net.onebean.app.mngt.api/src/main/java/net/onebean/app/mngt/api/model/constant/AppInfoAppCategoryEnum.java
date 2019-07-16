package net.onebean.app.mngt.api.model.constant;

public enum AppInfoAppCategoryEnum {

    //app分类 0:基础应用,1:普通应用,2云应用
    BASIC_APP("0", "基础应用"),
    NORMAL_APP("1", "普通应用"),
    CLOUD_APP("2", "云应用"),
    ;

    AppInfoAppCategoryEnum() {
    }

    private AppInfoAppCategoryEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (AppInfoAppCategoryEnum s : AppInfoAppCategoryEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (AppInfoAppCategoryEnum s : AppInfoAppCategoryEnum.values()) {
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
