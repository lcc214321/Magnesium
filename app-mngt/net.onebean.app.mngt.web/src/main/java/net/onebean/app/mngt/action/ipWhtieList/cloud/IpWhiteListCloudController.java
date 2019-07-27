package net.onebean.app.mngt.action.ipWhtieList.cloud;

import net.onebean.app.mngt.api.model.IpWhiteListVo;
import net.onebean.app.mngt.common.ErrorCodesEnum;
import net.onebean.app.mngt.service.IpWhiteListService;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 0neBean
 * ip白名单
 */
@RestController
@RequestMapping("/cloud/ipWhtieList")
public class IpWhiteListCloudController {


    private final static Logger logger = LoggerFactory.getLogger(IpWhiteListCloudController.class);
    
    @Autowired 
    private IpWhiteListService ipWhiteListService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<IpWhiteListVo> find() {
        logger.info("IpWhiteListCloudController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<IpWhiteListVo> response = new BasePaginationResponse<>();
        try {
            response = BasePaginationResponse.ok(ipWhiteListService.findSyncList());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("IpWhiteListCloudController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("IpWhiteListCloudController find method catch Exception e = ",e);
        }
        return response;
    }
}
