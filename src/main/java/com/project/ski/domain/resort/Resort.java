package com.project.ski.domain.resort;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, unique = true)
    private String resortName;

//    @OneToMany
//    private List<Weather> weather;
}
