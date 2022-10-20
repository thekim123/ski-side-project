package com.project.ski.domain.resort;

import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Data
public class Weather {

    private String resortName;
    private String weatherDescription;
    private Double temp;
    private Double feelsLike;
    private Double wind;

    //json으로부터 Weather을 build함.
    public Weather buildWeather(String apiUrl) {
        try {
            String jsonStr = getWeatherApiJson(apiUrl);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);

            JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
            JSONObject weatherObj = (JSONObject) weatherArray.get(0);
            JSONObject tempObj = (JSONObject) jsonObject.get("main");
            JSONObject windObj = (JSONObject) jsonObject.get("wind");

            temp = ((Double) tempObj.get("temp"));
            weatherDescription = ((String) weatherObj.get("description"));
            feelsLike = ((Double) tempObj.get("feels_like"));
            wind = ((Double) windObj.get("speed"));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this;
    }


    // api주소를 입력하면 json데이터를 받아옴
    public String getWeatherApiJson(String apiUrl) {
        try {
            StringBuilder sb = new StringBuilder();

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "application/json");
            BufferedReader br;
            if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            }
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}