import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom';
import styled from 'styled-components'
import { getCarpool } from '../../action/carpool';
import Chat from '../Chat/Chat'
import { CarPoolListItem } from './CarPoolListItem';

export default function CarPoolChat() {
    const {id} = useParams();
    const dispatch = useDispatch();
    const post = useSelector(state => state.carpool.carpool);
    const {type} = useParams();

    useEffect(() => {
        dispatch(getCarpool(id));
    }, [])

    useEffect(() => {

    }, [post]) 
    return (
    <Wrapper>
        <Top>
        {post && <CarPoolListItem {...post} />}
        <What>{type === 'quest' ? <Button>카풀 신청하기</Button> : <div></div>}</What>
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
`
const Button = styled.button`
background-color: var(--button-sub-color);
border: none;
border-radius: 10px;
justify-self: end;
padding: 10px;
`
const Bottom = styled.div`
margin-top: 230px;
`