import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { AiFillLock } from 'react-icons/ai';
import { getSingleClub } from '../../action/club';

export function ClubSecret() {
    const dispatch = useDispatch();
    const club = useSelector(state => state.club.club);
    let {id} = useParams();

    useEffect(() => {
        if (id) {
            dispatch(getSingleClub(id));
        }
    }, [dispatch, id]);

    return (
    <>
    {club && 
    <Container>
        <NameFlex>
            <ClubName>{club.club_nm}</ClubName>
            <AiFillLock className="clubSecret-lock" />
        </NameFlex>
        <ClubResort>{club.resort_id}</ClubResort>
        <ContentBox>
            <ClubContent>{club.words}</ClubContent>
        </ContentBox>
        <Button>가입 신청</Button>
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
.clubSecret-lock {
    width: 1.6rem;
    height: 1.6rem;
    padding: 2px 9px;
}
`
const ClubName = styled.div`
font-weight: bold;
font-size: 20px;
padding-left: 7px;
`
const ClubResort = styled.div`
padding-top: 8px;
`
const ContentBox = styled.div`
height: 180px;
width: 80%;
border-top: 1px solid #CCCCCC;
border-bottom: 1px solid #CCCCCC;
margin-top: 50px;
`
const ClubContent = styled.div`
padding: 20px;
`
const Button = styled.button`
padding: 13px 20px;
border-radius: 10px;
border: none;
margin-top: 50px;
`