import axios from 'axios';
import React, { useState } from 'react'
import styled from 'styled-components'
import img from '../assets/imgs/한반도.png'
import { ResortModal } from './ResortModal';
import shortid from 'shortid';
import { loadBookmarks } from '../action/bookmark';
import { useDispatch } from 'react-redux';
import {FaSkiing} from 'react-icons/fa'

export function SkiButton(props) {
    const dispatch = useDispatch();
    const [resortOpen, setResortOpen] = useState(false);
    const [dayState, setDayState] = useState({
        /*
        date: "",
        day: "",
        currentWeather: "",
        tempCelcius: "",
        tempMax: "",
        tempMin: "",
        icon: "",
        week: []*/
        hourly_info: [],
        date: "",
        currentWeather: "",
        tempCelcius: "",
        tempMax: "",
        tempMin: "",
        icon: "",
    })
    const [isLoading, setIsLoading] = useState(false);
    const API_KEY = process.env.REACT_APP_WEATHER_KEY;

    const openResort = () => {
        setResortOpen(true);
        getWeather(props.lat, props.lon);
    }

    const closeResort = () => {
        dispatch(loadBookmarks());
        setResortOpen(false);
    }

    const getWeather = async (lat, lon) => {
        try{
            setIsLoading(true);
            //현재 날씨 정보
            const resWeather = await axios.get(
                `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
            );

                let _main = resWeather.data.weather[0].main;
                let _icon = resWeather.data.weather[0].icon;
                let _temp = resWeather.data.main.temp;

                let _test = "http://openweathermap.org/img/w/" + _icon + ".png";

            // 5일 간 최저, 최고 기온
            /*
            let date_now = new Date();
            let m = date_now.getMonth() + 1;
            let d = date_now.getDate();
            let idx = date_now.getDay();
            let week = ['일', '월', '화', '수', '목', '금', '토', '일', '월', '화', '수', '목', '금', '토'];
            let weekSlice = week.slice(idx, idx+5);*/

            const resWeek = await axios.get(
                `https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
            );

            let hourly_info = [];
            /*
            let w_object = {
                dt: "",
                temp_c: 0,
                description: "",
                icon: "",
            }*/
            let icon_str;
            for (let i = 0; i < 40; i++) {
                icon_str = resWeek.data.list[i].weather[0].icon;
                let w_object = {
                    dt: resWeek.data.list[i].dt_txt,
                    temp_c: resWeek.data.list[i].main.temp,
                    description: resWeek.data.list[i].weather[0].description,
                    icon: "http://openweathermap.org/img/w/" + icon_str + ".png",
                }
                /*
                w_object.dt = resWeek.data.list[i].dt_txt; //나중에 결과 보고 시간만 slice하기
                w_object.temp_c = resWeek.data.list[i].main.temp;
                w_object.description = resWeek.data.list[i].weather[0].description;

                icon_str = resWeek.data.list[i].weather.icon;
                w_object.icon = "http://openweathermap.org/img/w/" + icon_str + ".png";*/

                hourly_info.push(w_object);
            }

            // 오늘 최고, 최저 기온
            const today_hourly = hourly_info.slice(0, 8).map(w => w.temp_c);
            const today_min = Math.min.apply(null, today_hourly);
            const today_max = Math.max.apply(null, today_hourly);

            /*
            let min_arr = [];
            let max_arr = [];
            for (let i = 0; i < 40; i++) {
                min_arr.push(resWeek.data.list[i].main.temp_min);
                max_arr.push(resWeek.data.list[i].main.temp_max);
            }
            let minMax = [];
            for (let i = 0; i < 40; i += 8) {
                let i_min = Math.min.apply(null, min_arr.slice(i, i + 8));
                let i_max = Math.max.apply(null, max_arr.slice(i, i + 8));
                minMax.push({"day": weekSlice[i / 8], "min": Math.floor(i_min * 10) / 10, "max": Math.ceil(i_max * 10) / 10});
            }*/
            
            setDayState({
                ...dayState,
                hourly_info,
                //date: m+'월 '+d+'일 ',
                //day: week[idx],
                date: hourly_info[0].dt,
                currentWeather: _main,
                tempCelcius: Math.round(_temp * 10) / 10,
                tempMax: Math.ceil(today_max * 10) / 10,
                tempMin: Math.floor(today_min * 10) / 10,
                //tempMax: minMax[0].max,
                //tempMin: minMax[0].min,
                icon: _test,
                //week: minMax
            })
            setIsLoading(false);
        } catch (error) {
            console.log(error);
            console.log("날씨 정보 get 실패");
            //setError(true);
            setIsLoading(false);
        }
    };

    const MapButton = <Button>
                            <Region className={props.color ? "kang" : "else"}>{props.region}</Region>
                            <ResortName onClick={openResort}>{props.name}</ResortName>
                        </Button>

    const MyResortBtn = <MySkiWrap key={shortid.generate()} onClick={openResort}>
                        <MySki>
                            <FaSkiing className="home-ski-icon" />
                        </MySki>
                        <div className="home-resort-name">{props.name}</div>
                        </MySkiWrap>

    return (
        <div key={shortid.generate()}>
        {props.id ? 
        <>
        {props.isMyBtn ? MyResortBtn : MapButton}
        <ResortModal  
            open={resortOpen} 
            close={closeResort} 
            resortId={props.id} 
            header={props.name} 
            engCity="Seoul" 
            dayState={dayState} 
            like={props.like}
            loading={isLoading}
        />
        </>
        : <Empty></Empty>}
        </div>
    )
}
const Empty = styled.div`
width: 10px;
`
const Button = styled.div`
display: flex;
font-size: 12px;
margin: 10px;
margin-left: 20px;
.kang{
    background-color: #C2CFD8;
    //background-color: #F5EFE6;
    //background-color: gray;
    //color: #FAFAFA;
}
.else{
    background-color: #C2CFD8;
}
`

const Region = styled.div`
    
    //background-color: #94ABBA;
    padding: 10px;
    border-radius: 10px 0 0 10px;
    //border: 1px solid #48494B;
    box-shadow: 4px 6px 6px -2px rgba(17, 20, 24, 0.15);
`

const ResortName = styled.div`
    background-color: #FAFAFA;
    border-radius: 0 10px 10px 0;
    padding: 10px;
    //border: 1px solid #48494B;
    box-shadow: 4px 6px 6px -2px rgba(17, 20, 24, 0.15);
    color: gray;
`

const MySkiWrap = styled.div`
    display: block;
    text-align: center;

    .home-resort-name {
        font-size: 14px;
        padding-top: 7px;
    }
`
const MySki = styled.button`
background-color: var(--button-color);
box-shadow: 0 0 1px 1px rgba(17, 20, 24, 0.1);
border-radius: 45px;
border: none;
margin-right: 7px;
margin-left: 7px;
width: 90px;
height: 90px;
text-align: center;
font-size: 14px;
line-height: 100px;
font-weight: bold;

.home-ski-icon {
    width: 25px;
    height: 25px;
    color: #FAFAFA;
}
`