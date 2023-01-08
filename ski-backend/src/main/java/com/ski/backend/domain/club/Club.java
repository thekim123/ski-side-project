package com.ski.backend.domain.club;

import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.web.dto.ClubRequestDto;
import lombok.*;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "club",
                columnNames = "clubNm"
        )
})
public class Club extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "resort_id")
    private Resort resort;

    @OneToMany(mappedBy = "club", cascade = ALL,orphanRemoval = true)
    private List<ClubUser> clubUsers = new ArrayList<>();


    /**
     *  동호회명
     */
    @Column(length = 100, nullable = false)
    private String clubNm;

    /**
     *  인원수
     */
    @Column(nullable = false)
    private int memberCnt;

    /**
     * 연령대
     */
    @Column(nullable = false)
    @Enumerated(STRING)
    private AgeGrp ageGrp;

    /**
     * 성별 ( 1: 남 2: 여 3: 무관)
     */
    @Column(nullable = false)
    @Enumerated(STRING)
    private Gender gender;

    /**
     * 동호회 오픈 여부
     */
    @Column(nullable = false)
    private String openYn;

    /**
     * 오픈카톡 UR
     */
    private String url;

    /**
     * 홍보문구
     */
    @Column(nullable = false)
    private String memo;



    public void update(ClubRequestDto dto, Resort resort) {
        this.clubNm = dto.getClubNm();
        //this.memberCnt =dto.getMemberCnt(); 동호회 수정과 회원수는 관련 없으므로
        this.gender = dto.getGender();
        this.ageGrp = dto.getAgeGrp();
        this.openYn = dto.getOpenYn();
        this.memo = dto.getMemo();
        this.url = dto.getUrl();
        this.resort = resort;
    }

    public void addMember() {
        this.memberCnt++;
    }
}
