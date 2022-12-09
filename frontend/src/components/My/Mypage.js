import React, { useState } from 'react'
import styled from 'styled-components'
import { BsPersonCircle } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom';
import { IoIosArrowForward } from 'react-icons/io';

export default function Mypage() {
    const navigate = useNavigate();
    const [myPostPage, setMyPostPage] = useState("자유게시판");

    const gotoMyDetail = () => {
        navigate('/my/detail');
    }

    return (
    <Wrapper>
        <PageName>마이페이지</PageName>
        <ProfileWrapper>
            <SIoPersonCircle />
            <InfoWrapper>
                <Nickname></Nickname>
            </InfoWrapper>
        </ProfileWrapper>

        <SubmitWrapper onClick={gotoMyDetail}>
            <SubmitTitle>내가 쓴 글</SubmitTitle>
            <IoIosArrowForward />
        </SubmitWrapper>

        <SubmitWrapper>
            <SubmitTitle>신청 내역</SubmitTitle>
            <IoIosArrowForward />
        </SubmitWrapper>

        <SubmitWrapper>
            <SubmitTitle>받은 신청</SubmitTitle>
            <IoIosArrowForward />
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
margin-bottom: 15px;
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
padding: 20px 10px;
margin: 0 10px;
border-bottom: 1px solid var(--button-sub-color);
display: flex;
justify-content: space-between;
`
const SubmitTitle = styled.div`
font-family: nanum-square-bold;
`