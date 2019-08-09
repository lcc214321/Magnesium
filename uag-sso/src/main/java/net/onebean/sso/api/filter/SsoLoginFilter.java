package net.onebean.sso.api.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.sso.api.common.ConstantEnum;
import net.onebean.sso.api.vo.GetAccTokenRequest;
import net.onebean.sso.api.vo.GetAccessTokenResponse;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import net.onebean.user.mngt.api.model.enumModel.PrivateTokenLoginFlagEnum;
import net.onebean.user.mngt.api.service.UserAuthApi;
import net.onebean.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * 过滤器
 *
 * @author 0neBean
 */
@Service
@WebFilter(filterName = "ssoLoginFilter")
public class SsoLoginFilter implements Filter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoLoginFilter.class);
    private static String SSO_BASE_OAUTH_URL;
    private static String SSO_UAG_APP_ID;
    private static String SSO_UAG_SECRET;
    private static String SSO_UAG_ACCESS_TOKEN_URL;

    static {
        SSO_BASE_OAUTH_URL = PropUtil.getInstance().getConfig("sso.base.oauth.url", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_APP_ID = PropUtil.getInstance().getConfig("sso.uag.app.id", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_SECRET = PropUtil.getInstance().getConfig("sso.uag.secret", PropUtil.PUBLIC_CONF_SSO);
        SSO_UAG_ACCESS_TOKEN_URL = PropUtil.getInstance().getConfig("sso.uag.access.token.url", PropUtil.PUBLIC_CONF_SSO);
    }

    @Autowired
    private UserAuthApi userAuthApi;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (pathMatcher.match("/**/*.html", request.getRequestURI())) {
            // 拦截静态资源
            try {
                checkLoginStatus(request, response);
            } catch (Exception e) {
                LOGGER.error("checkLoginStatus got a err is ,gonna resend redirect", e);
                response.sendRedirect(generateSsoCompleteUrl());
            }
        }
        //执行操作后必须doFilter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void checkLoginStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*获取参数里的deviceToken*/
        String uagDeviceTokenFromReqParam = request.getParameter(ConstantEnum.UAG_DEVICE_TOKEN_PARAM_KEY.getName());
        /*获取session里的deviceToken*/
        String uagDeviceTokenFromSession = Optional.ofNullable(UagSsoUtils.getCurrentUagLoginSessionInfo()).map(UagLoginSessionInfo::getUagDeviceToken).orElse("");
        /*获取带有accessToken的url*/
        String finalSsoUrl = generateSsoCompleteUrl();
        /*如果两个deviceToken 都为空 铁定是没登录的url*/
        if (StringUtils.isEmpty(uagDeviceTokenFromReqParam) && StringUtils.isEmpty(uagDeviceTokenFromSession)) {
            /*未登录*/
            response.sendRedirect(finalSsoUrl);
        } else if (StringUtils.isNotEmpty(uagDeviceTokenFromReqParam) || StringUtils.isNotEmpty(uagDeviceTokenFromSession)) {
            /*登录后获取到了参数里的deviceToken或session里已有deviceToken 都需要校验其有效性*/
            CheckUagLoginStatusReq req = new CheckUagLoginStatusReq();
            req.setAppId(SSO_UAG_APP_ID);
            if (StringUtils.isNotEmpty(uagDeviceTokenFromReqParam)) {
                req.setDeviceToken(uagDeviceTokenFromReqParam);
            } else {
                req.setDeviceToken(uagDeviceTokenFromSession);
            }
            BaseResponse<UagLoginInfo> resp = null;
            try {
                LOGGER.info("send cloud api request,check uag login flag");
                resp = userAuthApi.checkUagLoginInfo(req);
            } catch (Exception e) {
                LOGGER.error("check checkUagLoginInfo failure,uri is " + request.getRequestURI() + "e = ", e);
            }
            /*获取到校验登录信息的结果*/
            String isLogin = Optional.ofNullable(resp).map(BaseResponse::getDatas).map(UagLoginInfo::getLoginStatus).orElse("");
            UagLoginInfo uagLoginInfo = Optional.ofNullable(resp).map(BaseResponse::getDatas).orElse(new UagLoginInfo());
            if (isLogin.equals(PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey())) {
                /*如果登录成功信息放入session*/
                LOGGER.info("login success request ,pass url is " + "/?" + request.getQueryString());
                HttpSession session = request.getSession();
                UagLoginSessionInfo loginSessionInfo = new UagLoginSessionInfo();
                BeanUtils.copyProperties(uagLoginInfo, loginSessionInfo);
                loginSessionInfo.setUagAppId(SSO_UAG_APP_ID);
                loginSessionInfo.setUagDeviceToken(req.getDeviceToken());
                session.setAttribute(ConstantEnum.UAG_SSO_LOGIN_INFO.getName(), JSONUtil.toJson(loginSessionInfo));
                if (StringUtils.isNotEmpty(uagDeviceTokenFromReqParam)) {
                    response.sendRedirect(request.getRequestURI());
                }
            } else {
                /*如果当前登录失效 重定向到登录页面*/
                response.sendRedirect(finalSsoUrl);
            }
        }
    }

    private static String generateSsoCompleteUrl() {
        String accessToken = getAccessToken();
        String oauthUrl = SSO_BASE_OAUTH_URL + "#/" + "?uagAppId=" + SSO_UAG_APP_ID + "&uagAccessToken=" + accessToken;
        LOGGER.error("oauthUrl is = " + oauthUrl);
        return oauthUrl;
    }

    private static String getAccessToken() {
        GetAccTokenRequest request = new GetAccTokenRequest();
        String timestamp = DateUtils.getTimeStamp();
        request.setAppId(SSO_UAG_APP_ID);
        request.setSecret(SSO_UAG_SECRET);
        request.setTimestamp(timestamp);
        request.setSign(TokenCheckUtils.sign(SSO_UAG_APP_ID, SSO_UAG_SECRET, timestamp));
        String respJsonStr = (String) RestUtils.getInstance().doPostForObj(SSO_UAG_ACCESS_TOKEN_URL, request, String.class);
        BaseResponse<GetAccessTokenResponse> resp = JSON.parseObject(respJsonStr, new TypeReference<BaseResponse<GetAccessTokenResponse>>() {
        });
        String accessToken = Optional.ofNullable(resp).map(BaseResponse::getDatas).map(GetAccessTokenResponse::getAccessToken).orElse("");
        if (StringUtils.isEmpty(accessToken)) {
            LOGGER.error("un login request ,accessToken is empty");
        }
        return accessToken;
    }


}
