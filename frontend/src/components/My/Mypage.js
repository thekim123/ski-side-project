import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components'
import { BsPersonCircle } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom';
import { IoIosArrowForward } from 'react-icons/io';
import { asyncGetUser } from '../../action/auth';
import { logoutAction } from '../../action/auth';

export default function Mypage() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector(state => state.auth.user);
    const [myPostPage, setMyPostPage] = useState("자유게시판");
    const age = [" ", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대"]
    const dataAge = ["ANY", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY"]
    const gender = ["남자", "여자", ""]
    const dataGender = ["MEN", "WOMEN", "NO"]

    const gotoMyDetail = () => {
        navigate('/my/detail');
    }
    const gotoMySubmit = () => {
        navigate('/my/submit')
    }
    const gotoGetSubmit = () => {
        navigate('/my/received');
    }
    const logout = () => {
        dispatch(logoutAction());
        navigate('/');
    }

    useEffect(() => {
        dispatch(asyncGetUser());
    }, [])

    return (
    <Wrapper>
        <PageName>마이페이지</PageName>
        <ProfileWrapper>
            <SIoPersonCircle />
            {user.nickname && <InfoWrapper>
                <Nickname>{user.nickname.split("_")[0]}</Nickname>
                <div className='my-email'>{user.email}</div>
                {/* <span>{age[dataAge.indexOf(user.ageGrp)]}</span> */}
                <span>{user.age}세</span>
                <span>{gender[dataGender.indexOf(user.gender)]}</span>
            </InfoWrapper>}
        </ProfileWrapper>

        <SubmitWrapper onClick={gotoMyDetail}>
            <SubmitTitle>내가 쓴 글</SubmitTitle>
            <IoIosArrowForward />
        </SubmitWrapper>

        <SubmitWrapper onClick={gotoMySubmit}>
            <SubmitTitle>신청 내역</SubmitTitle>
            <IoIosArrowForward />
        </SubmitWrapper>

        <SubmitWrapper onClick={gotoGetSubmit}>
            <SubmitTitle>받은 신청</SubmitTitle>
            <IoIosArrowForward />
        </SubmitWrapper>

        <SubmitWrapper onClick={logout}>
            <SubmitTitle>로그아웃</SubmitTitle>
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
display: grid;
grid-template-columns: 85px 1fr;
align-items: center;
background-color: var(--button-color);
padding: 30px;
margin-bottom: 15px;
`
const SIoPersonCircle = styled(BsPersonCircle)`
color: #FAFAFA;
width: 70px;
height: 70px;
`
const InfoWrapper = styled.div`
color: #FAFAFA;
.my-email{
    font-size: 13px;
    color: #CCCCCC;
    padding-bottom: 2px;
}
span {
    font-size: 12px;
    margin-right: 10px;
    color: #CCCCCC;
    //padding: 5px;
    //background-color: #CCCCCC;
    //color: #FAFAFA;
    border-radius: 10px;
    
}
`
const Nickname = styled.div`
font-family: nanum-square-bold;
font-size: 20px;
padding-bottom: 5px;
`
const SubmitWrapper = styled.div`
padding: 20px 10px;
margin: 0 10px;
border-bottom: 1px solid var(--button-sub-color);
display: flex;
justify-content: space-between;
cursor: pointer;
`
const SubmitTitle = styled.div`
font-family: nanum-square-bold;
`