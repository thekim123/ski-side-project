import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { getSingleClub } from '../../action/club';
import { loadClubPosts } from '../../action/clubBoard';

export default function ClubBoardList() {
    const dispatch = useDispatch();
    const club = useSelector(state => state.club.club);
    const clubBoard = useSelector(state => state.clubBoard.clubBoards);
    const [filteredBoard, setFilteredBoard] = useState(clubBoard);
    const type = useLocation().state;
    const [boardType, setBoardType] = useState(type);
    const {id} = useParams();

    const changeType = e => {
        const t = e.target.innerText;
        setBoardType(t);
        const boards = [...clubBoard];
        if (t === '전체') {
            setFilteredBoard(boards);
        } else if (t === '공지') {
            setFilteredBoard(boards.filter(board => board.sortScope === 'notice'));
        } else if (t === '일반') {
            setFilteredBoard(boards.filter(board => board.sortScope === 'general'));
        }
    }
    const cutText = (text, margin) => {
        if (text.length > margin) {
            return text.slice(0, margin-1) + "...";
        } else return text;
    }

    useEffect(() => {
        dispatch(getSingleClub(id));
        dispatch(loadClubPosts(id));
    }, [])

    useEffect(() => {
        if (clubBoard) {
            const boards = [...clubBoard];
        if (type === '공지') {
            setFilteredBoard(boards.filter(board => board.sortScope === 'notice'));
        } else if (type === '일반') {
            setFilteredBoard(boards.filter(board => board.sortScope === 'general'));
        }
    }
    }, [clubBoard])
    return (
    <Wrapper>
        {club && clubBoard &&
        <>
        <Title>{club.clubNm} <span>게시판</span></Title>
        <Type>
            <div>
                <span className={boardType === '전체' ? 'selected' : ''} onClick={changeType}>전체</span>
                <span className={boardType === '공지' ? 'selected' : ''} onClick={changeType}>공지</span>
                <span className={boardType === '일반' ? 'selected' : ''} onClick={changeType}>일반</span>
            </div>
            <div></div>
        </Type>
        <BoardList>
            {filteredBoard.map(board => (
                <NoticeItem key={board.id}>
                    <NoticeItemWrap className={board.sortScope === 'general' ? 'club-normal' : ''}>
                        <TitleWho>
                        <NoticeContent>{cutText(board.title, 25)}</NoticeContent>
                        <NoticeWho className={board.sortScope === 'general' ? 'club-normal-who' : ''}>관리자</NoticeWho>
                        </TitleWho>
                        <NoticeDate>2022.11.02</NoticeDate>
                    </NoticeItemWrap>
                </NoticeItem>
            ))

            }
        </BoardList>
        </>
        }
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin-top: 30px;
padding: 0 20px;
`
const Title = styled.div`
font-family: nanum-square-bold;
font-size: 18px;
span{
    font-family: nanum-square;
    padding-left: 3px;
    font-size: 16px;
}
`
const Type = styled.div`
display: flex;
justify-content: space-between;
margin-top: 25px;
padding-bottom: 10px;
border-bottom: 1px solid var(--button-color);
color: var(--button-sub-color);
span{
    margin-right: 14px;
}
.selected{
    color: var(--button-color);
    font-family: nanum-square-bold;
}
`
const BoardList = styled.div`

`

const NoticeItemWrap = styled.div`
//border: 1px solid #CCCCCC;
padding: 18px 15px 8px 15px;
display: grid;
border-radius: 5px;
box-shadow: 5px 3px 7px -2px rgba(17, 20, 24, 0.18);
background-color: var(--button-sub-color);
`
const NoticeItem = styled.div`
//width: 90%;
margin-top: 10px;
.club-normal{
    background-color: #FAFAFA;
}
`
const NoticeWho = styled.div`
font-size: 13px;
background-color: #FAFAFA;
border-radius: 10px;
padding: 5px;
color: gray;
width: 37px;
text-align: center;
`
const TitleWho = styled.div`
display: flex;
justify-content: space-between;
align-items: center;
.club-normal-who{
    background-color: #E7E6E6;
}
`
const NoticeContent = styled.div`
font-size: 15px;
`
const NoticeDate = styled.div`
font-size: 12px;
color: gray;
padding-top: 5px;
`