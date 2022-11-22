package com.project.ski.web.dto;

import com.project.ski.domain.club.AgeGrp;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.Gender;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.ResortName;
import com.project.ski.domain.user.User;
import com.project.ski.service.ClubService;
import lombok.*;

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



    public ClubRequestDto getDto(Club club) {
        this.memberCnt = club.getMemberCnt();
        this.clubNm = club.getClubNm();
        this.resortId = club.getResort().getId();
        return this;
    }


    public ClubRequestDto clubDto(Club club) {
        this.id = club.getId();
        this.memberCnt = club.getMemberCnt();
        this.clubNm = club.getClubNm();
        this.resortId = club.getResort().getId();
        this.gender = club.getGender();
        this.ageGrp = club.getAgeGrp();
        this.openYn = club.getOpenYn();
        this.memo = club.getMemo();
        return this;
    }

    public Club toEntity(User user, Resort resort) {
        return Club.builder()
                .clubNm(clubNm)
                .user(user)
                .memberCnt(memberCnt)
                .resort(resort)
                .gender(gender)
                .ageGrp(ageGrp)
                .openYn(openYn)
                .memo(memo)
                .url(url)
                .build();
    }


}
