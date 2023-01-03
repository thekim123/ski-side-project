import React from 'react'
import styled from 'styled-components'
import { BsArrowRight } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux';
import { BsFillCheckCircleFill } from 'react-icons/bs'

export function CarPoolListItem(props) {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    //const date = new Date(props.departTime);

    return (
    <Wrapper>
        <Top>
            <div><TakeCnt>인원 {props.curPassenger}/{props.passenger}</TakeCnt><Tag>{props.smoker ? "흡연" : "금연"} 차량</Tag></div>
            {/* <CarCnt>운행건수 (2회)</CarCnt> */}
        </Top>

        <Middle onClick={() => props.func(props.id)}> 
            {/* <MiddleTag><TalkTag className="carPoolItem-middleTag">협의 가능</TalkTag><div></div><div></div></MiddleTag> */}
            <Place>
                <StartBox>
                    {props.negotiate.departure && <SBsFillCheckCircleFill />}
                    <Start>{props.departure}</Start>
                </StartBox>
                <SBsArrowRight />
                <EndBox>
                    {props.negotiate.destination && <SBsFillCheckCircleFill />}
                    <End>{props.destination}</End>
                </EndBox>
            </Place>
            <TimeWrap>
                <TimeBox>
                    {props.negotiate.departTime && <SBsFillCheckCircleFill />}
                    <Time>
                        {/* {date.getMonth()+1}.{date.getDate()} {date.getHours()}:{date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes()}  */}
                        {props.departTime[1]}.{props.departTime[2]} {props.departTime[3]}:{props.departTime[4]} <TimeText>출발</TimeText>
                    </Time>
                </TimeBox>
            </TimeWrap>
        </Middle>

        <Bottom>
            <div>
            {props.negotiate.boardingPlace && <SBsFillCheckCircleFill className='bottom-icon'/>}
            <Boarding>탑승 장소: {props.boarding}</Boarding>
            </div>
        </Bottom>
    </Wrapper>
    )
}

const Wrapper = styled.div`
background-color: #FAFAFA;
border-radius: 10px;
margin-bottom: 19px;
//padding: 10px;
padding-bottom: 0px;
//padding-top: 7px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
color: gray;
//padding-bottom: 7px;
`

// Top (인원, 운행 건수)
const Top = styled.div`
display:flex;
justify-content: space-between;
font-size: 12px;
padding: 10px;
padding-bottom: 3px;
div{
    display: flex;
}
`
const TakeCnt = styled.div`
align-self: center;
`
const CarCnt = styled.div`
align-self: center;
`

// Middle (출발지, 도착지)
const Middle = styled.div`
padding: 0 10px;
`
const MiddleTag = styled.div`
display:grid;
grid-template-columns: 1fr 50px 1fr;
.carPoolItem-middleTag {
    justify-self: end;
}
`
const TalkTag = styled.div`
//background-color: #C00000;
background-color: #005C00;
font-size: 11px;
padding: 4px;
border-radius: 3px;
margin-bottom: 2px;
color: #FAFAFA;
`
const Place = styled.div`
display:grid;
grid-template-columns: 1fr 50px 1fr;
//justify-items: center;
color: black;
font-weight: 900;
font-size: 18px;
padding: 3px;
`
const StartBox = styled.div`
justify-self: end;
display:flex;
align-self: center;
`
const SBsFillCheckCircleFill = styled(BsFillCheckCircleFill)`
align-self: center;
margin-right: 3px;
width: 14px;
height: 14px;
color: #005C00;
`
const Start = styled.span`
//justify-self: end;
font-family: nanum-square-bold;
`
const SBsArrowRight = styled(BsArrowRight)`
justify-self: center;
padding-top: 3px;
`
const EndBox = styled.div`
display: flex;
`
const End = styled.div`
justify-self: start;
align-self: center;
font-family: nanum-square-bold;
`
// 출발 시간
const TimeWrap = styled.div`
display:grid;
justify-content: center;
color: black;
padding-top: 3px;
padding-bottom: 8px;
`
const Boarding = styled.span`
//font-family: nanum-square-bold;
font-size: 13px;
text-align: center;
color: #646464;
width: 100%;

`
const TimeBox = styled.div`
display: flex;
`
const Time = styled.div`
//background-color: #C0C0C0;
padding: 5px 0;
border-radius: 8px;
font-weight: bold;
color: var(--button-color);
text-align: center;
`
const TimeText = styled.span`
font-size: 13px;
font-weight: 200;
`

// Bottom (도착 시간)
const Bottom = styled.div`
display: grid;
justify-items: center;
background-color: var(--button-sub-color);
border-radius: 0px 0px 10px 10px;
padding: 7px 10px 7px 10px;
div{
    display: flex;
}
.bottom-icon {
    align-self: center;
}
`
const Tag = styled.div`
background-color: #A7A7A7;
font-size: 11px;
padding: 4px 6px;
margin-left: 5px;
border-radius: 3px;
margin-bottom: 2px;
color: #FAFAFA;
`