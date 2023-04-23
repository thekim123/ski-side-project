package com.ski.backend.config;

import com.ski.backend.config.auth.PrincipalDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;


/**
 * @author jo-kim
 * @author thekim123
 * @apiNote Spring Data JPA 의 auditing 기능을 구현하는 클래스
 * 이 프로젝트에서는 String 타입으로 현재 사용자를 식별한다.
 * @since 2022.11.25
 * @since modified at 2023.04.23
 */
@Component
public class AuditorProvider implements AuditorAware<String> {


    /**
     * @return authentication 의 Principal 이 'anonymousUser' 이거나 null 이면 Optional.empty()를 리턴한다.
     * 그렇지 않으면 유저의 username 을 리턴한다.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
            return Optional.empty();
        }
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return Optional.ofNullable(principal.getUser().getUsername());
    }
}

