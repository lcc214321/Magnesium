package net.onebean.tenant.mngt.vo;

import java.util.List;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class CityCascaderVo {

    private String value;
    private String label;
    private List<CityCascaderVo> children;
    private String type;


    public List<CityCascaderVo> getChildren() {
        return children;
    }

    public void setChildren(List<CityCascaderVo> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}