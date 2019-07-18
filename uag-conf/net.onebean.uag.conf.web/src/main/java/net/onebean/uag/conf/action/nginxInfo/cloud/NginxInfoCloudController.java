package net.onebean.uag.conf.action.nginxInfo.cloud;

import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.uag.conf.service.ManualUpdateServerNodeService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cloud/nginxInfo")
@RestController
public class NginxInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(NginxInfoCloudController.class);

    @Autowired
    private ManualUpdateServerNodeService manualUpdateServerNodeService;


    @SuppressWarnings("unchecked")
    @PostMapping(value = "/syncNginxConf", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> syncNginxConf() {
        logger.info("ApiInfoCloudController syncNginxConf method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(manualUpdateServerNodeService.updateAllNginxUpSteamConf());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ApiInfoCloudController syncNginxConf method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ApiInfoCloudController syncNginxConf method catch Exception e = ", e);
        }
        return response;
    }


}
