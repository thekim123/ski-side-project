package com.ski.backend.repository;

import com.ski.backend.web.dto.ClubResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubUserRepositoryTest {

    @Autowired
    private ClubUserRepository clubUserRepository;
    @Autowired
    private ClubRepository clubRepository;

//    @Test
//    void 클럽유저카운트() {
//        Long aLong = clubUserRepository.countClubUserByClubId(1L);
//        System.out.println("aLong = " + aLong);
//    }



}