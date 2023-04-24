package com.ski.backend.board.dto;

import lombok.Data;

@Data
public class LikesDto {
    private long id;
    private long boardId;
    private boolean likeState;
    private boolean dislikeState;
}
