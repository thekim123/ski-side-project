package com.ski.backend.domain.Tayo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.common.Status;
import com.ski.backend.user.entity.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TayoUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnoreProperties({"boards", "clubUsers", "carpools", "tayoUsers"})
    @ManyToOne(fetch = LAZY)// 여러 사용자가 한 클럽에 속할 수 있으니까
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)  // 개인이 여러개의 클럽에 속할 수 있으니까
    @JoinColumn(name = "tayo_id")
    private Tayo tayo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(STRING)
    private Role role;

    public TayoUser(Tayo tayo, User user, Status status, Role role) {
        this.tayo = tayo;
        this.user = user;
        this.status = status;
        this.role = role;
    }

    public TayoUser(Tayo tayo, User user) {
        this.tayo = tayo;
        this.user = user;
        this.status = Status.WAITING;
        this.role = Role.NONMEMBER;
    }

    public void admit() {
        this.status = Status.ADMIT;
        this.role = Role.MEMBER;
    }

    public void decline() {
        this.status = Status.DENIED;
    }
}
