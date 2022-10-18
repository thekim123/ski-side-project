package com.project.ski.config.oauth;

public interface OAuthUserInfo {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getUsername();

}
