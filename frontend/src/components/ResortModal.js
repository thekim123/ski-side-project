import axios from 'axios';
import React, {useState} from 'react'
import styled from 'styled-components'

export function ResortModal(props) {
    // const days = ['일', '월', '화', '수', '목', '금', '토', '일', '월', '화', '수', '목', '금', '토'];
    // const idx = days.indexOf(props.dayState.day);
    // const days_5 = {
    //     "tt": days.slice(idx, idx+5),
    //     "minmax": props.dayState.week,
    // }

    const clickOutside = (e) => {
        if (e.target.className === "openModal skiModal") {
            props.close();
        }
    }
    return (
    <Wrapper>
        <div className={props.open ? "openModal skiModal" : "skiModal"} onClick={clickOutside}>
        {props.open ? (
            <Section>
                <Header>
                    <div></div>
                    <div className="modal-resortName">{props.header}</div>
                    <Button onClick={props.close}>&times;</Button>
                </Header>

                
                {/* {isLoading || error */}
                    {/* ? <div>Waiting..</div>  */}
                    {/* :  */}
                    <TempWeather>
                        <TempImg src={props.dayState.icon} />
                        <TempDate>{props.dayState.date} ({props.dayState.day})</TempDate>
                        <div><TempC>{props.dayState.tempCelcius}&deg;</TempC>
                        {/* <TempDesc>{props.dayState.currentWeather}</TempDesc> */}
                        </div>
                        <div><MaxC>최고 {props.dayState.tempMax}&deg;</MaxC><MinC>최저 {props.dayState.tempMin}&deg;</MinC></div>
                </TempWeather>
                {/* } */}

                <WeekWeather>
                    {
                        props.dayState.week.map((elem) => (
                            <EachDay>
                                <TempDate>{elem.day}</TempDate>
                                <MaxC>{elem.max}&deg;</MaxC>
                                <MinC>{elem.min}&deg;</MinC>
                            </EachDay>
                        ))
                    }
                </WeekWeather>

                <ResortInfo>
                
                        <Row><Label>운영 시간</Label><>매일 09:00 ~ 17:00 야간 19:00 ~ 22:00</></Row>
                        <Row><Label>운영 기간</Label><>연중무휴</></Row>
                        <Row><Label>이용 요금</Label><Url>https://www.yongpyong.co.kr/kor/skiNboard/introduce.do</Url></Row>
                        
                </ResortInfo>
            </Section>
        ): null}
        </div>
    </Wrapper>
    )
}

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
    background-color: #fff;
    //animation: modal-show 0.3s;
    overflow: hidden;
`

const Header = styled.div`
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .modal-resortName {
        font-weight: bold;
        font-size: 15px;
        margin-left: 20px;
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
`

const TempImg = styled.img`
    width: 90px;
    height: 90px;
`
const TempDate = styled.div`
    font-weight: bold;
    font-size: 14px;
`
const TempDesc = styled.span`
    font-size: 14px;
`

const TempC = styled.span`
    font-weight: bold;
    font-size: 20px;
`

const MaxC = styled.span`
    font-size: 12px;
    font-weight: bold;
    color: #CD5C5C;
    margin-right: 7px;
`
const MinC = styled.span`
    font-size: 12px;
    font-weight: bold;
    color: #447F96;
    margin-left: 7px;
`

// 주간 날씨
const WeekWeather = styled.div`
    display: flex;
    justify-content: space-between;
    margin: 20px;
    border-top: 1px solid #CCCCCC;
    border-bottom: 1px solid #CCCCCC;
`
const EachDay = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 15px;
`

const ResortInfo = styled.div`
    border-top: 1px solid #CCCCCC;
    margin: 20px;
    padding-top: 20px;
`
const Row = styled.div`
    display: flex;
    font-size: 12px;
    padding-bottom: 4px;

`
const Label = styled.div`
    margin-right: 9px;
    font-weight: bold;
`
const Url = styled.div`
    font-size: 9px;
`