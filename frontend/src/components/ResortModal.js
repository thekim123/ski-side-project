import axios from 'axios';
import React, {useEffect, useState} from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { AiOutlineStar, AiFillStar } from 'react-icons/ai'
import { addMyResort, deleteMyResort } from '../action/resort'
import { addBookmark, deleteBookmark, loadBookmarks } from '../action/bookmark';
import styled from 'styled-components'
import shortid from 'shortid';
import { Loading } from './common/Loading';
import resorts  from '../data/resort.json';
import { BsBoxArrowUpRight } from 'react-icons/bs';
import Slider from 'react-slick'

export function ResortModal(props) {
    const dispatch = useDispatch();
    const [emptyStar, setEmptyStar] = useState(!props.like);
    const resortData = resorts.filter(resort => resort.id !== null);
    const resortInfo = resortData.find(resort => resort.id === props.resortId);
    const t_date = new Date();
    let week = ['일', '월', '화', '수', '목', '금', '토', '일', '월', '화', '수', '목', '금', '토'];

    const settings = {
        className: "center",
        infinite: false,
        centerPadding: "6px",
        slidesToShow: 5,
        swipeToSlide: true,
        afterChange: function(index) {
            console.log(
            `Slider Changed to: ${index + 1}, background: #222; color: #bada55`
            );
        }
    }
    
    const clickOutside = (e) => {
        if (e.target.className === "openModal skiModal") {
            dispatch(loadBookmarks());
            props.close();
        }
    }
    const toggleStar = (e) => {
        if (emptyStar) {
            dispatch(addBookmark(props.resortId));
        } else {
            dispatch(deleteBookmark(props.resortId));
        }
        //dispatch(loadMyResorts)
        setEmptyStar(!emptyStar);
    }

    return (
    <Wrapper>
        <div className={props.open ? "openModal skiModal" : "skiModal"} onClick={clickOutside}>
        {props.open ? (
            <Section>
                {!props.dayState.icon ? <Loading /> :
                <>
                <Header>
                    <div onClick={toggleStar}>{emptyStar ? <AiOutlineStar className="modal-star"/> : <AiFillStar className="modal-star"/>}</div>
                    <div className="modal-resortName">{props.header}</div>
                    <Button onClick={props.close}>&times;</Button>
                </Header>

                    <TempWeather>
                        <TempImg src={props.dayState.icon} />
                        {/* <TempDate>{props.dayState.date} ({props.dayState.day})</TempDate> */}
                        <TempDate>{t_date.getMonth() === 12 ? 1 : t_date.getMonth() + 1}.{t_date.getDate()+" ("+week[t_date.getDay()]+")"}</TempDate>
                        <CBox><TempC>{props.dayState.tempCelcius}&deg;</TempC>
                        {/* <TempDesc>{props.dayState.currentWeather}</TempDesc> */}
                        </CBox>
                        <MinMaxBox><MaxC className='temp-max'>{props.dayState.tempMax}&deg;</MaxC>/<MinC>{props.dayState.tempMin}&deg;</MinC></MinMaxBox>
                </TempWeather>

                <Slider {...settings} className="hour-slider">
                {props.dayState.hourly_info.slice(0,8).map(w => (
                        <EachDay>
                            {w.dt.slice(11, 13) === "00" ? <EachDate>{w.dt.slice(5,7)}.{w.dt.slice(8,10)}</EachDate> : <EachDate></EachDate>}
                            <EachTime>{w.dt.slice(11, 13)+"시"}</EachTime>
                            <Img src={w.icon} />
                            <EachTemp>{w.temp_c}&deg;</EachTemp>
                        </EachDay>
                    ))
                    }
                </Slider>

                <ResortInfo>
                
                        <Row><Label className='label-time'>운영 시간</Label><Box>{resortInfo.time.split('\n').map(s => <div className='box-inside'>{s}</div>)}</Box></Row>
                        <Row><Label>운영 기간</Label><>연중무휴</></Row>
                        <Row><Label>이용 요금</Label>
                            <OutButton 
                                onClick={() => window.open(resortInfo.url, '_blank')}>
                                    보러가기
                                <SBsBoxArrowUpRight />
                            </OutButton>
                        </Row>
                        
                </ResortInfo>
                </>
            }
            </Section>
        ): null}
        </div>
    </Wrapper>
    )
}
const Box = styled.div`
display: grid;
.box-inside{
    padding-bottom: 3px;
}
`
const OutButton = styled.button`
border: none;
color: #FAFAFA;
background-color: var(--button-color);
padding: 6px 8px;
border-radius: 4px;
`
const SBsBoxArrowUpRight = styled(BsBoxArrowUpRight)`
width: 11px;
height: 11px;
padding-left: 4px;
`
const Wrapper = styled.div`
    .skiModal {
        display: none;
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 99;
        background-color: rgba(0, 0, 0, 0.6);
    }

    .openModal {
        display: flex;
        align-items: center;
        animation: modal-bg-shadow 0.3s;
    }
`

