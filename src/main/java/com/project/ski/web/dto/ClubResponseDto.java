package com.project.ski.web.dto;

import com.project.ski.domain.club.AgeGrp;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.Gender;
import lombok.Builder;
import lombok.Data;


@Data
public class ClubResponseDto {


    // 동호회 식별 id
    private Long id;
    // 회원수
    private int memberCnt;

    // 동호회명
    private String clubNm;

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

    public ClubResponseDto(int memberCnt, String clubNm, long resortName) {
        this.memberCnt = memberCnt;
        this.clubNm = clubNm;
        this.resortId = resortName;
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
