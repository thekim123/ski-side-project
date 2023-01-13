package com.ski.backend.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    private long commentId;

    @NotBlank
    private String content;

    private long boardId;
}
