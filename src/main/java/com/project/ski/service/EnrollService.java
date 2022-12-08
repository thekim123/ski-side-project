package com.project.ski.service;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.club.Enroll;
import com.project.ski.domain.user.User;
import com.project.ski.handler.ex.CustomApiException;
import com.project.ski.repository.ClubRepository;
import com.project.ski.repository.ClubUserRepository;
import com.project.ski.repository.EnrollRepository;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.EnrollRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollService {

    private final EnrollRepository enrollRepository;

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;

    @Transactional
    public Long enroll(long userId, long clubId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다"));
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("해당 동호회가 없습니다"));

        Enroll enroll = new Enroll();
        enroll.signup(user, club);

        enrollRepository.save(enroll);
        ClubUser cu = new ClubUser(club, user);
        user.getClubUsers().add(cu);
        clubUserRepository.save(cu);
        return enroll.getId();
    }


}
