package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ski.backend.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString
public class Whisper extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIncludeProperties({"username", "id", "carpools"})
    @ManyToOne
    private User principal;

    private String toUsername;
}
