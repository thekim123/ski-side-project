package com.ski.backend.web.dto;

import lombok.Data;

@Data
public class CommentDto {
    private long commentId;
    private long boardId;
    private String content;
}
