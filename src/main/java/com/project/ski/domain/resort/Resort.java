package com.project.ski.domain.resort;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, unique = true)
    private String resortName;

    private String lat;
    private String lon;

    @OrderBy("id DESC")
    @JsonIgnoreProperties("resort")
    @OneToMany(mappedBy = "resort", fetch = FetchType.EAGER)
    private List<Weather> weather;

    public String buildApiUrl(String weatherMapKey) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat="
                + lat + "&lon="
                + lon + "&appid=" + weatherMapKey
                + "&units=metric";
        return apiUrl;
    }

}
