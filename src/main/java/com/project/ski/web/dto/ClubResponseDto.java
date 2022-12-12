package com.project.ski.web.dto;

import com.project.ski.domain.club.AgeGrp;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.Gender;
import com.project.ski.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
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

    private String url;

    private String nickName;

    public ClubResponseDto(long clubId, int memberCnt, String clubNm, long resortName, String openYn) {
        this.id = clubId;
        this.memberCnt = memberCnt;
        this.clubNm = clubNm;
        this.resortId = resortName;
        this.openYn = openYn;
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
    public ClubResponseDto(Club club, User user) {
        this.id = club.getId();
        this.memberCnt = club.getMemberCnt();
        this.clubNm = club.getClubNm();
        this.resortId = club.getResort().getId();
        this.gender = club.getGender();
        this.ageGrp = club.getAgeGrp();
        this.openYn = club.getOpenYn();
        this.memo = club.getMemo();
        this.nickName = user.getNickname();
    }
    public ClubResponseDto(long clubId,Club dto) {
        this.id     = dto.getId();
        this.clubNm = dto.getClubNm();
        this.memberCnt =dto.getMemberCnt();
        this.resortId = dto.getResort().getId();
        this.gender = dto.getGender();
        this.ageGrp = dto.getAgeGrp();
        this.openYn = dto.getOpenYn();
        this.memo = dto.getMemo();
        this.url = dto.getUrl();
    }
}
