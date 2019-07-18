package net.onebean.user.mngt.api.service;

import net.onebean.core.BaseResponse;
import net.onebean.user.mngt.api.fallback.UserAuthApiFallBack;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-mngt",fallback = UserAuthApiFallBack.class)
public interface UserAuthApi {
    /**
     * 查询uag用户登录状态
     * @param req 参数
     * @return bool
     */
    @PostMapping(value = "cloud/userAuth/checkUagLoginInfo",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<UagLoginInfo> checkUagLoginInfo(@RequestBody CheckUagLoginStatusReq req);

    /**
     * uag 登出操作 删除登录标识
     * @param req 参数
     * @return bool
     */
    @PostMapping(value = "cloud/userAuth/uagLogOut",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> uagLogOut(@RequestBody @Validated CheckUagLoginStatusReq req);
}
