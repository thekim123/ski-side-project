import React from 'react'
import styled from 'styled-components'
import { BsArrowRight } from 'react-icons/bs'

export function CarPoolListItem() {
    return (
    <Wrapper>
        <Top>
            <TakeCnt>인원 0/4</TakeCnt>
            <CarCnt>운행건수 (2회)</CarCnt>
        </Top>

        <Middle>
            <MiddleTag><TalkTag className="carPoolItem-middleTag">협의 가능</TalkTag><div></div><div></div></MiddleTag>
            <Place>
                <Start>서울역</Start>
                <SBsArrowRight />
                <End>엘리시안</End>
            </Place>
            <TimeWrap>
                <Time>12/1 07:00 <TimeText>출발</TimeText></Time>
            </TimeWrap>
        </Middle>

        <Bottom>
            <Tag>흡연 차량</Tag>
            <Tag>여유공간 많아요</Tag>
        </Bottom>
    </Wrapper>
    )
}

const Wrapper = styled.div`
background-color: #FAFAFA;
border-radius: 10px;
margin-bottom: 10px;
padding: 10px;
padding-bottom: 6px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
color: gray;
//padding-bottom: 7px;
`

// Top (인원, 운행 건수)
const Top = styled.div`
display:flex;
justify-content: space-between;
font-size: 12px;
padding-bottom: 3px;
`
const TakeCnt = styled.div`

`
const CarCnt = styled.div`

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
const Start = styled.div`
justify-self: end;
`
const SBsArrowRight = styled(BsArrowRight)`
justify-self: center;
padding-top: 3px;
`
const End = styled.div`
justify-self: start;
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
padding: 5px 8px;
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
margin-right: 5px;
border-radius: 3px;
margin-bottom: 2px;
color: #FAFAFA;
`