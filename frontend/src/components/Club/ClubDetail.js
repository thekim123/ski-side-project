import React, { forwardRef, useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { asyncGetClub, getSingleClub } from '../../action/club';
import styled from 'styled-components';
import { HiPlus } from 'react-icons/hi';
import { BsCircle } from 'react-icons/bs';
import { AiFillRightCircle } from 'react-icons/ai';
import { IoMdAddCircle } from 'react-icons/io';
import resorts from '../../data/resort.json';
import { BsPeopleFill } from 'react-icons/bs'
import { loadPosts } from '../../action/clubBoard';

export function ClubDetail() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    //const club = useLocation().state;
    const club = useSelector(state => state.club.club);
    const clubBoards = useSelector(state => state.clubBoard.clubBoards);
    const {id} = useParams();

    const cutText = (text, margin) => {
        if (text.length > margin) {
            return text.slice(0, margin-1) + "...";
        } else return text;
    }

    const clickNoticePlus = e => {
        navigate(`/club/board/write/${true}`, { state: club });
    }
    const clickPlus = e => {
        navigate(`/club/board/write/${false}`, { state: club });
    }

    useEffect(() => { //새로 로그인 한 후 라든지.. 그럴때를 대비해 state.club이 null인 경우에만 dispatch 호출.
        dispatch(loadPosts(id));
        dispatch(getSingleClub(id));
    }, [dispatch, id]);

    return (
    <>
    {/* {club &&  */}
    <Container>
        <ClubName onClick={cutText}>
            {club && club.clubNm}
        </ClubName>
        <ClubResort>
            {club && resorts.find(resort => resort.id === club.resortId).name}
        </ClubResort>
        <InfoBox>
            <CntBox>
                <SBsPeopleFill />
                <Cnt>{club && club.memberCnt}명</Cnt>
            </CntBox>

        </InfoBox>
        <TopLine> </TopLine>

        <NoticeBox>
            <NoticeTop>
                <ButtonBox>
                <Notice>공지</Notice>
                {/* 공지는 방장이나 부방장만 보이게. */}
                <SHiPlus className="tayo-plus" onClick={clickNoticePlus}/>
                </ButtonBox>
                <MoreBox>{clubBoards.filter(board => board.sortScope === "notice").length > 2 && <Button>more...</Button>}</MoreBox>
            </NoticeTop>
            
            {clubBoards.length > 0 && clubBoards.filter(board => board.sortScope === "notice").map((board, i) => (
                i < 2 && <NoticeItem key={board.id}>
                    <NoticeItemWrap>
                        <TitleWho>
                        <NoticeContent>{cutText(board.title, 25)}</NoticeContent>
                        <NoticeWho>관리자</NoticeWho>
                        </TitleWho>
                        <NoticeDate>2022.11.02</NoticeDate>
                    </NoticeItemWrap>
                </NoticeItem>
            ))               
            }

            <BoardTop>
                <ButtonBox>
                <Notice>일반</Notice>
                {/* 공지는 방장이나 부방장만 보이게. */}
                <SHiPlus className="tayo-plus" onClick={clickPlus}/>
                </ButtonBox>
                <MoreBox>{clubBoards.filter(board => board.sortScope === "general").length > 2 && <Button>more...</Button>}</MoreBox>
            </BoardTop>

            {clubBoards.length > 0 && clubBoards.filter(board => board.sortScope === "general").map((board, i) => (
                i < 2 && <NoticeItem key={board.id}>
                    <NoticeItemWrap className='club-normal'>
                        <TitleWho>
                        <NoticeContent>{cutText(board.title, 23)}</NoticeContent>
                        <BoardWho>{cutText("스키넘좋아", 4)}</BoardWho>
                        </TitleWho>
                        <NoticeDate>2022.11.02</NoticeDate>
                    </NoticeItemWrap>
                </NoticeItem>
                ))
            }
        </NoticeBox>
    </Container>
    {/* } */}
    </>
    )
}
const BoardTop = styled.div`
margin-top:75px;
display: flex;
justify-content: space-between;
`
const Container = styled.div`
padding-top: 100px;
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
`
const ClubName = styled.div`
font-weight: bold;
font-size: 25px;
`
const ClubResort = styled.div`
padding-top: 8px;
`
const InfoBox = styled.div`
margin-top: 70px;
display: flex;
`
const CntBox = styled.div`
display: flex;
color: gray;
justify-items: center;
align-items: center;
padding: 0 5px;
`
const SBsPeopleFill = styled(BsPeopleFill)`

`
const Cnt = styled.div`
font-size: 13px;
padding-left: 3px;
`
const TopLine = styled.div`
border-bottom: 1px solid #CCCCCC;
width: 90%;
font-size: 14px;
padding: 5px;
`
const NoticeBox = styled.div`
width: 90%;
margin-top: 20px;
margin-bottom: 40px;
`
const NoticeTop = styled.div`
display: flex;
justify-content: space-between;
`
const ButtonBox = styled.div`
display: flex;
`
const Notice = styled.div`
font-weight: bold;
padding: 10px;
`
const MoreBox = styled.div`
display:grid;
justify-items: end;
align-items: end;
`
const Button = styled.button`
padding: 0px 11px;
border-radius: 7px;
border: none;
color: gray;
background-color: var(--background-color);
height: 20px;
font-size: 17px;
`
const SHiPlus = styled(HiPlus)`
width: 0.9rem;
height: 0.9rem;
background-color: #6B89A5;
background-color: var(--button-color);
color: #FAFAFA;
padding: 7px;
border-radius: 13px;
align-self: center;
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
const TitleWho = styled.div`
display: flex;
justify-content: space-between;
align-items: center;
`
const NoticeContent = styled.div`
font-size: 15px;
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
const NoticeDate = styled.div`
font-size: 12px;
color: gray;
padding-top: 5px;
`
const BoardWho = styled.div`
font-size: 13px;
color: #7F7F7F;
border-radius: 10px;
padding: 7px;
background-color: #E7E6E6;
width: 50px;
text-align: center;
`