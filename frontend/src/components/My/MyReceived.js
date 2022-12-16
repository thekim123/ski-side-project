import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { admitSubmit, asyncGetSubmits, getSubmits, loadCarpools, refuseSubmit } from '../../action/carpool';
import { CarPoolListItem } from '../CarPool/CarPoolListItem';
import { FiArrowDownCircle, FiArrowUpCircle } from 'react-icons/fi';

export default function MyReceived() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [myPostPage, setMyPostPage] = useState("카풀");
    const [myCarpool, setMyCarpool] = useState([]);
    const [submitShow, setSubmitShow] = useState(null);
    const [submitContent, setSubmitContent] = useState(null);
    const carpools = useSelector(state => state.carpool.carpools);
    const user = useSelector(state => state.auth.user);

    const changeMyPost = e => {
        let page = e.target.innerText;
        setMyPostPage(page);
        
        if (page === '카풀') {
            
        } else if (page === '같이타요') {
            //dispatch(loadTayos());
        } else if (page === '동호회') {
            //dispatch(lo)
            //dispatch(loadClubPosts());
        }
    }
    const showDetail = (id) => {
        navigate(`/carpool/detail/${id}`)
    }
    const seeSubmit = async (idx, carpoolId) => {
        const newArr = [...submitShow];
        if (!newArr[idx]) {
            const result = await dispatch(asyncGetSubmits(carpoolId)).unwrap();
            const newUserArr = [...submitContent];
            if (result.length === 0) {
                newUserArr[idx] = "신청자가 없습니다."
            } else {
                newUserArr[idx] = result;
            }
            setSubmitContent(newUserArr);
        }
        newArr[idx] = !newArr[idx];
        setSubmitShow(newArr);
    }

    const admitUser = (submitId, id, idx) => {
        const data = {
            admitUserId: submitId,
            toCarpoolId: id
        }
        dispatch(admitSubmit(data));
        //setSubmitContent([...submitContent, submitContent[idx].state = "테스트"]);
        const newArr = [...submitShow];
        newArr[idx] = false;
        setSubmitShow(newArr);
    }
    const denyUser = (submitId, id, idx) => {
        const data = {
            admitUserId: submitId,
            toCarpoolId: id
        }
        dispatch(refuseSubmit(data));
        const newArr = [...submitShow];
        newArr[idx] = false;
        setSubmitShow(newArr);
    }

    useEffect(() => {
        dispatch(loadCarpools());
    }, [])

    useEffect(() => {
        if (carpools.length > 0) {
            setMyCarpool(carpools.filter(carpool => carpool.user.id === user.id));
            setSubmitShow([...Array(myCarpool.length)].map(x => false));
            setSubmitContent([...Array(myCarpool.length)].map(x => "신청자가 없습니다."));
        }
    }, [carpools])

    return (
        <Wrapper>
            <MyWrapper>
            <SubmitTitle>받은 신청</SubmitTitle>
            <ButtonWrap>
                <Btn onClick={changeMyPost} className={myPostPage === '카풀' ? 'selected' : ''}>카풀</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '같이타요' ? 'selected' : ''}>같이타요</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '동호회' ? 'selected' : ''}>동호회</Btn>
            </ButtonWrap>
            </MyWrapper>

            <CarWrap>{myPostPage === '카풀' && myCarpool.length > 0 && 
                myCarpool.map((carpool, idx) => <>
                <CarPoolListItem key={carpool.id} {...carpool} func={showDetail}/>
                <SubmitWrapper>
                    <InsideWrapper onClick={() => seeSubmit(idx, carpool.id)}>
                        <div>신청자 보기</div>{submitShow[idx] ? <SFiArrowUpCircle /> : <SFiArrowDownCircle />}
                    </InsideWrapper>
                    {submitShow[idx] && 
                        <>
                            {submitContent[idx] === "신청자가 없습니다." ? <UserWrapper>{submitContent[idx]}</UserWrapper>
                                : <UserWrapper>{submitContent[idx].map(submit => 
                                <UserRow>
                                    <Name>{submit.fromUser.nickname.split("_")[0]}</Name>
                                    <Info>{submit.fromUser.ageGrp}</Info>
                                    <Info>{submit.fromUser.gender}</Info>
                                    {submit.state === "0" ? 
                                    <ButtonWrapper>
                                        <Button className='mine-ok' onClick={() => admitUser(submit.id, submit.toCarpool.id, idx)}>수락</Button>
                                        <Button className='mine-c' onClick={() => denyUser(submit.id, submit.toCarpool.id, idx)}>거절</Button>
                                    </ButtonWrapper> 
                                    : <InfoText>신청 {submit.state}한 상대</InfoText>}
                                </UserRow>)}</UserWrapper>}
                        </>}
                </SubmitWrapper>
                </>)}
            </CarWrap>
        </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 20px 0px 50px 0px;
`
const PageName = styled.div`
font-family: nanum-square-bold;
font-size: 19px;
padding: 0 20px;
margin-bottom: 15px;
`
const SubmitTitle = styled.div`
font-family: nanum-square-bold;
`
const MyWrapper = styled.div`
padding: 10px 20px;
padding-bottom: 10px;
`
const ButtonWrap = styled.div`
display: flex;
padding: 20px 0 10px 0;
border-bottom: 1px solid var(--button-color);
.selected {
    color: var(--button-color)
}
`
const Btn = styled.div`
font-size: 13px;
font-family: nanum-square-bold;
color: var(--button-sub-color);
padding-right: 16px;
`

const ItemWrapper = styled.div`
`
const CarWrap = styled.div`
margin: 15px;
margin-top: 3px;
`
const SubmitWrapper = styled.div`
display: grid;
justify-items: end;
margin-bottom: 15px;
`
const InsideWrapper = styled.div`
display: flex;
div{
    align-self: center;
    font-size: 14px;
}
`
const SFiArrowDownCircle = styled(FiArrowDownCircle)`
margin-left: 8px;
width: 20px;
height: 20px;
`
const SFiArrowUpCircle = styled(FiArrowUpCircle)`
margin-left: 8px;
width: 20px;
height: 20px;
`
const UserWrapper = styled.div`
margin: 6px;
padding: 15px;
padding-bottom: 6px;
background-color: #FAFAFA;
border-radius: 10px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
margin-bottom: 13px;
width: 80%;
`
const UserRow = styled.div`
display: grid;
grid-template-columns: 80px 40px 40px 1fr;
margin-bottom: 11px;
align-items: center;
`
const Name = styled.div`
font-family: nanum-square-bold;
`
const Info = styled.div`
color: #CCCCCC;
font-size: 13px;
`
const Button = styled.button`
border: none;
border-radius: 10px;
justify-self: end;
margin-left: 5px;
`
const ButtonWrapper = styled.div`
display: flex;
justify-self: end;
.mine-ok {
    background-color: var(--button-color);
    color: #FAFAFA;
    padding: 10px 12px;
}
.mine-c{
    background-color: #FAFAFA;
    color: var(--button-color);
    border: 1px solid var(--button-color);
    padding: 10px 12px;    
}
`
const InfoText = styled.div`
justify-self: end;
font-size: 14px;
`