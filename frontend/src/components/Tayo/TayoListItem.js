import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill, BsFilePost } from 'react-icons/bs'
import { AiOutlineLike } from 'react-icons/ai'
import { BsPeopleFill } from 'react-icons/bs'
import { MdEmojiPeople } from 'react-icons/md'
import { deletePost } from '../../action/board'

function TayoListItem(props) { 
    const user = useSelector(state => state.auth.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [isMine, setIsMine] = useState(true);
    const [title, setTitle] = useState("");
    const [timePass, setTimePass] = useState("");
    const ageData = ["ANY", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY"]
    const age_kor = ["연령 무관", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대"]

    const detailDate = (create_dt) => {
        const milliSeconds = new Date() - Date.parse(create_dt);
        const seconds = milliSeconds / 1000;
        if (seconds < 60) return "방금 전";
        const minutes = seconds / 60;
        if (minutes < 60) return `${Math.floor(minutes)}분 전`;
        const hours = minutes / 60;
        if (hours < 24) return `${Math.floor(hours)}시간 전`;
        const days = hours / 24;
        if (days < 7) return `${Math.floor(days)}일 전`;
        const weeks = days / 7;
        if (weeks < 5) return `${Math.floor(weeks)}주 전`;
        const months = days / 30;
		if (months < 12) return `${Math.floor(months)}개월 전`;
		const years = days / 365;
		return `${Math.floor(years)}년 전`;
    }

    const showIcon = () => {
        //if (user === props.user.username) setIsMine(true);
        console.log(props);
    }

    const setScreenTitle = () => {
        if (props.title.length > 18) {
        setTitle(props.title.slice(0, 18)+"...")
        } else {
            setTitle(props.title)
        }
    }

    const handlePencil = e => {
        //navigate(`/board/edit/${props.id}`);
    }

    const handleTrash = e => {
        dispatch(deletePost(props.id));
    }

    const showDetail = e => {
        navigate(`/tayo/detail/${props.id}`);
    }

    useEffect(() => {
        setTimePass(detailDate(props.createdDate));
        showIcon();
        setScreenTitle();
    }, []);

    return (
        <PostContainer>
            <Top>
                <SkiName>{props.resortName}[엘리시안]</SkiName>
                <Time>{timePass}</Time>
                {/*
                <div>
                    {isMine && <HiPencil className="boardPost-icon" onClick={handlePencil}/>}
                    {isMine && <BsTrashFill className="boardPost-icon" onClick={handleTrash}/>}
    </div>*/}
            </Top>
            <Content 
            // onClick={showDetail}
            >
                <Count>
                    <BsPeopleFill className="tayo-count"/>
                    {!props.tayoMemCnt ? "인원 무제한" :  <div>0 / {props.tayoMemCnt}</div>}
                </Count>
                <Age>
                    <MdEmojiPeople className="tayo-age" />
                    {age_kor[ageData.indexOf(props.age)]}
                </Age>
                <TitleTime>
                    <Title>{title}</Title>
                </TitleTime>
            </Content>
        </PostContainer>
    )
}

const PostContainer = styled.div`
    background-color: #FAFAFA;
    //border: 0.1rem solid #CCCCCC;
    border-radius: 10px;
    margin: 10px 20px;
    padding: 3px;
    padding-bottom: 10px;
    color: gray;
    box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
`

const Top = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 8px;

    .boardPost-icon{
        width: 1.2rem;
        height: 1.2rem;
        color: var(--button-color);
        padding-right: 3px;
    }
`

const SkiName = styled.div`
    font-size: 0.8rem;
`

const Content = styled.div`
display: flex;
align-items: center;
padding: 0 10px;
`
const Count = styled.div`
display: grid;
font-size:12px;
text-align: center;
.tayo-count{
    width: 2rem;
    height: 2rem;
    color: var(--button-color);
    padding-left: 10px;
    padding-right: 10px;
}

`
const Age = styled.div`
display: grid;
font-size:12px;
text-align: center;
padding-left: 5px;
.tayo-age{
    width: 3rem;
    height: 1.9rem;
    color: var(--button-color);
    padding-top: 3px;
}
`
const TitleTime = styled.div`

`
const Title = styled.div`
font-weight:bold;
font-size: 15px;
padding-left: 8px;
color: black;
`
const Time = styled.div`
text-align: right;
font-size: 12px;
`

export default TayoListItem