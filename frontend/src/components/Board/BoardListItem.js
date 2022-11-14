import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill, BsFilePost } from 'react-icons/bs'
import { AiOutlineLike } from 'react-icons/ai'
import { deletePost } from '../../action/board'

function BoardListItem(props) {
    const user = useSelector(state => state.auth.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [isMine, setIsMine] = useState(false);

    const [timePass, setTimePass] = useState("");

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
        if (user === props.user.username) setIsMine(true);
    }

    const handlePencil = e => {
        navigate(`/board/edit/${props.id}`);
    }

    const handleTrash = e => {
        dispatch(deletePost(props.id));
    }

    const showDetail = e => {
        //console.log("show");
        navigate(`/board/detail/${props.id}`);
    }

    useEffect(() => {
        setTimePass(detailDate(props.createDate));
        showIcon();
        console.log("item", props);
    }, []);

    return (
        <PostContainer>
            <Top>
                <SkiName>[{props.resort.resortName}]</SkiName>
                <div>
                    {isMine && <HiPencil className="boardPost-icon" onClick={handlePencil}/>}
                    {isMine && <BsTrashFill className="boardPost-icon" onClick={handleTrash}/>}
                </div>
            </Top>
            <Content onClick={showDetail}>
                <Img>{/*<BsFilePost className="boardPost-imgIcon" />*/}</Img>
                <div>
                    <Title>{props.title}</Title>
                    <Detail>{props.content}</Detail>
                    <Bottom>
                        <Empty></Empty>
                        <Real>
                            <AiOutlineLike className="boardPost-likeIcon"/>
                            <LikeCnt>{props.likeCount}</LikeCnt>
                            <div className="boardPost-bottomText">{props.user.nickname}</div>
                            <div className="boardPost-bottomText">{timePass}</div>
                        </Real>
                    </Bottom>
                </div>
            </Content>
            
        </PostContainer>
    )
}

const PostContainer = styled.div`
    background-color: #FAFAFA;
    //border: 0.1rem solid #CCCCCC;
    border-radius: 10px;
    margin: 10px 20px;
    box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    color: gray;
    padding-bottom: 7px;
`

const Top = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 8px;

    .boardPost-icon{
        width: 1.2rem;
        height: 1.2rem;
        color: #6B89A5;
        padding-right: 3px;
    }
`

const SkiName = styled.div`
    font-size: 0.8rem;
`

const Content = styled.div`
    display: grid;
    grid-template-columns: 80px 1fr;
    margin-left: 10px;

    .boardPost-imgIcon {
        width:80%;
        height: 80%;
    }
`

const Img = styled.div`
    background-color: #C2CFD8;
    width: 70px;
    height: 70px;
    align-items: center;
`

const Title = styled.div`
    margin-left: 10px;
    font-weight: bold;
    color: black;
`

const Detail = styled.div`
    font-size: 0.9rem;
    margin-left: 10px;
    margin-top: 4px;
`

const Bottom = styled.div`
    display: flex;
    justify-content: space-between;
    padding-top: 10px;

    .boardPost-likeIcon {
        color: gray;
        width: 1.1rem;
        height: 1.1rem;
        padding-top: 2px;
    }
    .boardPost-bottomText {
        font-size: 0.7rem;
        padding: 5px 7px;
    }
`
const Empty = styled.div`
    width:5px;
    height:5px;
`
const Real = styled.div`
    display:flex;
    margin-right: 5px;
    margin-bottom: 4px;
`
const LikeCnt = styled.div`
    padding: 5px 7px 5px 2px;
    font-size: 0.7rem;
`

export default BoardListItem
