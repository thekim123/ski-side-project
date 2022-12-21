package com.ski.backend.service;

import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.ClubUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubServiceTest {

    @Autowired
    ClubUserRepository  clubUserRepository;

    @Test
    void 클럽관리자_체크() {
//        ClubUser result = clubUserRepository.findByClubId(3L, 2L, "관리자");
//
//        assertThat(result.getClub().getId()).isEqualTo(3L);

//        List<ClubUser> list = clubUserRepository.findByClubIdAAndStatus(3L, "대기");

//        assertThat(list.size()).isEqualTo(0);
//        assertThat(list.size()== 0).isTrue();
//
//        if(cu.getClub().getId() != clubId)
//            throw new CustomApiException("관리자만 동호회를 수정 / 삭제할 수 있습니다");
//
    }


}