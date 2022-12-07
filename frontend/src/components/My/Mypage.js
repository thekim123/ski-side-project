import React from 'react'
import styled from 'styled-components'
import { BsPersonCircle } from 'react-icons/bs'

export default function Mypage() {
    return (
    <Wrapper>
        <PageName>마이페이지</PageName>
        <ProfileWrapper>
            <SIoPersonCircle />
            <InfoWrapper>
                <Nickname></Nickname>
            </InfoWrapper>
        </ProfileWrapper>

        <SubmitWrapper>
            <SubmitTitle>신청 내역</SubmitTitle>
        </SubmitWrapper>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 30px 0px 50px 0px;
`
const PageName = styled.div`
font-family: nanum-square-bold;
font-size: 19px;
padding: 0 20px;
margin-bottom: 15px;
`
const ProfileWrapper = styled.div`
background-color: var(--button-color);
padding: 30px;
margin-bottom: 30px;
`
const SIoPersonCircle = styled(BsPersonCircle)`
color: #CCCCCC;
width: 70px;
height: 70px;
`
const InfoWrapper = styled.div`
color: #FAFAFA;
`
const Nickname = styled.div`
font-family: nanum-square-bold;

`
const SubmitWrapper = styled.div`
padding: 0 20px;
`
const SubmitTitle = styled.div`
font-family: nanum-square-bold;
`