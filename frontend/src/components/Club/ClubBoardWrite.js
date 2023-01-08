import React, { useRef, useState } from 'react'
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { addPost } from '../../action/clubBoard';

export function ClubBoardWrite() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const club = useLocation().state;
    const titleInput = useRef();
    const contentInput = useRef();
    const [error, setError] = useState({
        title: "",
        content: "",
    });
    const {isNotice} = useParams();

    const resetTitleError = () => {
        setError({...error, title: ""})
    }
    const resetContentError = () => {
        setError({...error, content: ""})
    }

    const validateInput = (title, content) => {
        let t_error = "";
        let c_error = "";
        if (title.trim() === '') {
            t_error = "제목을 입력하세요.";
        } else if (title.length > 50) {
            t_error = "제목은 50자 이하여야 합니다."
        } 
        if (content.trim() === '') {
            c_error = "본문 내용을 입력하세요."
        } else if (content.length > 1000) {
            c_error = "본문 내용은 1000자 이하여야 합니다."
        }

        if (!t_error && !c_error) return true;
        else {
            setError({
                title: t_error,
                content: c_error
            })
            return false;
        }
    }

    const handleSubmit = e => {
        e.preventDefault();
        const enteredTitle = titleInput.current.value;
        const enteredContent = contentInput.current.value;
        if (!validateInput(enteredTitle, enteredContent)){
            return;
        } else {
            const post = {
                clubId: club.id,
                title: enteredTitle,
                content: enteredContent,
                sortScope: isNotice === "true" ? "notice" : "general",
                tempFlag: "N",
            }
            dispatch(addPost(post));
            navigate(`/club/detail/${club.id}`, { state: club });
        } 
    }
    const test = e => {
        console.log(typeof(isNotice));
    }
    return (
    <Wrapper onSubmit={handleSubmit}>
        <Top>
            <Text>
                <TopText>{club.clubNm}</TopText>
                <Exp onClick={test}>{isNotice === "true" ? "공지" : "글"} 작성</Exp>
            </Text>
            <Button>완료</Button>
        </Top>

        <TitleContent>
            <Title>
                <input 
                    type="text"
                    placeholder="제목"
                    ref={titleInput}
                    onClick={resetTitleError} />
                <Error className="boardWrite-error">{error.title ? error.title : null}</Error>
            </Title>
            <Content>
            <textarea 
                placeholder="내용을 입력하세요" 
                ref={contentInput} 
                onClick={resetContentError}>
            </textarea>
            <Error className="boardWrite-error">{error.content ? error.content : null}</Error>
        </Content>
        </TitleContent>
    </Wrapper>
    )
}

const Wrapper = styled.form`
margin-top: 30px;
padding: 0 20px;
`
const Top = styled.div`
display: flex;
justify-content: space-between;
`
const Text = styled.div`

`
const TopText = styled.div`
padding-bottom: 6px;
font-family: nanum-square-bold;
`
const Exp = styled.div`
font-size: 13px;
color: gray;
`
const Button = styled.button`
background-color: var(--button-color);
color: #FAFAFA;
border: none;
border-radius: 5px;
padding: 0 8px;
`

const TitleContent = styled.div`
margin: 30px 0;
`
const Title = styled.div`
input {
    background-color: var(--background-color);
    width: 90%;
    border: none;
    border-bottom: 1px solid var(--button-color);
    padding: 10px;
}
input:focus {
    outline: none;
}
`
const Error = styled.div`
font-size: 12px;
color: #CD5C5C;
padding-top: 8px;
padding-left: 10px;
`
const Content = styled.div`
textarea {
    width: 90%;
    height: 300px;
    border: none;
    padding: 10px;
    margin-top: 14px;
    background-color: var(--background-color);
}
textarea:focus{
    outline: none;
    border-color: #6B89A5;
}
`