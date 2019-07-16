package net.onebean.user.mngt.common;

public enum  BindingKeyNameEnum {


            ;

    private String name;

    BindingKeyNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}