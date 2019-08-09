package net.onebean.api.adapter.service.impl;

import net.onebean.api.adapter.api.model.GetAccTokenVo;
import net.onebean.api.adapter.common.ErrorCodesEnum;
import net.onebean.api.adapter.enumModel.CacheConstants;
import net.onebean.api.adapter.provider.mq.SetAccessTokenCacheSender;
import net.onebean.api.adapter.service.AccessTokenService;
import net.onebean.api.adapter.vo.GetAccTokenRequest;
import net.onebean.api.adapter.vo.GetAccessTokenResponse;
import net.onebean.app.mngt.api.model.AppInfoCloudVo;
import net.onebean.app.mngt.api.model.FindAppByAppIdAndSecretReq;
import net.onebean.app.mngt.api.model.constant.AppInfoAppCategoryEnum;
import net.onebean.app.mngt.api.model.constant.AuthTypeEnum;
import net.onebean.app.mngt.api.service.AppInfoApi;
import net.onebean.component.AccessTokenEncoder;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.util.ApolloPropUtils;
import net.onebean.util.StringUtils;
import net.onebean.util.TokenCheckUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessTokenServiceImpl.class);


    @Autowired
    private SetAccessTokenCacheSender setAccessTokenCacheSender;
    @Autowired
    private AccessTokenEncoder accessTokenEncoder;
    @Autowired
    private AppInfoApi appInfoApi;


    @Override
    public GetAccessTokenResponse getAccessToken(GetAccTokenRequest getAccTokenRequest) {
        /*参数拆包*/
        String appId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getAppId).orElse("");
        String secret = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getSecret).orElse("");
        String timestamp = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getTimestamp).orElse("");
        String sign = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getSign).orElse("");
        String tenantId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getTenantId).orElse("");
        String deviceToken = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getDeviceToken).orElse("");
        String customerId = Optional.of(getAccTokenRequest).map(GetAccTokenRequest::getCustomerId).orElse("");
        String accessToken_expire = ApolloPropUtils.getString("access.token.expire");

        /*参数非空校验*/
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(secret) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        /*时间戳校验异常*/
        if (!TokenCheckUtils.legalTimeStamp4AccToken(timestamp)) {
            LOGGER.info("AccessTokenServiceImpl method getAccessToken currentTimeMillis = " + System.currentTimeMillis());
            throw new BusinessException(ErrorCodesEnum.TIME_STAMP_ERROR.code(), ErrorCodesEnum.TIME_STAMP_ERROR.msg());
        }

        /*签名校验异常*/
        if (!TokenCheckUtils.checkSignMd5(sign, appId, secret, customerId, tenantId, deviceToken, timestamp)) {
            LOGGER.info("AccessTokenServiceImpl method getAccessToken correct sign = " + TokenCheckUtils.sign(appId, secret, customerId, tenantId, deviceToken, timestamp));
            throw new BusinessException(ErrorCodesEnum.SIGN_ERROR.code(), ErrorCodesEnum.SIGN_ERROR.msg());
        }


        if (StringUtils.isEmpty(accessToken_expire)) {
            throw new BusinessException(ErrorCodesEnum.READ_VALUE_FROM_APOLLO.code(), ErrorCodesEnum.READ_VALUE_FROM_APOLLO.msg() + " field of accessToken_expire");
        }

        /*通过spring cloud接口校验入参的合法性*/
        FindAppByAppIdAndSecretReq req = new FindAppByAppIdAndSecretReq();
        req.setOpenId(appId);
        req.setSecret(secret);
        BaseResponse<AppInfoCloudVo> baseResponse = appInfoApi.findByAppIdAndSecret(req);
        String errCode = Optional.ofNullable(baseResponse).map(BaseResponse::getErrCode).orElse("");
        if (!errCode.equals(ErrorCodesEnum.SUSSESS.code())) {
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERR.code(), ErrorCodesEnum.CLOUD_API_ERR.msg() + " method of findByAppIdAndSecret");
        }

        AppInfoCloudVo app = Optional.ofNullable(baseResponse).map(BaseResponse::getDatas).orElse(null);
        if (null == app || StringUtils.isEmpty(app.toString())) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " appId || secret is not exits");
        }

        String authType = Optional.of(app).map(AppInfoCloudVo::getAuthType).orElse(null);
        String appCategory = Optional.of(app).map(AppInfoCloudVo::getAppCategory).orElse(null);

        if (StringUtils.isEmpty(appCategory)) {
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERR.code(), ErrorCodesEnum.CLOUD_API_ERR.msg() + " method of findByAppIdAndSecret,appCategory is empty");
        }

        if (StringUtils.isEmpty(authType)) {
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERR.code(), ErrorCodesEnum.CLOUD_API_ERR.msg() + " method of findByAppIdAndSecret,authType is empty");
        }

        if (appCategory.equals(AppInfoAppCategoryEnum.CLOUD_APP.getKey())) {
            if (StringUtils.isEmpty(tenantId)) {
                throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " field of tenantId");
            }
        }

        /*装在缓存信息*/
        GetAccTokenVo vo = new GetAccTokenVo();
        vo.setAccessTokenExpire(accessToken_expire);
        /*生成AccessToken*/
        String accessToken = accessTokenEncoder.generatorAccessToken(appId, secret, timestamp);
        String accessToeknCacheKey = CacheConstants.generateKey(CacheConstants.ACCESSTOKEN_KEY, appId);
        try {
            BeanUtils.copyProperties(vo, getAccTokenRequest);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + " appId || secret is not exits");
        }
        vo.setAccessToken(accessToken);

        /*私有令牌模式*/
        if (authType.equals(AuthTypeEnum.OAUTH_PRIVATE_CODE.getKey()) || authType.equals(AuthTypeEnum.AVOID_LOGIN.getKey())) {

            if (authType.equals(AuthTypeEnum.AVOID_LOGIN.getKey())) {
                if (StringUtils.isEmpty(customerId)) {
                    throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " field of customerId");
                }
            }

            accessToeknCacheKey = CacheConstants.generateKey(accessToeknCacheKey, CacheConstants.RS_SALES_PRIVATE_TOKEN_KEY);
            /*参数非空校验*/
            if (StringUtils.isEmpty(deviceToken)) {
                throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " field of deviceToken");
            }
            accessToeknCacheKey = CacheConstants.generateKey(accessToeknCacheKey, accessToken);

            vo.setAccessTokenCacheKey(accessToeknCacheKey);
            /*设置私有令牌缓存*/
            setAccessTokenCacheSender.send(vo);
            return new GetAccessTokenResponse(appId, accessToken, accessToken_expire);
        }


        /*设置redis缓存*/
        vo.setAccessTokenCacheKey(accessToeknCacheKey);
        setAccessTokenCacheSender.send(vo);
        return new GetAccessTokenResponse(appId, accessToken, accessToken_expire);
    }

}