const Section = styled.div`
    width: 90%;
    margin: 0 auto;
    border-radius: 0.3rem;
    background-color: var(--background-color);
    //animation: modal-show 0.3s;
    overflow: hidden;

    .hour-slider{
        margin: 30px;
        margin-top: 10px;
        border-top: 1px solid var(--button-sub-color);
        border-bottom: 1px solid var(--button-sub-color);
        padding: 20px 0;
    }
    .slick-prev:before{
        color: var(--button-color);
    }
    .slick-next:before{
        color: var(--button-color);
    }
`

const Header = styled.div`
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 5px;
    .modal-star {
        width: 1.8rem;
        height: 1.8rem;
        padding-left: 9px;
        color: var(--button-color);
    }
    .modal-resortName {
        font-family: nanum-square-bold;
        font-size: 15px;
    }
`

const Button = styled.button`
    outline: none;
    cursor: pointer;
    border: 0;
    background-color: transparent;
    font-size: 30px;
`

const TempWeather = styled.div`
    align-items: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 10px;
    margin: 10px;
    margin-bottom: 0;
    padding-bottom: 20px;

    .temp-max{
        margin-right: 5px;
    }
`

const TempImg = styled.img`
    width: 90px;
    height: 90px;
`
const TempDate = styled.div`
    font-family: nanum-square-bold;
    font-size: 14px;
    padding-bottom: 10px;
`
const CBox = styled.div`
padding-bottom: 10px;
display: grid;
justify-items: center;
`
const TempDesc = styled.div`
    font-size: 14px;
    margin-right: 4px;
`

const TempC = styled.div`
    font-family: nanum-square-bold;
    font-size: 22px;
`

const MaxC = styled.span`
    font-size: 12px;
    font-family: nanum-square-bold;
    color: #B73E3E;
`
const MinC = styled.span`
    font-size: 12px;
    font-family: nanum-square-bold;
    color: #628E90;
    padding-left: 5px;
`
const MinMaxBox = styled.div`
color: gray;
`

// 주간 날씨
const WeekWeather = styled.div`
    display: flex;
    //justify-content: space-between;
    border-top: 1px solid var(--button-color);
    border-bottom: 1px solid var(--button-color);
    margin: 10px 10px;
`
const EachDay = styled.div`
    display: grid;
    //flex-direction: column;
    justify-items: center;
    align-items: center;
    padding-left: 5px;
`

const ResortInfo = styled.div`
    //border-top: 1px solid var(--button-color);
    margin: 20px;
    //padding-top: 20px;
`
const Row = styled.div`
    display: flex;
    font-size: 12px;
    padding-bottom: 4px;
    align-items: center;
    .label-time{
        align-self: start;
    }
`
const Label = styled.div`
    margin-right: 9px;
    font-family: nanum-square-bold;
`
const Url = styled.div`
    font-size: 9px;
`

const EachDate = styled.div`
font-size: 11px;
height: 16px;
color: var(--button-sub-color);
padding-left: 3px;
`
const EachTime = styled.div`
font-size: 13px;
color: var(--button-color);
padding-left: 5px;
`
const Img = styled.img`
width: 42px;
height: 42px;
margin-right: 0;
`
const EachTemp = styled.div`
font-family: nanum-square-bold;
font-size: 13px;
padding-left: 5px;
`