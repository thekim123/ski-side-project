package com.ski.backend.config.oauth;

import java.util.Map;

public class KakaoUser implements OAuthUserInfo {

    private Map<String, Object> attribute;

    public KakaoUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attribute.get("id"));
    }

    @Override
    public String getEmail() {
        Map<String, Object> accountInfo = (Map<String, Object>) attribute.get("kakao_account");
        return (String) accountInfo.get("email");
    }

    @Override
    public String getUsername() {
        Map<String, Object> accountInfo = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profileInfo = (Map<String, Object>) accountInfo.get("profile");
        return (String) profileInfo.get("nickname");
    }

    public String getAgeRange() {
        Map<String, Object> accountInfo = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profileInfo = (Map<String, Object>) accountInfo.get("profile");
        String rawAgeGrp = (String) profileInfo.get("age_range");
        return rawAgeGrp;
    }

    public String getGender() {
        Map<String, Object> accountInfo = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profileInfo = (Map<String, Object>) accountInfo.get("profile");
        return (String) profileInfo.get("gender");
    }
}
