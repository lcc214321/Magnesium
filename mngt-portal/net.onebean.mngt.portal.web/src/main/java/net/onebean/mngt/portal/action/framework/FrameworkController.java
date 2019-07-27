package net.onebean.mngt.portal.action.framework;

import net.onebean.app.mngt.api.model.FindBasicMenuListResp;
import net.onebean.app.mngt.api.service.AppInfoApi;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.mngt.portal.common.ErrorCodesEnum;
import net.onebean.sso.api.service.SsoLoginOutService;
import net.onebean.util.DateUtils;
import net.onebean.util.UagSsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("framework")
public class FrameworkController {

    private final static Logger logger = LoggerFactory.getLogger(FrameworkController.class);

    @Autowired
    private AppInfoApi appInfoApi;

    @Autowired
    private SsoLoginOutService ssoLoginOutService;



    @SuppressWarnings("unchecked")
    @PostMapping(value = "getCurrentUagLoginInfo",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginSessionInfo> getCurrentUagLoginInfo() {
        logger.info("getCurrentUagLoginInfo method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginSessionInfo> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(UagSsoUtils.getCurrentUagLoginSessionInfo());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("getCurrentUagLoginInfo method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("getCurrentUagLoginInfo method catch Exception e = ",e);
        }
        return response;
    }



    @PostMapping(value = "findBasicMenuList",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<FindBasicMenuListResp> findBasicMenuList() {
        logger.info("findBasicMenuList method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<FindBasicMenuListResp> response = new BasePaginationResponse<>();
        try {
            response = appInfoApi.findBasicMenuList();
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findBasicMenuList method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findBasicMenuList method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "logout",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> logout() {
        logger.info("logout method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(ssoLoginOutService.logout());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("logout method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("logout method catch Exception e = ",e);
        }
        return response;
    }
}
