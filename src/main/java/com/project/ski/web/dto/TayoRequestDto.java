package com.project.ski.web.dto;


import com.project.ski.domain.Tayo.Tayo;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TayoRequestDto {

    // 타요 식별 id
    private Long id;

    private Long resortId;

    private String title;

    private String age;

    private int tayoMemCnt;

    private LocalDateTime createDt;

    public TayoRequestDto tayoDto(Tayo tayo) {
        this.id = tayo.getId();
        this.resortId = tayo.getResort().getId();
        this.title = tayo.getTitle();
        this.age =tayo.getAge();
        this.tayoMemCnt = tayo.getTayoMemCnt();
        return this;
    }

    public Tayo toEntity(User user) {
        return Tayo.builder()
                .user(user)
                .title(title)
                .resort(new Resort())
                .age(age)
                .tayoMemCnt(tayoMemCnt)
                .build();
    }
}
