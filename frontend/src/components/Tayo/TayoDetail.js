import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import styled from 'styled-components'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import { MdSnowboarding } from 'react-icons/md'
import { GiHills } from 'react-icons/gi'
import { BsPeopleFill } from 'react-icons/bs'
import { MdEmojiPeople } from 'react-icons/md'

export function TayoDetail() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isMine, setIsMine] = useState(true);
    //let {id} = useParams();

    const handlePencil = e => {
        //navigate(`/tayo/edit/${id}`);
    }

    const handleTrash = e => {
        //navigate("/board")
        //dispatch(deletePost(id));
    }
    return (
    <Wrapper>
        <TopInfo>
            <Count>4/5</Count>
            <Resort>[엘리시안]</Resort>
            <Empty></Empty>
        </TopInfo>
        <Top>
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
        <Info>
            <InfoBox><MdSnowboarding className="icon"/>보드</InfoBox>
            <InfoBox><MdEmojiPeople className='tayoDetail-ageIcon'/><Age>20대</Age></InfoBox>
            {/* <InfoItem>
                <Circle><MdSnowboarding /></Circle>
                <ItemText>보드</ItemText>
            </InfoItem>
            <InfoItem>
                <Circle><GiHills /></Circle>
                <ItemText>엘리시안</ItemText>
            </InfoItem>
            <InfoItem>
                <Circle><BsPeopleFill /></Circle>
                <ItemText>4 / 5</ItemText>
            </InfoItem>
            <InfoItem>
                <Circle><MdEmojiPeople className='tayoDetail-ageIcon'/></Circle>
                <ItemText>20대</ItemText>
            </InfoItem> */}
        </Info>

        <Content>
            같이 스키타러 가요!<br />실력 무관!<br />술 좋아하시는 분 우대!<br />
        </Content>

        <DateTime>
                <DateBox>
                    <Label>날짜</Label>
                    <Date>2022-11-17 (목)</Date>
                </DateBox>
                <DateBox>
                    <Label>시간</Label>
                    <Date>08:00 ~ 19:00</Date>
                </DateBox>
        </DateTime>
        <Button>신청하기</Button>
        </Middle>
        <Bottom>
            
        </Bottom>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin-top: 20px;
`
// Top
const TopInfo = styled.div`
display: flex;
justify-content: space-between;
font-weight: 700;
font-size: 14px;
padding: 0 10px;
`
const Count = styled.div`
padding-left: 10px;
`
const Resort = styled.div`

`
const Empty = styled.div`
width: 35px;
`

// Title
const Top = styled.div`
//background-color: #57748F;
padding: 20px;
`
const Title = styled.div`
font-weight: 700;
font-size: 19px;
padding-bottom: 7px;
//color: #E8E8E8;
color: black;
`
const NameDate = styled.div`
display: flex;
justify-content: space-between;
font-size: 13px;
//font-weight: bold;
//color: #FAFAFA;
color: black;
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
        color: #57748F;
        padding-left: 8px;
    }
`

//Info
const Info = styled.div`
display: flex;
margin: 15px;
`
const InfoBox = styled.div`
background-color: #C2CFD8;
border-radius: 15px;
width: 53px;
padding: 7px;
margin-top: 7px;
margin-right: 7px;
font-size: 13px;
.icon{
    padding-right: 3px;
    width: 1rem;
    height: 1rem;
}
.tayoDetail-ageIcon{
    padding-right: 3px;
    width: 1.1rem;
    height: 1.1rem;
}
`
const Age = styled.span`
`

// Content
const Content = styled.div`
padding: 25px 20px;
//background-color: #FAFAFA;
font-size: 15px;
font-weight: 200;
//margin: 20px 15px;
border-radius: 10px;
//box-shadow: 7px 7px 7px -2px rgba(17, 20, 24, 0.15);
text-align: center;
`

// Date & Time
const DateTime = styled.div`
padding: 3px;
margin: 15px;
margin-bottom: 22px;
font-weight: 200;
background-color: #FAFAFA;
border-radius: 10px;
border: 1px solid #CCCCCC;
//box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
width: 70%;
`
const DateBox = styled.div`
display: grid;
grid-template-columns: 50px 1fr;
margin: 5px;
align-items: center;
`
const Label = styled.div`
background-color: #C2CFD8;
height: 30px;
border-radius: 7px;
border: 1px solid #e8e8e8;
padding-left: 9px;
padding-top: 7px;
color: black;
font-size: 14px;
`
const Date = styled.div`
margin-left: 3px;
height: 33px;
border: 1px solid #E8E8E8;
border-radius: 7px;
padding-top: 5px;
text-align: center;
`
const Middle = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
margin: 15px;
//border-radius: 0 0 15px 15px;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
background-color: #FAFAFA;
`

// 신청하기 button
const Bottom = styled.div`
display: flex;
justify-content: center;
`
const Button = styled.button`
border-radius: 10px;
padding: 13px 20px;
color: #FAFAFA;
background-color: #57748F;
border: none;
margin: 100px;
margin-bottom: 15px;
width: 90%;
`