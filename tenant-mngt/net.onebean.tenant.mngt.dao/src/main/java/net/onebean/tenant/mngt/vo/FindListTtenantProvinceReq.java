package net.onebean.tenant.mngt.vo;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class FindListTtenantProvinceReq {

    private String provinceName;
    public String getProvinceName(){
        return this.provinceName;
    }
    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    private String provinceCode;
    public String getProvinceCode() {
        return provinceCode;
    }
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }


}