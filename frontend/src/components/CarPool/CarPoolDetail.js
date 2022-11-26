import React, { useState } from 'react'
import styled from 'styled-components'
import { CarPoolListItem } from './CarPoolListItem'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill, BsFilePost, BsArrowRight } from 'react-icons/bs'

export function CarPoolDetail() {
    const [isMine, setIsMine] = useState(true);

    const handlePencil = e => {

    }
    const handleTrash = e => {

    }

    return (
    <Wrapper>
        <Top>
            <div>
                <User>스키짱</User>
                <Time>2022-11-22 12:07</Time>
            </div>
            <Icons>
                {isMine && <HiPencil className="boardPost-icon" onClick={handlePencil}/>}
                {isMine && <BsTrashFill className="boardPost-icon" onClick={handleTrash}/>}
            </Icons>
        </Top>

        {/* <CarPoolListItem /> */}
        <Item>
            <ItemTop>
                <TakeCnt>인원 0/4</TakeCnt>
                <CarCnt>운행건수 (2회)</CarCnt>
            </ItemTop>

            <ItemMiddle>
                <Place>
                    <Start>서울역</Start>
                    <SBsArrowRight />
                    <End>엘리시안</End>
                </Place>
                <TimeWrap>
                    <ItemTime>12/1 07:00 <TimeText>출발</TimeText></ItemTime>
                </TimeWrap>
            </ItemMiddle>
        </Item>

        {/* Content */}
        <Middle>
            <Tags>
                <div>
                <TalkTag>출발지 협의 가능</TalkTag>
                <TalkTag>출발 시간 협의 가능</TalkTag>
                </div>
                <TagBox>
                <Tag>흡연 차량</Tag>
                <Tag>여유공간 많아요</Tag>
                </TagBox>
            </Tags>
            <Content>
                휴게소 한번 들러서 10분정도 쉬었다가 갈게요~ <br /> 
                새차 안한지 오래되어서 차가 좀 지저분 할 수 있어요 ㅜㅜ
            </Content>
            <Button>문의 및 신청하기</Button>
        </Middle>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin-top: 10px;
padding: 20px;
`
const Top = styled.div`
display:flex;
justify-content: space-between;
padding-bottom: 10px;
`
const User = styled.span`
margin-right: 10px;
padding-top: 8px;
padding-left: 3px;
font-size: 12px;
`
const Time = styled.span`
padding-top: 8px;
font-size: 12px;
`
const Icons = styled.div`
color: var(--button-color);
.boardPost-icon{
    padding: 3px;
    width: 1.2rem;
    height: 1.2rem;
}
`
// // Item // //
const Item = styled.div`
background-color: #FAFAFA;
border-radius: 10px;
margin-bottom: 10px;
padding: 10px;
padding-bottom: 6px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
color: gray;
`
// Top (인원, 운행 건수)
const ItemTop = styled.div`
display:flex;
justify-content: space-between;
font-size: 12px;
`
const TakeCnt = styled.div`

`
const CarCnt = styled.div`

`

// Middle (출발지, 도착지)
const ItemMiddle = styled.div`

`
const Place = styled.div`
display:grid;
grid-template-columns: 1fr 50px 1fr;
//justify-items: center;
color: black;
font-weight: 900;
font-size: 18px;
padding: 7px 0 5px 0;
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
//padding-top: 4px;
padding-bottom: 6px;
`
const ItemTime = styled.div`
//background-color: #C0C0C0;
padding: 5px 8px;
border-radius: 8px;
font-weight: bold;
`
const TimeText = styled.span`
font-size: 13px;
font-weight: 200;
`

// Middle - content
const Middle = styled.div`
background-color: #FAFAFA;
border-radius: 10px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
margin-top: 10px;
padding: 20px 10px;
`
const Tags = styled.div`
display: inline-block;
padding-bottom: 10px;
`
const TalkTag = styled.span`
background-color: #005C00;
font-size: 12px;
padding: 4px 6px;
margin-right: 5px;
border-radius: 3px;
margin-bottom: 5px;
color: #FAFAFA;
text-align: center;
`
const TagBox = styled.div`
padding-top: 10px;
`
const Tag = styled.span`
background-color: var(--button-sub-color);
font-size: 12px;
padding: 4px 6px;
margin-right: 5px;
border-radius: 3px;
margin-top: 5px;
color: #FAFAFA;
text-align: center;
`
const Content = styled.div`
font-size: 15px;
font-weight: 300;
padding: 30px;
border: 1px solid #CCCCCC;
border-radius: 10px;
margin: 10px 0;
`
const Button = styled.div`
background-color: var(--button-color);
padding: 12px;
margin-top: 20px;
color: #FAFAFA;
border-radius: 10px;
font-size: 14px;
text-align: center;
`