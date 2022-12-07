import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { CarPoolListItem } from './CarPoolListItem'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill, BsFilePost, BsArrowRight } from 'react-icons/bs'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { getCarpool, submitCarpool } from '../../action/carpool'

export function CarPoolDetail() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isMine, setIsMine] = useState(false);
    //const carpool = useLocation().state;
    const carpool = useSelector(state => state.carpool.carpool);
    const user = useSelector(state => state.auth.user);
    const [date, setDate] = useState(null);
    const {id} = useParams();

    const handlePencil = e => {
        //navigate(`/carpool/edit/${id}`)
        navigate('/carpool/edit/1')
    }
    const handleTrash = e => {
        //console.log(carpool);
    }

    const dummyFunc = (id) => {

    }

    const handleSubmit = e => {
        dispatch(submitCarpool(id));
        //navigate to chat
    }

    useEffect(() => {
        dispatch(getCarpool(id));
    }, [dispatch]);

    useEffect(() => {
        if (carpool) {
            //setDate(new Date(carpool.createDate));
            const t = new Date(carpool.createDate)
            const month = t.getMonth() + 1
            setDate(t.getFullYear() + "."+month + "." + t.getDate()+". "+carpool.createDate.slice(11,16));

            if (user === carpool.user.username) {
                setIsMine(true);
            }
        }
    }, [carpool])

    return (
        <>
    {carpool && <Wrapper>
        <Top>
            <div>
                <User>{carpool.user.nickname}</User>
                <Time>{date}</Time>
            </div>
            <Icons>
                {isMine && <HiPencil className="boardPost-icon" onClick={handlePencil}/>}
                {isMine && <BsTrashFill className="boardPost-icon" onClick={handleTrash}/>}
            </Icons>
        </Top>

        <CarPoolListItem {...carpool} func={dummyFunc}/>

        {/* Content */}
        <Middle>
            <Tags>
                <div>
                {carpool.negotiate.departure && <TalkTag>출발지 협의 가능</TalkTag>}
                {carpool.negotiate.destination && <TalkTag>도착지 협의 가능</TalkTag>}
                </div>
                <div>
                {carpool.negotiate.departTime && <TalkTag>출발 시간 협의 가능</TalkTag>}
                {carpool.negotiate.boardingPlace && <TalkTag>탑승 장소 협의 가능</TalkTag>}
                </div>
                {/*
                <TagBox>
                <Tag>흡연 차량</Tag>
                <Tag>여유공간 많아요</Tag>
    </TagBox>*/}
            </Tags>
            <Content>
                <ContentTitle>추가 사항</ContentTitle>
                {carpool.memo}
            </Content>
            <ButtonBox>
            <SButton>문의하기</SButton>
            <Button onClick={handleSubmit}>신청하기</Button>
            </ButtonBox>

        </Middle>
    </Wrapper>}</>
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

// Middle - content
const Middle = styled.div`
//background-color: #FAFAFA;
//border-radius: 10px;
//box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
margin-top: 10px;
padding: 20px 10px;
`
const Tags = styled.div`
display: inline-block;
padding-bottom: 10px;
div {
    margin-bottom: 13px;
}
`
const TalkTag = styled.span`
background-color: #005C00;
font-size: 12px;
padding: 6px 8px;
margin-right: 5px;
border-radius: 3px;
margin-bottom: 5px;
color: #FAFAFA;
text-align: center;
//width: 104px;
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
padding: 20px 0;
//border: 1px solid #CCCCCC;
//border-radius: 10px;
margin: 10px 0;
color: #646464;
line-height: 20px;
`
const ContentTitle = styled.div`
font-family: nanum-square-bold;
padding-bottom: 10px;
color: black;
`
const ButtonBox = styled.div`
display: grid;
grid-template-columns: 1fr 1fr;
position: fixed;
bottom: 0;
left: 0;
right: 0;
padding-bottom: 85px;
`
const Button = styled.div`
background-color: var(--button-color);
padding: 12px;
margin: 7px;
margin-top: 20px;
color: #FAFAFA;
border-radius: 10px;
font-size: 14px;
text-align: center;
`
const SButton = styled.div`
background-color: #FAFAFA;
border: 1px solid var(--button-color);
padding: 12px;
margin: 7px;
margin-top: 20px;
color: var(--button-color);
border-radius: 10px;
font-size: 14px;
text-align: center;
`