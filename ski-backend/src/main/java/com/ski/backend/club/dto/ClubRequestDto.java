package com.ski.backend.club.dto;

import com.ski.backend.user.entity.AgeGrp;
import com.ski.backend.club.entity.Club;
import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.Gender;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.user.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubRequestDto {

    // 동호회 식별 id
    private Long id;
    // 회원수
    private int memberCnt;

    // 동호회명
    private String clubNm;

    // 스키장
    private long resortId;


    // 성별
    private Gender gender;

    // 연령대
    private AgeGrp ageGrp;

    // 비밀여부
    private String openYn;

    // 홍보 문구
    private String memo;

    // 오픈카톡 URL
    private String url;

    private List<ClubUser> user;


    public Club toEntity(User user, Resort resort) {
        return Club.builder()
                .clubNm(clubNm)
                .memberCnt(+1)
                .resort(resort)
                .gender(gender)
                .ageGrp(ageGrp)
                .openYn(openYn)
                .memo(memo)
                .url(url)
                .clubAdmin(user)
                .build();
    }


}
