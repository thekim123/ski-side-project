import React from 'react'
import styled from 'styled-components'
import { BsArrowRight } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux';
import { BsFillCheckCircleFill } from 'react-icons/bs'

export function CarPoolListItem(props) {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const showDetail = e => {
        //dispatch()
        //navigate(`/carpool/detail/${props.id}`)
        navigate('/carpool/detail');
    }
    return (
    <Wrapper>
        <Top>
            <div><TakeCnt>인원 0/{props.passenger}</TakeCnt><Tag>흡연 차량</Tag></div>
            <CarCnt>운행건수 (2회)</CarCnt>
        </Top>

        <Middle onClick={showDetail}> 
            {/* <MiddleTag><TalkTag className="carPoolItem-middleTag">협의 가능</TalkTag><div></div><div></div></MiddleTag> */}
            <Place>
                <StartBox>
                    <SBsFillCheckCircleFill />
                    <Start>{props.departure}</Start>
                </StartBox>
                <SBsArrowRight />
                <End>{props.destination}</End>
            </Place>
            <TimeWrap>
                <TimeBox>
                    <SBsFillCheckCircleFill />
                    <Time>12/1 07:00 <TimeText>출발</TimeText></Time>
                </TimeBox>
            </TimeWrap>
        </Middle>

        <Bottom>
            {/* <Tag>{props.smoke}</Tag> */}
            {/* <Tag>여유공간 많아요</Tag> */}
        </Bottom>
    </Wrapper>
    )
}
const TimeBox = styled.div`
display: flex;
`
const Wrapper = styled.div`
background-color: #FAFAFA;
border-radius: 10px;
margin-bottom: 10px;
padding: 10px;
padding-bottom: 6px;
padding-top: 7px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
color: gray;
//padding-bottom: 7px;
`

// Top (인원, 운행 건수)
const Top = styled.div`
display:flex;
justify-content: space-between;
font-size: 12px;
padding-bottom: 8px;
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
`
const SBsArrowRight = styled(BsArrowRight)`
justify-self: center;
padding-top: 3px;
`
const End = styled.div`
justify-self: start;
align-self: center;
`
// 출발 시간
const TimeWrap = styled.div`
display:grid;
justify-content: center;
color: black;
padding-top: 6px;
padding-bottom: 8px;
`
const Time = styled.div`
//background-color: #C0C0C0;
padding: 5px 0;
border-radius: 8px;
font-weight: bold;
`
const TimeText = styled.span`
font-size: 13px;
font-weight: 200;
`

// Bottom (도착 시간)
const Bottom = styled.div`
display: flex;
`
const Tag = styled.div`
background-color: var(--button-sub-color);
font-size: 11px;
padding: 4px 6px;
margin-left: 5px;
border-radius: 3px;
margin-bottom: 2px;
color: #FAFAFA;
`