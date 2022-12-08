import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import styled from 'styled-components'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import { MdSnowboarding } from 'react-icons/md'
import { GiHills } from 'react-icons/gi'
import { BsPeopleFill, BsCalendarCheck } from 'react-icons/bs'
import { MdEmojiPeople } from 'react-icons/md'
import { getSinglePost } from '../../action/tayo'
import { AiOutlineClockCircle } from 'react-icons/ai'

export function TayoDetail() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isMine, setIsMine] = useState(true);
    let {id} = useParams();

    const handlePencil = e => {
        //navigate(`/tayo/edit/${id}`);
    }

    const handleTrash = e => {
        //navigate("/board")
        //dispatch(deletePost(id));
    }

    useEffect(() => {
        //dispatch(getSinglePost(id));
    }, [dispatch])
    return (
    <Wrapper>
        <Top>
            <Resort>[엘리시안]</Resort>
            <Title>겨울방학도 했는데 스키장은 가야지!</Title>
            <NameDate>
                <div className="boardDetail-nd">
                    <Name>스키짱</Name>
                    <DateForm>2022-11-14 18:15</DateForm>
                </div>
                <Icon>
                    {isMine && <HiPencil className="boardDetail-icon" onClick={handlePencil}/>}
                    {isMine && <BsTrashFill className="boardDetail-icon" onClick={handleTrash}/>}
                </Icon>
            </NameDate>
        </Top>

        <Middle>
        <Content>
        <Info>
            <InfoBox><MdSnowboarding className="icon"/><div>보드</div></InfoBox>            
            <InfoBox><BsPeopleFill className="icon"/><div>인원제한없음</div></InfoBox>
            <InfoBox><MdEmojiPeople className='tayoDetail-ageIcon'/><div>20대</div></InfoBox>
        </Info>
            같이 스키타러 가요!<br />실력 무관!<br />술 좋아하시는 분 우대!<br />
        </Content>

        
        
        </Middle>
        <Bottom>
        <DateTime>
                <DateBox>
                    <BsCalendarCheck className='date-icon'/>
                    <Date>2022-11-17 (목)</Date>
                </DateBox>
                <DateBox>
                    <AiOutlineClockCircle className='date-icon'/>
                    <Date>08:00 ~ 19:00</Date>
                </DateBox>
        </DateTime>
        <Button>신청하기</Button>
        </Bottom>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin-top: 20px;
`
// Top
const Resort = styled.div`
padding-bottom: 8px;
font-size: 14px;
`
const Empty = styled.div`
width: 35px;
`

// Title
const Top = styled.div`
//background-color: #57748F;
padding: 10px;
//border-bottom: 1px solid var(--button-color);
margin: 10px;
margin-bottom: 0;
`
const Title = styled.div`
font-family: nanum-square-bold;
font-size: 19px;
padding-bottom: 7px;
//color: #E8E8E8;
color: black;
`
const NameDate = styled.div`
display: flex;
justify-content: space-between;
font-size: 13px;
color: gray;
.boardDetail-nd{
    display:flex;
}
`
const Name = styled.div`
padding-right: 20px;
`
const DateForm = styled.div`

`
const Icon = styled.div`
    .boardDetail-icon {
        width: 1.4rem;
        height: 1.4rem;
        color: var(--button-color);
        padding-left: 8px;
    }
`

//Info
const Info = styled.div`
display: flex;
`
const InfoBox = styled.div`
display: flex;
background-color: #C2CFD8;
border-radius: 10px;
padding: 4px 7px;
margin-bottom: 60px;
margin-top: 7px;
margin-right: 7px;
font-size: 13px;
color: #585858;
.icon{
    padding-right: 3px;
    width: 1rem;
    height: 1rem;
}
.tayoDetail-ageIcon{
    //padding-right: 3px;
    width: 1.1rem;
    height: 1.1rem;
}
div{
    align-self: center;
}
`
const Age = styled.span`
`

// Content
const Content = styled.div`
padding: 20px;
font-size: 15px;
font-weight: 200;
line-height: 20px;
padding-bottom: 500px;
background-color: #F6F6F4;
`

// Date & Time
const DateTime = styled.div`
margin-bottom: 15px;
`
const DateBox = styled.div`
display: flex;
padding-bottom: 5px;
padding-left: 5px;
.date-icon {
    width: 20px;
    height: 20px;
    padding-right: 5px;
    //background-color: var(--button-color);
    //border-radius: 50%;
    color: var(--button-color);
}
`
const Date = styled.div`
align-self: center;
font-family: nanum-square-bold;
color: var(--button-color);
`
const Middle = styled.div`
display: grid;
//margin: 15px;
`

// 신청하기 button
const Bottom = styled.div`
position: fixed;
bottom: 0;
left: 0;
right: 0;
padding: 10px;
padding-bottom: 95px;
background-color: var(--background-color);
padding-top: 15px;
`
const Button = styled.button`
border-radius: 10px;
padding: 13px 20px;
color: #FAFAFA;
background-color: var(--button-color);
border: none;
width: 100%;
`