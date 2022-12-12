package com.ski.backend.web.dto;

import lombok.Data;

@Data
public class LikesDto {
    private long id;
    private long boardId;
    private boolean likeState;
    private boolean dislikeState;
}
