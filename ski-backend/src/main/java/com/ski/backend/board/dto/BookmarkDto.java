package com.ski.backend.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookmarkDto {
    private long id;
    private String username;
    private String resortName;
    private Integer bookmarkState;
    private Integer equalUserState;
}
