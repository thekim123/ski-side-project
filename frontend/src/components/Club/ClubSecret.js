import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { AiFillLock } from 'react-icons/ai';
import resorts from '../../data/resort.json';
import { BsPeopleFill } from 'react-icons/bs'

export function ClubSecret() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const club = useLocation().state;

    const gotoDetail = e => {
        navigate(`/club/detail/${club.id}`, { state: club });
        //동호회 인원 추가
    }

    return (
    <>
    {club && 
    <Container>
        <NameFlex>
            {club.openYn === "N" && <AiFillLock className="clubSecret-lock" />}
            <ClubName>{club.clubNm}</ClubName>
        </NameFlex>
        <ClubResort>{resorts.find(resort => resort.id === club.resortId).name}</ClubResort>
        <InfoBox>
            <CntBox>
                <SBsPeopleFill />
                <Cnt>{club.memberCnt}명</Cnt>
            </CntBox>

        </InfoBox>
        <ContentBox>
            <ClubContent>{club.memo}</ClubContent>
        </ContentBox>
        {club.openYn === "Y" ? <Button onClick={gotoDetail}>가입하기</Button> : <Button>가입 신청하기</Button>}
    </Container>}
    </>
    )
}

const Container = styled.div`
padding-top: 130px;
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
`
const NameFlex = styled.div`
display: flex;
align-items: center;
.clubSecret-lock {
    width: 1.6rem;
    height: 1.6rem;
    color: gray;
    padding: 2px;
}
`
const ClubName = styled.div`
font-weight: bold;
font-size: 25px;
padding: 0 6px;
`
const ClubResort = styled.div`
padding-top: 8px;
`
const InfoBox = styled.div`
margin-top: 50px;
margin-bottom: 10px;
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
const ContentBox = styled.div`
height: 180px;
width: 80%;
border-top: 1px solid #CCCCCC;
border-bottom: 1px solid #CCCCCC;
margin-bottom: 40px;
`
const ClubContent = styled.div`
padding: 20px;
`
const Button = styled.button`
background-color:var(--button-color);
color: #FAFAFA;
padding: 13px 20px;
border-radius: 15px;
border: none;
`