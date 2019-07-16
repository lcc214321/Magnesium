package net.onebean.server.mngt.action.nginxNodeInfo.cloud;

import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import net.onebean.server.mngt.api.model.NginxNodeSyncVo;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.service.NginxNodeService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/nginxInfo")
public class NginxNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(NginxNodeInfoCloudController.class);

    @Autowired
    private NginxNodeService nginxNodeService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "findSyncNginxNode",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<NginxNodeSyncVo> findSyncNginxNode(){
        logger.info("NginxNodeInfoCloudController findSyncNginxNode method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<NginxNodeSyncVo> response = new BasePaginationResponse<>();
        try {
            response = BasePaginationResponse.ok(nginxNodeService.findSyncableNode());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoCloudController findSyncNginxNode method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findSyncNginxNode method catch Exception e = ",e);
        }
        return response;
    }




}
