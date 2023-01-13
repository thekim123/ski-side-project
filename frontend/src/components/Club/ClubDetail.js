import React, { forwardRef, useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { asyncGetClub, getClubUser, getSingleClub } from '../../action/club';
import styled from 'styled-components';
import { HiPlus } from 'react-icons/hi';
import { BsCircle } from 'react-icons/bs';
import { AiFillRightCircle } from 'react-icons/ai';
import { IoMdAddCircle } from 'react-icons/io';
import resorts from '../../data/resort.json';
import { BsPeopleFill } from 'react-icons/bs'
import { loadClubPosts } from '../../action/clubBoard';
import OkCancelModal from '../common/OkCancelModal';
import { ClubUserModal } from '../common/ClubUserModal';

export function ClubDetail() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    //const club = useLocation().state;
    const club = useSelector(state => state.club.club);
    const clubBoards = useSelector(state => state.clubBoard.clubBoards);
    const clubUsers = useSelector(state => state.club.users);
    const user = useSelector(state => state.auth.user);
    const {id} = useParams();
    const [delOpen, setDelOpen] = useState(false);
    const [isCap, setIsCap] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [enrollModalOpen, setEnrollModalOpen] = useState(false);

    const gotoEdit = e => {
        navigate(`/club/edit/${id}`);
    }
    const delClub = e => {
        setDelOpen(true);
    }
    const closeDel = () => {
        setDelOpen(false);
    }
    const closeModal = () => {
        setModalOpen(false);
    }
    const closeEnrollModal = () => {
        setEnrollModalOpen(false);
    }

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
    const clickNoticeMore = e => {
        navigate(`/club/board/list/${id}`, {state: "공지"});
    }
    const clickGeneralMore = e => {
        navigate(`/club/board/list/${id}`, {state: "일반"});
    }

    const gotoDetail = (boardId) => {
        navigate(`/club/board/detail/${boardId}/${id}`, {state: club.clubNm})
    }

    const showMember = e => {
        console.log(clubUsers);
        setModalOpen(true);
    }

    const showEnroll = e => {
        setEnrollModalOpen(true);
    }

    useEffect(() => { //새로 로그인 한 후 라든지.. 그럴때를 대비해 state.club이 null인 경우에만 dispatch 호출.
        dispatch(loadClubPosts(id));
        dispatch(getSingleClub(id));
        dispatch(getClubUser(id));
    }, [dispatch, id]);

    useEffect(() => {
        console.log("clubUsers", clubUsers);
        if (clubUsers) {
            let myInfo = clubUsers.find(cuser => cuser.nickname === user.nickname);
            if (myInfo === undefined) {
                setIsCap(false);
            }
            else if (myInfo.role === 'ADMIN') {
                setIsCap(true);
            } else {
                setIsCap(false);
            }
        }
    }, [clubUsers])

    return (
    <>
    {/* 방장한테만 보이게. */}

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
                {isCap && <UserButton onClick={showMember}>회원 조회</UserButton>}
                {club && club.openYn === 'N' && <UserButton onClick={showEnroll}>신청자 조회</UserButton>}
            </CntBox>

        </InfoBox>
        <ClubUserModal 
            open={modalOpen}
            close={closeModal}  
            type="showUser"  
            userList={clubUsers
                        .filter(user => user.status !== 'WAITING')
                        .map(mem => mem.role === 'ADMIN' ? {...mem, order: 1} : {...mem, order: 3})}
        />
        <ClubUserModal 
            open={enrollModalOpen}
            close={closeEnrollModal}  
            type="showEnroll"  
            clubId={id}
            userList={clubUsers
                        .filter(user => user.status === 'WAITING')}
        />
        <TopLine> </TopLine>

        <NoticeBox>
            <NoticeTop>
                <ButtonBox>
                <Notice>공지</Notice>
                {/* 공지는 방장이나 부방장만 보이게. */}
                {isCap && <SHiPlus className="tayo-plus" onClick={clickNoticePlus}/>}
                </ButtonBox>
                <MoreBox>{clubBoards.filter(board => board.sortScope === "notice").length > 2 && <Button onClick={clickNoticeMore}>more...</Button>}</MoreBox>
            </NoticeTop>
            
            {clubBoards.length > 0 && clubBoards.filter(board => board.sortScope === "notice").map((board, i) => (
                i < 2 && <NoticeItem key={board.id}>
                    <NoticeItemWrap onClick={() => gotoDetail(board.id)}>
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
                <MoreBox>{clubBoards.filter(board => board.sortScope === "general").length > 2 && <Button onClick={clickGeneralMore}>more...</Button>}</MoreBox>
            </BoardTop>

            {clubBoards.length > 0 && clubBoards.filter(board => board.sortScope === "general").map((board, i) => (
                i < 2 && <NoticeItem key={board.id}>
                    <NoticeItemWrap className='club-normal' onClick={() => gotoDetail(board.id)}>
                        <TitleWho>
                        <NoticeContent>{cutText(board.title, 23)}</NoticeContent>
                        <BoardWho>{cutText(board.nickName.split("_")[0], 4)}</BoardWho>
                        </TitleWho>
                        <NoticeDate>2022.11.02</NoticeDate>
                    </NoticeItemWrap>
                </NoticeItem>
                ))
            }
        </NoticeBox>

        <EditDelBtn>
        <div></div>
                <BtnWrap>
                {isCap && <EditBtn onClick={gotoEdit} className='club-detail-topBtn'>수정</EditBtn>}
                {isCap && <DelBtn onClick={delClub} className='club-detail-topBtn'>삭제</DelBtn>}
                {/* 모달창 */}
                {club && <OkCancelModal 
                    open={delOpen}
                    close={closeDel}
                    message={`'${club.clubNm}' 동호회를 삭제하시겠습니까?`}
                    sub={'삭제하려면 동호회 명을 입력하세요.'}
                    name={club.clubNm} 
                    ok={"삭제"}
                    clubId={id}/>}
                </BtnWrap>
        </EditDelBtn>
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

display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
margin-bottom: 50px;
`
const EditDelBtn = styled.div`
display: flex;
justify-content: space-between;
padding-top: 20px;
padding-right: 10px;
justify-self: end;
`
const BtnWrap = styled.div`
display: flex;
.club-detail-topBtn{
    margin-left: 8px;
    color: var(--button-color);
    //border: 1px solid var(--button-color);
    border: none;
    border-radius: 3px;
    padding: 8px 11px;
    background-color: #FAFAFA;
}
`
const EditBtn = styled.button`

`
const DelBtn = styled.button`
`
const ClubName = styled.div`
padding-top: 70px;
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

const UserButton = styled.button`
border: none;
padding: 7px;
font-size: 12px;
background-color: var(--button-sub-color);
border-radius: 5px;
margin-left: 5px;
`