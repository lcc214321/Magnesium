package net.onebean.tenant.mngt.api.model;

/**
 * @ClassName TenantIdCloudRequestVo
 * @Description TODO
 * @Author liujinshan
 * @Date 2019/5/27 16:00
 * @Version 1.0
 **/
public class TenantIdCloudRequestVo {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TenantIdCloudRequestVo{" +
                "id=" + id +
                '}';
    }
}
