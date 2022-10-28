package com.project.ski.domain.club;

import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
public class Club {

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
     * 홍보문구
     */

    private String memo;

    public void update(String clubNm, AgeGrp ageGrp, String openYn, String memo) {
        this.clubNm = clubNm;
        this.ageGrp = ageGrp;
        this.openYn = openYn;
        this.memo = memo;
    }
}
