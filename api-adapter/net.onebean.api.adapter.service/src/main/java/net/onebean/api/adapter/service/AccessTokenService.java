package net.onebean.api.adapter.service;

import net.onebean.api.adapter.vo.GetAccTokenRequest;
import net.onebean.api.adapter.vo.GetAccessTokenResponse;

public interface AccessTokenService {
    /**
     * 获取accessToken
     * @param getAccTokenRequest 参数体
     * @return GetAccessTokenResponse
     */
    GetAccessTokenResponse getAccessToken(GetAccTokenRequest getAccTokenRequest);
}
