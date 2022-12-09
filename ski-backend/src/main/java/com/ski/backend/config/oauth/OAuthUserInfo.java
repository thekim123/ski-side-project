package com.ski.backend.config.oauth;

public interface OAuthUserInfo {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getUsername();

}
