package net.onebean.uag.conf.common;

public enum RunnerExecStatusEnum {

    FAILURE("0", "失败"),
    SUCCESSFUL("1", "成功");

    private String key;
    private String value;

    private RunnerExecStatusEnum() {
    }

    private RunnerExecStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getKeyByValue(String value) {
        RunnerExecStatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RunnerExecStatusEnum s = var1[var3];
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }

        return "";
    }

    public static String getValueByKey(String key) {
        RunnerExecStatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RunnerExecStatusEnum s = var1[var3];
            if (s.getKey().equals(key)) {
                return s.getValue();
            }
        }

        return "";
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
