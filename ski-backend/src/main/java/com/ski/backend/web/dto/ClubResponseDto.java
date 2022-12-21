package com.ski.backend.web.dto;

import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
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

    public ClubResponseDto(long clubId, int memberCnt, String clubNm, long resortName, String openYn) {
        this.id = clubId;
        this.memberCnt = memberCnt;
        this.clubNm = clubNm;
        this.resortId = resortName;
        this.openYn = openYn;
    }

    public ClubResponseDto(long clubId, String clubNm, String memo, String openYn, int memberCnt) {
        this.id = clubId;
        this.clubNm = clubNm;
        this.memo = memo;
        this.openYn = openYn;
        this.memberCnt = memberCnt;
    }
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
