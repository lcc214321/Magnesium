package net.onebean.tenant.mngt.biz.model;

import net.onebean.component.SpringUtil;
import net.onebean.tenant.mngt.service.TtenantCityService;
import net.onebean.tenant.mngt.service.impl.TtenantCityServiceImpl;

/**
 * app状态枚举
 * @author 0neBean
 */
public enum CityCodeEnum {


            ;

    CityCodeEnum() {
    }

    private CityCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;


    public static String getValueByKey(String key) {
        TtenantCityService cityService = SpringUtil.getBean(TtenantCityServiceImpl.class);
        return cityService.findCityNameByCode(key);
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
