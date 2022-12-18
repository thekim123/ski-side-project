import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { CarPoolListItem } from './CarPoolListItem'
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill, BsFilePost, BsArrowRight } from 'react-icons/bs'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { getCarpool, getSubmits, submitCarpool } from '../../action/carpool'
import OkButtonModal from '../common/OkButtonModal'
import { getUser } from '../../action/auth'

export function CarPoolDetail() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isMine, setIsMine] = useState(false);
    //const carpool = useLocation().state;
    const carpool = useSelector(state => state.carpool.carpool);
    const user = useSelector(state => state.auth.user);
    const submits = useSelector(state => state.carpool.submits);
    const [date, setDate] = useState(null);
    const [submitOpen, setSubmitOpen] = useState(false);
    const [questBtn, setQuestBtn] = useState("문의하기");
    const [showBtn, setShowBtn] = useState("신청하기");
    const [submitUser, setSubmitUser] = useState(false);
    const [delOpen, setDelOpen] = useState(false);
    const {id} = useParams();

    const handlePencil = e => {
        navigate(`/carpool/edit/${id}`)
    }
    const handleTrash = e => {
        setDelOpen(true);
    }
    const closeDel = e => {
        setDelOpen(false);
    }

    const dummyFunc = (id) => {

    }

    const closeSubmit = e => {
        setSubmitOpen(false);
    }

    const handleQ = e => {
        navigate(`/carpool/chat/${id}/carpool${id}submit${user.id}writer${carpool.user.id}/quest`)
    }
    const gotoChat = () => {
        navigate(`/carpool/chat/${id}/carpool${id}submit${user.id}writer${carpool.user.id}/chat`)
    }

    const handleSubmit = e => {
        setSubmitOpen(true);
    }

    useEffect(() => {
        setSubmitUser(false);
        dispatch(getCarpool(id));
    }, [dispatch]);

    useEffect(() => {
        dispatch(getSubmits(id));
    }, [])

    useEffect(() => {
        if (carpool && user) {
            //setDate(new Date(carpool.createDate));
            const t = new Date(carpool.createDate)
            const month = t.getMonth() + 1
            setDate(t.getFullYear() + "."+month + "." + t.getDate()+". "+carpool.createDate.slice(11,16));

            if (user.username === carpool.user.username) {
                setIsMine(true);
            }
        }
    }, [carpool, user])

    useEffect(() => {
        if (submits) {
            const sbm = submits.find(submit => submit.fromUser.id === user.id);
            if (sbm !== undefined) {
                setSubmitUser(true); 
            } else {
                setSubmitUser(false);
            }
        }
    }, [submits]);


    return (
        <>
    {carpool && user && <Wrapper>
        <Top>
            <div>
                <User>{carpool.user.nickname.split("_")[0]}</User>
                <Time>{date}</Time>
            </div>
            <Icons>
                {isMine && <HiPencil className="boardPost-icon" onClick={handlePencil}/>}
                {isMine && <BsTrashFill className="boardPost-icon" onClick={handleTrash}/>}
            </Icons>          
        </Top>
        <OkButtonModal
                            open={delOpen}
                            close={closeDel}
                            message={"게시글을 삭제하시겠습니까?"}
                            ok={"삭제"}
                            usage={"carpoolDel"}
                            targetId={id}/>  

        <CarPoolListItem {...carpool} func={dummyFunc}/>

        {/* Content */}
        <Middle>
            <Tags>
                <div>
                {carpool.negotiate.departure && <TalkTag>출발지 협의 가능</TalkTag>}
                {carpool.negotiate.destination && <TalkTag>도착지 협의 가능</TalkTag>}
                </div>
                <div>
                {carpool.negotiate.departTime && <TalkTag>출발 시간 협의 가능</TalkTag>}
                {carpool.negotiate.boardingPlace && <TalkTag>탑승 장소 협의 가능</TalkTag>}
                </div>
            </Tags>
            <Content>
                <ContentTitle>추가 사항</ContentTitle>
                {carpool.memo}
            </Content>
            {!isMine &&
            <div>{!submitUser && <div>
            <ButtonBox className='nosubmit-btn'>
            <SButton onClick={handleQ}>문의하기</SButton>
            <Button onClick={handleSubmit}>신청하기</Button>
            </ButtonBox>
                        <OkButtonModal 
                        open={submitOpen}
                        close={closeSubmit}
                        message={"신청하시겠습니까?"}
                        ok={"신청"}
                        usage={"carpoolSubmit"}
                        submitId={user.id}
                        writerId={carpool.user.id}
                        targetId={id} />
            </div>}

            {submitUser && 
                <ButtonBox><Button onClick={gotoChat}>채팅하기</Button></ButtonBox>
            }</div>
            }
            {/* 신청을 했는데 state가 아직 0이라면 신청 취소, state가 수락이면 신청 수락됨 거절이라면 신청 거절됨 */}

        </Middle>
    </Wrapper>}</>
    )
}

const Wrapper = styled.div`
margin-top: 10px;
padding: 20px;
`
const Top = styled.div`
display:flex;
justify-content: space-between;
padding-bottom: 10px;
`
const User = styled.span`
margin-right: 10px;
padding-top: 8px;
padding-left: 3px;
font-size: 12px;
`
const Time = styled.span`
padding-top: 8px;
font-size: 12px;
`
const Icons = styled.div`
color: var(--button-color);
.boardPost-icon{
    padding: 3px;
    width: 1.2rem;
    height: 1.2rem;
}
`

// Middle - content
const Middle = styled.div`
//background-color: #FAFAFA;
//border-radius: 10px;
//box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
margin-top: 10px;
padding: 20px 10px;
.nosubmit-btn{
    grid-template-columns: 1fr 1fr;
}
`
const Tags = styled.div`
display: inline-block;
padding-bottom: 10px;
div {
    margin-bottom: 13px;
}
`
const TalkTag = styled.span`
background-color: #005C00;
font-size: 12px;
padding: 6px 8px;
margin-right: 5px;
border-radius: 3px;
margin-bottom: 5px;
color: #FAFAFA;
text-align: center;
//width: 104px;
`
const TagBox = styled.div`
padding-top: 10px;
`
const Tag = styled.span`
background-color: var(--button-sub-color);
font-size: 12px;
padding: 4px 6px;
margin-right: 5px;
border-radius: 3px;
margin-top: 5px;
color: #FAFAFA;
text-align: center;
`

const Content = styled.div`
font-size: 15px;
font-weight: 300;
padding: 20px 0;
//border: 1px solid #CCCCCC;
//border-radius: 10px;
margin: 10px 0;
color: #646464;
line-height: 20px;
`
const ContentTitle = styled.div`
font-family: nanum-square-bold;
padding-bottom: 10px;
color: black;
`
const ButtonBox = styled.div`
display: grid;

position: fixed;
bottom: 0;
left: 0;
right: 0;
padding-bottom: 85px;
`
const Button = styled.div`
background-color: var(--button-color);
padding: 12px;
margin: 7px;
margin-top: 20px;
color: #FAFAFA;
border-radius: 10px;
font-size: 14px;
text-align: center;
`
const SButton = styled.div`
background-color: #FAFAFA;
border: 1px solid var(--button-color);
padding: 12px;
margin: 7px;
margin-top: 20px;
color: var(--button-color);
border-radius: 10px;
font-size: 14px;
text-align: center;
`