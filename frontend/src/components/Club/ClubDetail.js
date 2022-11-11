import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import { getSingleClub } from '../../action/club';
import styled from 'styled-components';

import { register } from '../../action/register';

export function ClubDetail() {
    const dispatch = useDispatch();
    const club = useSelector(state => state.club.club);
    let {id} = useParams();

    const testApi = e => {
        dispatch(register());
    }

    useEffect(() => {
        if (id) {
            dispatch(getSingleClub(id));
        }
    }, [dispatch, id]);

    return (
    <>
    {club && 
    <Container>
        <ClubName onClick={testApi}>{club.club_nm}</ClubName>
        <ClubResort>{club.resort_id}</ClubResort>
        <TopLine>게시판</TopLine>
        <NoticeBox>
            <NoticeTop>
                <Notice>공지</Notice><Button>글쓰기</Button>
            </NoticeTop>
            <NoticeItem>
                    <NoticeDate>2022.11.02</NoticeDate>
                    <NoticeItemWrap>
                        <NoticeContent>특별 강습 있습니다 10분 선착순 접수 받습니다</NoticeContent>
                        <NoticeWho>방장</NoticeWho>
                    </NoticeItemWrap>
            </NoticeItem>
            <NoticeItem>
                    <NoticeDate>2022.11.02</NoticeDate>
                    <NoticeItemWrap>
                        <NoticeContent>이번 피셔 힙색 대량 구매 희망자 접수해주세요</NoticeContent>
                        <NoticeWho>방장</NoticeWho>
                    </NoticeItemWrap>
            </NoticeItem>

            <BoardTop>
                <Notice>일반</Notice><Button>글쓰기</Button>
            </BoardTop>
            <NoticeItem>
                    <NoticeDate>2022.11.02</NoticeDate>
                    <BoardItemWrap>
                        <NoticeContent>이번주 토요일 엘리시안 갑니다~</NoticeContent>
                        <NoticeWho>닉네임</NoticeWho>
                    </BoardItemWrap>
            </NoticeItem>
            <NoticeItem>
                    <NoticeDate>2022.11.02</NoticeDate>
                    <BoardItemWrap>
                        <NoticeContent>주말에 모여요~~!</NoticeContent>
                        <NoticeWho>닉네임</NoticeWho>
                    </BoardItemWrap>
            </NoticeItem>
        </NoticeBox>
    </Container>}
    </>
    )
}
const BoardTop = styled.div`
margin-top:40px;
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
const TopLine = styled.div`
border-bottom: 1px solid #CCCCCC;
width: 90%;
font-size: 12px;
margin-top: 20px;
padding: 5px;
`
const NoticeBox = styled.div`
width: 90%;
margin-top: 10px;
`
const NoticeTop = styled.div`
display: flex;
justify-content: space-between;
`
const Notice = styled.div`
font-weight: bold;
padding: 10px;
`
const Button = styled.button`
padding: 2px 13px;
border-radius: 10px;
border: none;
margin-top: 10px;
`
const NoticeItem = styled.div`
//width: 90%;
margin-top: 10px;
`
const NoticeDate = styled.div`
font-size: 12px;
padding-left: 10px;
padding-bottom: 3px;
`
const NoticeItemWrap = styled.div`
border: 1px solid #CCCCCC;
padding: 15px 10px;
display: grid;
grid-template-columns: 10fr 1fr;
`
const NoticeContent = styled.div`
font-size: 13px;
`
const NoticeWho = styled.div`
font-size: 13px;
`
const BoardItemWrap = styled.div`
border: 1px solid #CCCCCC;
padding: 15px 10px;
display: grid;
grid-template-columns: 7fr 1fr;
`