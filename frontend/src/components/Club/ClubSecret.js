import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { AiFillLock } from 'react-icons/ai';
import resorts from '../../data/resort.json';
import { BsPeopleFill } from 'react-icons/bs';
import { MdEmojiPeople } from 'react-icons/md'
import { TbGenderBigender } from 'react-icons/tb'
import { asyncEnrollClub, asyncGetClub, asyncGetClubUser, asyncGetWaitingUser, enrollClub, getClubUser, getSingleClub } from '../../action/club';

export function ClubSecret() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const age = ["나이 제한 없음", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대"]
    const dataAge = ["ANY", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY"]
    const gender = ["남", "여", "성별 무관"]
    const dataGender = ["MEN", "WOMEN", "NO"]
    const status = useSelector(state => state.club.status);
    const club = useSelector(state => state.club.club);
    const clubUser = useSelector(state => state.club.users);
    const user = useSelector(state => state.auth.user);
    const waitingUsers = useSelector(state => state.club.waitingUsers);
    const {id} = useParams();
    const [btnText, setBtnText] = useState(""); //useEffect에서 신청자 목록 불러와서 승인 상태에 따라.
    const [isWaiting, setIsWaiting] = useState(false);
    //const club = useLocation().state;

    const gotoDetail = async e => {
        let data = {
            userId: user.id,
            clubId: id,
        }
        await dispatch(asyncEnrollClub(data)).unwrap();
        navigate(`/club/detail/${id}`);
        
    }
    const submitClub = async(e) => {
        if (e.target.innerText === "가입 신청하기") {
        let data = {
            userId: user.id,
            clubId: id,
        }
        dispatch(asyncEnrollClub(data));
        setIsWaiting(true);
        //관리자의 승인
        }
    }

    
    useEffect(() => {
        const loadClub = async () => {

        }
        const loadClubUser = async () => {
            const result = await dispatch(asyncGetClubUser(id)).unwrap();
        }
        dispatch(asyncGetClub(id));
        dispatch(asyncGetClubUser(id));
    }, [id]);

    useEffect(() => {
        if (club) {
            if (club.openYn === 'N') dispatch(asyncGetWaitingUser(id));
        }
    }, [club])

    useEffect(() => {
        if (waitingUsers) {
            const enrollUser = waitingUsers.find(mem => mem.username === user.username);
            console.log(enrollUser);
            if (enrollUser !== undefined) {
                setIsWaiting(true);
            } else {
                setIsWaiting(false);
            }
        }
    }, [waitingUsers])
/*
    // 나중에 삭제
    useEffect(() => {
        dispatch(getSingleClub(id));
    }, []);*/

    return (
    <>
    {status === 'complete' &&  
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
        {club.openYn === "Y" && <Button onClick={gotoDetail}>가입하기</Button>}
        {club.openYn === "N" && !isWaiting && <div><Button onClick={submitClub}>가입 신청하기</Button></div>}
        {club.openYn === "N" && isWaiting && <Waiting>승인 대기</Waiting>}
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

const Waiting = styled.div`
//background-color:var(--button-color);
//color: #FAFAFA;
padding: 13px 20px;
`