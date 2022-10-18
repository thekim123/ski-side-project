package com.project.ski.service;

import com.project.ski.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

//    @Transactional
//    public void writeBoard(Board board, User user) {
//        board.setUser(user);
//        boardRepository.save(board);
//    }

    public void deleteBoard() {

    }

    public void updateBoard() {

    }

    public void getBoardList() {

    }


}
