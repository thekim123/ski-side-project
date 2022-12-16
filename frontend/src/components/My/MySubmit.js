import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { getMyCarSubmit, getSubmits, loadCarpools } from '../../action/carpool';
import { CarPoolListItem } from '../CarPool/CarPoolListItem';
import MyCarSubmit from './MyCarSubmit';

export default function MySubmit() {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const [myPostPage, setMyPostPage] = useState("카풀");
    const [myCarpool, setMyCarpool] = useState([]);
    //const carpools = useSelector(state => state.carpool.carpools);
    const myCar = useSelector(state => state.carpool.my);
    const user = useSelector(state => state.auth.user);

    const changeMyPost = e => {
        let page = e.target.innerText;
        setMyPostPage(page);
        
        if (page === '카풀') {
            dispatch(loadCarpools());
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

    useEffect(() => {
        //dispatch(loadCarpools());
        dispatch(getMyCarSubmit());
    }, []);

    useEffect(() => {
        if (myCar.length > 0) {
            setMyCarpool(myCar);
            console.log(myCarpool);
        }
    }, [myCar]);

    return (
        <Wrapper>
            <MyWrapper>
            <SubmitTitle>신청 내역</SubmitTitle>
            <ButtonWrap>
                <Btn onClick={changeMyPost} className={myPostPage === '카풀' ? 'selected' : ''}>카풀</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '같이타요' ? 'selected' : ''}>같이타요</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '동호회' ? 'selected' : ''}>동호회</Btn>
            </ButtonWrap>
            </MyWrapper>

            <CarWrap>{myPostPage === '카풀' && myCarpool.length > 0 && 
                myCarpool.map(carpool => <CarPoolListItem key={carpool.toCarpool.id} {...carpool.toCarpool} func={showDetail}/>)}
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