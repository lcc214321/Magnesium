package net.onebean.server.mngt.action.upsteamNodeInfo.cloud;

import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import net.onebean.server.mngt.api.model.UpSteamSyncNodeVo;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.service.UpSteamNodeService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/upSteamNodeInfo")
public class UpSteamNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNodeInfoCloudController.class);

    @Autowired
    private UpSteamNodeService upSteamNodeService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findSyncUpSteamNode",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UpSteamSyncNodeVo> findSyncUpSteamNode(){
        logger.info("UpSteamNodeInfoCloudController findSyncUpSteamNode method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpSteamSyncNodeVo> response = new BasePaginationResponse<>();
        try {
            response = BasePaginationResponse.ok(upSteamNodeService.findSyncNode());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("UpSteamNodeInfoCloudController findSyncUpSteamNode method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findSyncUpSteamNode method catch Exception e = ",e);
        }
        return response;
    }




}
