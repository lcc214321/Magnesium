package net.onebean.app.mngt.api.service;

import net.onebean.app.mngt.api.fallback.IpWhiteApiFallBack;
import net.onebean.app.mngt.api.model.IpWhiteListVo;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "app-mngt",fallback = IpWhiteApiFallBack.class)
public interface IpWhiteApi {

    @PostMapping(value = "/cloud/ipWhtieList/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<IpWhiteListVo> find();
}
