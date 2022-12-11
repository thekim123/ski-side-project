import React, { useRef, useState } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { asyncDeleteClub, deleteClub } from '../../action/club';

export default function OkCancelModal(props) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const inputRef = useRef();
    const [error, setError] = useState(null);

    const clickOutside = (e) => {
        if (e.target.className === "openModal skiModal") {
            setError(null);
            props.close();
        }
    }
    const clickX = () => { 
        setError(null);
        props.close();
    }
    const resetError = () => {
        setError(null);
    }


    const fetchDelClub = async (e) => {
        e.preventDefault();
        console.log(inputRef.current.value);
        const inp = inputRef.current.value;
        if (inp !== props.name) {
            setError("입력과 동호회 명이 일치하지 않습니다.")
            return;
        } else {
            const result = await dispatch(asyncDeleteClub(props.clubId)).unwrap();
            navigate('/club'); 
        }
    }
    return (
        <Wrapper>
        <div className={props.open ? "openModal skiModal" : "skiModal"} onClick={clickOutside}>
        {props.open ? (
            <Section>
                <Button onClick={clickX}>&times;</Button>
                <Message>{props.message}</Message>
                <SubMsg>{props.sub}</SubMsg>
                <Form onSubmit={fetchDelClub}>
                    <Sinput ref={inputRef} onClick={resetError} />
                    <OkBtn>제출</OkBtn>
                </Form>
                <Error>{error ? error : null}</Error>
            </Section>
        ): null}
        </div>
    </Wrapper>
    )
}

const Wrapper = styled.div`
    .skiModal {
        display: none;
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 99;
        background-color: rgba(0, 0, 0, 0.6);
    }

    .openModal {
        display: flex;
        align-items: center;
        animation: modal-bg-shadow 0.3s;
    }
`
const Section = styled.div`
    display: grid;
    width: 330px;
    margin: 30px;
    border-radius: 13px;
    background-color: #FAFAFA;
    //animation: modal-show 0.3s;
    overflow: hidden;
    padding: 10px 20px 20px 20px;
`
const Message = styled.div`
padding-top: 10px;
`
const SubMsg = styled.div`
font-size: 13px;
color: gray;
padding-top: 8px;
padding-bottom: 30px;
`
const BtnWrap = styled.div`
display: flex;
justify-content: space-between;
`
const Form = styled.form`
display: flex;
justify-content: space-between;
padding-bottom: 10px;
input:focus{
    outline: none;
}
`
const Sinput = styled.input`
border: none;
border-bottom: 1px solid var(--button-color);
width: 220px;
font-family: nanum-square;
`
const Box = styled.div`
display: flex;
color: var(--button-color);
font-family: nanum-square-bold;
`
const CancelBtn = styled.div`
`
const OkBtn = styled.button`
margin-left: 15px;
background-color: var(--button-color);
border: none;
border-radius: 5px;
color: #FAFAFA;
padding: 8px 12px;
font-family: nanum-square;
`

const Button = styled.div`
font-size: 28px;
justify-self: end;
color: gray;
`
const Error = styled.div`
font-size: 12px;
color: #CD5C5C;
`