package com.ski.backend.service;

import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Enroll;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.ClubRepository;
import com.ski.backend.repository.ClubUserRepository;
import com.ski.backend.repository.EnrollRepository;
import com.ski.backend.repository.UserRepository;
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
