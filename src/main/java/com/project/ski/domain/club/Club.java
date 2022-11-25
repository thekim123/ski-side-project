package com.project.ski.domain.club;

import com.project.ski.domain.BaseTimeEntity;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.ClubRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Getter
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resort_id")
    private Resort resort;

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


    public void update(ClubRequestDto dto) {
        this.clubNm = dto.getClubNm();
        this.memberCnt =dto.getMemberCnt();
        this.gender = dto.getGender();
        this.ageGrp = dto.getAgeGrp();
        this.openYn = dto.getOpenYn();
        this.memo = dto.getMemo();
        this.url = dto.getUrl();
    }
}
