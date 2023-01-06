import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { BiArrowBack, BiUser, BiBell } from "react-icons/bi"
import { BsChatDots } from 'react-icons/bs'
import { HiOutlineChat } from 'react-icons/hi'
import {FaSkiing} from 'react-icons/fa'

export function TopBar() {
    const navigate = useNavigate();
    const t = useParams()['*'];


    const goback = () => {
        //detail까지 slice
        if (t === 'carpool/detail') {
            console.log("t")
            navigate(-1, {state: "test"});
        }
        else navigate(-1);
    }
    const gotoMyPage = e => {
        navigate('/my');
    }
    const gotoChatPage = () => {
        navigate('/chat');
    }
    const gotoAlarm = () => {
        navigate('/alarm');
    }
    return(
        <>
        {t !== 'login' && <Container>
            <LeftSide>
                {t !== '' && t !== 'board' && t !== 'carpool' && t !== 'tayo' && t !== 'club' && t !== 'my' && t !== 'chat' && t !== 'alarm' &&
                    <BiArrowBack className="backBtn" onClick={goback} />}
                {t === '' && <PageName>같이 타요!</PageName>}
            </LeftSide>
            <RightSide>
                {/* <HiOutlineChat className='chatBtn' onClick={gotoChatPage}/> */}
                {/* <BiBell className="bellBtn" onClick={gotoAlarm}/> */}
                <BiUser className="myBtn" onClick={gotoMyPage}/>
            </RightSide>
        </Container>}
        </>
    )
}

const Container = styled.div`
position: fixed;
    left: 0;
    right: 0;
    top: 0;
    z-index: 99;
    background-color: #E1EEFF;
    background-color: #EEF3F7;
    //border-bottom: 0.01rem solid #CCCCCC;
    height: 48px;
    padding-top: 3px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .backBtn {
        width: 1.2rem;
        height: 1.2rem;
        margin: 7px;
        margin-left: 10px;
        color: var(--button-color);
    }
`

const LeftSide = styled.div`
    display:flex;
`

const RightSide = styled.div`
    display: flex;
    margin: 7px;

    .bellBtn, .myBtn, .chatBtn{
        margin-right: 4px;
        margin-left: 8px;
        width: 1.7rem;
        height: 1.7rem;
        color: var(--button-color);
    }
    
`

const PageName = styled.div`
    margin: 0;
    padding-top: 4px;
    padding-left: 13px;
    font-family: nanum-square-bold;
    font-size: 18px;
    color: var(--button-color);
`