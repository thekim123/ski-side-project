import React from 'react'
import styled from 'styled-components'
import { FiSend } from 'react-icons/fi'

export default function CommentForm(props) {
    return (
        <Form onSubmit={props.handleSubmit}>
        <input
            type='text'
            placeholder='댓글 입력'
            value={props.commentInput}
            name='text'
            className='boardDetail-input'
            onChange={props.handleInputChange}
        />
        <button><FiSend className='boardDetail-sendIcon' /></button>
        </Form>
    )
}

const Form = styled.form`
    display: flex;
    background-color: var(--background-color);
    padding: 0px 20px;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding-bottom: 95px;

    .boardDetail-input {
        flex: 1 1;
        height: 40px;
        padding: 3px 7px;
        background-color: #FAFAFA;
        border: none;
        border-radius: 5px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }
    input:focus{
        outline: none;
    }

    button{
        background-color: var(--button-color);
        border: #CCCCCC;
        border-radius: 5px;
        width: 2.8rem;
        margin-left: 7px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }

    .boardDetail-sendIcon {
        color: #FAFAFA;
        width: 1.1rem;
        height: 1.1rem;
    }
`