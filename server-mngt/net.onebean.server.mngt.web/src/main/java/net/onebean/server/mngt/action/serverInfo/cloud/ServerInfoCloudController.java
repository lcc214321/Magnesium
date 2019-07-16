package net.onebean.server.mngt.action.serverInfo.cloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.only.serializer.json.BasePaginationResponse;
import net.onebean.server.mngt.api.model.FindServerByNameReq;
import net.onebean.server.mngt.api.model.ServerBasicInfo;
import net.onebean.server.mngt.api.service.ServerInfoApi;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.service.ServerInfoService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloud/serverInfo")
public class ServerInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoCloudController.class);
    
    @Autowired
    private ServerInfoService serverInfoService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findServerByName",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<ServerBasicInfo> findServerByName(@RequestBody @Validated FindServerByNameReq request, BindingResult result){
        logger.info("ServerInfoCloudController findServerByName method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerBasicInfo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoCloudController findServerByName method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BasePaginationResponse.ok(serverInfoService.findServerInfo(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoCloudController findServerByName method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoCloudController findServerByName method catch Exception e = ",e);
        }
        return response;
    }


}
