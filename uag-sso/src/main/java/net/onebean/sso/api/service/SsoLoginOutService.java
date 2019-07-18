package net.onebean.sso.api.service;

import net.onebean.component.SpringUtil;
import net.onebean.core.BaseResponse;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.sso.api.common.ConstantEnum;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.service.UserAuthApi;
import net.onebean.util.StringUtils;
import net.onebean.util.UagSsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 登出操作
 *
 * @author 0neBean
 */
@Service
public class SsoLoginOutService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoLoginOutService.class);

    @Autowired
    private UserAuthApi userAuthApi;

    public Boolean logout() {
        try {
            UagLoginSessionInfo uagLoginInfo = UagSsoUtils.getCurrentUagLoginSessionInfo();
            String uagDeviceToken = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagDeviceToken).orElse("");
            String uagAppId = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagAppId).orElse("");
            String uagUsername = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUsername).orElse("");
            if (StringUtils.isEmpty(uagDeviceToken) || StringUtils.isEmpty(uagAppId)) {
                return false;
            }
            SpringUtil.getHttpServletRequest().getSession().removeAttribute(ConstantEnum.UAG_SSO_LOGIN_INFO.getName());
            LOGGER.info("did logout ,deviceToken = " + uagDeviceToken + " uagAppId = " + uagAppId + " uagUsername = " + uagUsername);

            CheckUagLoginStatusReq req = new CheckUagLoginStatusReq();
            req.setDeviceToken(uagDeviceToken);
            req.setAppId(uagAppId);
            BaseResponse<Boolean> resp = userAuthApi.uagLogOut(req);
            Boolean deleteLoginFlag = Optional.ofNullable(resp).map(BaseResponse::getDatas).orElse(false);
            LOGGER.info("did logout ,deleteLoginFlag = " + deleteLoginFlag);
            return deleteLoginFlag;
        } catch (Exception e) {
            LOGGER.error("logout failure e = ", e);
            return false;
        }
    }






}
