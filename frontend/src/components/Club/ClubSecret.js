import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { AiFillLock } from 'react-icons/ai';
import resorts from '../../data/resort.json';
import { BsPeopleFill } from 'react-icons/bs';
import { MdEmojiPeople } from 'react-icons/md'
import { TbGenderBigender } from 'react-icons/tb'
import { asyncEnrollClub, enrollClub, getClubUser, getSingleClub } from '../../action/club';

export function ClubSecret() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const age = ["나이 제한 없음", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대"]
    const dataAge = ["ANY", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY"]
    const gender = ["남", "여", "성별 무관"]
    const dataGender = ["MEN", "WOMEN", "NO"]
    const club = useSelector(state => state.club.club);
    const clubUser = useSelector(state => state.club.users);
    const user = useSelector(state => state.auth.user);
    const {id} = useParams();
    const [btnText, setBtnText] = useState("가입 신청하기");
    //const club = useLocation().state;

    const gotoDetail = e => {
        //navigate(`/club/detail/${club.id}`, { state: club });
        //동호회 인원 추가
        if (club.openYn === "Y") {
            //dispatch(enrollClub(id)); 
            navigate(`/club/detail/${id}`);
        }
        //navigate(`/club/detail/${id}`);
    }
    const submitClub = async() => {
        const result = await dispatch(asyncEnrollClub(id)).unwrap();
        dispatch(getClubUser(id));
        //setBtnText("승인 대기");
    }

    /*
    useEffect(() => {
        const loadClubUser = async () => {
            const result = await dispatch(asyncEnrollClub(id)).unwrap();
        }
        dispatch(getSingleClub(id));
        //dispatch(getClubUser(id));
        loadClubUser();
    }, [id]);

    useEffect(() => {
        if (clubUser) {
            console.log(clubUser);
        }
    }, [clubUser])*/

    // 나중에 삭제
    useEffect(() => {
        dispatch(getSingleClub(id));
    }, []);

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
                {/* <SBsPeopleFill /> */}
                {/* <Cnt>{club.memberCnt}명</Cnt> */}
            </CntBox>
            {/* 연령대 */}
            <CntBox>
                <MdEmojiPeople className='clubSecret-icon'/>
                <Cnt>{age[dataAge.indexOf(club.ageGrp)]}</Cnt>
            </CntBox>
            {/* 성별 */}
            <CntBox>
                <TbGenderBigender className='clubSecret-icon'/>
                <Cnt>{gender[dataGender.indexOf(club.gender)]}</Cnt>
            </CntBox>
        </InfoBox>
        <ContentBox>
            <ClubContent>{club.memo}</ClubContent>
        </ContentBox>
        {club.openYn === "Y" && <Button onClick={gotoDetail}>둘러보기</Button>}
        {club.openYn === "N" && <div><Button onClick={submitClub}>가입 신청하기</Button></div>}
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
.clubSecret-icon{
    width: 20px;
    height: 20px;
    color: var(--button-color);
}
`
const SBsPeopleFill = styled(BsPeopleFill)`
padding-right: 3px;
color: var(--button-color);
`
const Cnt = styled.div`
font-size: 13px;
//padding-left: 3px;
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
line-height: 23px;
//text-align: center;
color: #404040;
`
const Button = styled.button`
background-color:var(--button-color);
color: #FAFAFA;
padding: 13px 20px;
border-radius: 15px;
border: none;
`