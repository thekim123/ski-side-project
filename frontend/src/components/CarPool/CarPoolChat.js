import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components'
import { admitSubmit, getCarpool, getSubmits, refuseSubmit } from '../../action/carpool';
import Chat from '../Chat/Chat'
import { CarPoolListItem } from './CarPoolListItem';
import OkButtonModal from '../common/OkButtonModal'

export default function CarPoolChat() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const post = useSelector(state => state.carpool.carpool);
    const user = useSelector(state => state.auth.user);
    const submits = useSelector(state => state.carpool.submits);
    const [isMine, setIsMine] = useState(false);
    const [state, setState] = useState("0");
    const [submitOpen, setSubmitOpen] = useState(false);
    const {id} = useParams();
    const {type} = useParams();
    const {room} = useParams();
    const submitId = room.split("submit")[1].split("writer")[0];
    
    const gotoDetail = (id) => {
        navigate(`/carpool/detail/${id}`)
    }

    const handleSubmit = e => {
        setSubmitOpen(true);
    }
    const closeSubmit = e => {
        setSubmitOpen(false);
    }

    const admitUser = () => {
        const data = {
            admitUserId: submitId,
            toCarpoolId: id
        }
        dispatch(admitSubmit(data));
    }
    const denyUser = () => {
        const data = {
            admitUserId: submitId,
            toCarpoolId: id
        }
        dispatch(refuseSubmit(data));
    }

    useEffect(() => {
        dispatch(getCarpool(id));
        dispatch(getSubmits(id));
    }, [])

    useEffect(() => {
        if (post) {
            if (post.user.username === user.username) {
                setIsMine(true);
            }
        }
    }, [post]) 

    useEffect(() => {
        
        if (submits) {
            const sbm = submits.find(submit => submit.fromUser.id === user.id);
            if (sbm !== undefined) {
                //수락 만들고 만약 state가 수락이면 다르게 보이도록.
                setState(sbm.state);
                console.log(sbm);
            }
        }
    }, [submits]);
    return (
    <Wrapper>
        <Top>
        {post && <CarPoolListItem {...post} func={gotoDetail} />}
        {isMine && 
        <What>
            {state !== '0' ? <Text>신청 {state}한 상대입니다.</Text> 
            :
            <MineRow>
                <Text>{} 님이 카풀 신청을 했습니다.</Text>
                <Button className='mine-ok' onClick={admitUser}>수락</Button><Button className='mine-c' onClick={denyUser}>거절</Button>
            </MineRow>}
        </What>}
        {/* 나중에 !isMine으로 바꾸기 */}
        {isMine && 
        <What>
            {type === 'quest' && <Button onClick={handleSubmit}>카풀 신청하기</Button>}
            {type !== 'quest' && <div>{state === '0' ? <div>승인 대기</div> : <div>신청이 {state}되었습니다.</div>}</div>}
        </What>}
        <OkButtonModal 
                        open={submitOpen}
                        close={closeSubmit}
                        message={"신청하시겠습니까?"}
                        ok={"신청"}
                        usage={"carpoolSubmit"}
                        submitId={user.id}
                        writerId={post.user.id}
                        targetId={id} />
        </Top>
        <Bottom>
        <Chat />
        </Bottom>

    </Wrapper>
    )
}

const Wrapper = styled.div`
margin-top: 30px;
`
const Top = styled.div`
padding: 0 15px;
padding-bottom: 10px;
border-bottom: 1px solid var(--button-sub-color);
margin-top: 60px;
position: fixed;
    left: 0;
    right: 0;
    top: 0;
    z-index: 99;
background-color: var(--background-color);
`
const What = styled.div`
display: grid;
justify-items: end;
`
const Button = styled.button`
background-color: var(--button-color);
color: #FAFAFA;
border: none;
border-radius: 10px;
justify-self: end;
padding: 10px;
margin-left: 5px;
`
const MineRow = styled.div`
display: flex;
align-items: center;
.mine-ok {
    background-color: var(--button-color);
    color: #FAFAFA;
    padding: 10px 15px;
}
.mine-c{
    background-color: #FAFAFA;
    color: var(--button-color);
    border: 1px solid var(--button-color);
    padding: 10px 15px;    
}
`
const Text = styled.div`
font-size: 14px;
margin-right: 5px;
`
const Bottom = styled.div`
margin-top: 230px;
`