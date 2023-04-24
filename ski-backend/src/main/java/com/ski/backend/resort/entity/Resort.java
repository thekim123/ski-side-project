package com.ski.backend.resort.entity;


import com.ski.backend.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Resort extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, unique = true)
    private ResortName resortName;

}
