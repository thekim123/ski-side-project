package com.ski.backend.web.dto;

import com.ski.backend.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


/**
 * Save - insert, update 쿼리시 사용하는 dto
 * Get - select 쿼리시 사용하는 dto
 */
@NoArgsConstructor
@Getter
@Builder
public class BoardDto {

    @Getter
    @NoArgsConstructor
    public static class Save {
        private Long id;
        @NotBlank
        private String title;

        @NotBlank
        private String content;
        private UserDto userDto;
        private String nickname;

        @NotBlank
        private String resortName;
        private LocalDateTime createDate;
    }

    /**
     * Get의 경우 아직 구현이 안됨
     */
    public static class Get {

    }
}
