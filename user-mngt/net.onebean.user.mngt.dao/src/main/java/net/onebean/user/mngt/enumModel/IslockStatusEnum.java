package net.onebean.user.mngt.enumModel;

/**
 * @author 0neBean
 */
public enum IslockStatusEnum {


    UN_LOCK("0", "未锁定"),
    LOCKED("1", "锁定"),
            ;

    IslockStatusEnum() {
    }

    private IslockStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (IslockStatusEnum s : IslockStatusEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (IslockStatusEnum s : IslockStatusEnum.values()) {
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
