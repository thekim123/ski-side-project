package com.ski.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Builder
public class CarpoolRequestDto {

    @Getter
    @NoArgsConstructor
    public static class Save {
        private Long id;
        @NotBlank
        private String departure;

        @NotBlank
        private String destination;

        @NotBlank
        private String boarding;

        private boolean smoker;

        private NegotiateDto negotiateDto;
        private int passenger;
        private String memo;

        @NotBlank
        private String request;

        private String departTime;


    }

}
