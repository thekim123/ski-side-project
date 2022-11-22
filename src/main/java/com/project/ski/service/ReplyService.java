package com.project.ski.service;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.Reply;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ClubBoardRepository;
import com.project.ski.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ClubBoardRepository clubBoardRepository;

    @Transactional
    public void saveReply(Long clubId, Reply reply, User user) {
        ClubBoard clubBoard = clubBoardRepository.findById(clubId).orElseThrow(() -> {
          return new IllegalArgumentException("해당 게시판이 없습니다 id = " + clubId);
        });
        reply.save(clubBoard, user);
        replyRepository.save(reply);
    }
    @Transactional
    public void deleteReply(Long replyId) {
        ClubBoard clubBoard = clubBoardRepository.findById(replyId).orElseThrow(() ->{
                return new IllegalArgumentException("해당 댓글이 없습니다 id = " + replyId);
        });

        replyRepository.deleteById(replyId);
    }
}
