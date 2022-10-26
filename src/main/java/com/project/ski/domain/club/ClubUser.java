package com.project.ski.domain.club;

import com.project.ski.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter

public class ClubUser {

    @ManyToOne(fetch = LAZY)  // 개인이 여러개의 클럽에 속할 수 있으니까
    private List<Club> clubs = new ArrayList<>();


    @ManyToOne(fetch = LAZY) // 여러 사용자가 한 클럽에 속할 수 있으니까
    private List<User> users = new ArrayList<>();
}
