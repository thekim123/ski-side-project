package com.ski.backend.web.dto.club;

import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.club.entity.Club;
import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubResponseDto {


    // 동호회 식별 id
    private Long id;
    // 회원수
    private int memberCnt;

    // 동호회명
    private String clubNm;

    // 사용자
    private String userId;

    // 스키장
    private Long resortId;

    // 성별
    private Gender gender;

    // 연령대
    private AgeGrp ageGrp;

    // 비밀여부
    private String openYn;

    // 홍보 문구
    private String memo;

    // 임시저장 여부
    private String tempFlag;

    private String url;

    private String nickName;

    private List<ClubUser> clubUsers;


    @Builder
    public ClubResponseDto(Club club) {
        this.id = club.getId();
        this.memberCnt = club.getMemberCnt();
        this.clubNm = club.getClubNm();
        this.resortId = club.getResort().getId();
        this.gender = club.getGender();
        this.ageGrp = club.getAgeGrp();
        this.openYn = club.getOpenYn();
        this.memo = club.getMemo();
    }

}
