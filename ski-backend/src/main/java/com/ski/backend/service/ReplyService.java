package com.ski.backend.service;

import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.Reply;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.ClubBoardRepository;
import com.ski.backend.repository.ReplyRepository;
import com.ski.backend.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ClubBoardRepository clubBoardRepository;


    @Transactional
    public ReplyDto saveReply(ReplyDto dto, User user) {
        ClubBoard cb = clubBoardRepository.findById(dto.getClubBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시판은 존재하지 않습니다."));

        Reply reply = dto.toEntity(user, cb);
        replyRepository.save(reply);
        return dto;
    }
    @Transactional
    public void deleteReply(Long replyId) {

        replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다"));

        replyRepository.deleteById(replyId);
    }


    @Transactional
    public ReplyDto updateReply(long replyId, ReplyDto dto) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다"));

        return reply.update(dto);

    }
}
