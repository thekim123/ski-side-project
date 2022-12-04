import React, { useState, useRef, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { updatePost, getSinglePost } from '../../action/board';
import styled from 'styled-components'
import { IoMdArrowDropdown } from 'react-icons/io';
import resorts from '../../data/resort.json'

export function EditBoard() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const resortData = resorts.filter(resort => resort.id !== null);
    const [showSelectBox, setShowSelectBox] = useState(false);
    const [selectedResort, setSelectedResort] = useState("스키장 선택");
    const titleInput = useRef();
    const contentInput = useRef();
    const [state, setState] = useState({
        title: "",
        content: "",
        resortName: "",
        create_dt: "",
    })
    let {id} = useParams();
    const originalPost = useSelector(state => state.board.post);
    const [error, setError] = useState({
        title: "",
        content: ""
    });

    const toggleSelectBox = () => {
        setShowSelectBox(!showSelectBox);
    };

    const handleResortClick = (e) => {
        setSelectedResort(e.target.id);
        setShowSelectBox(false);
    }

    const resetError = (e) => {
        if (e.target.className === 'boardWrite-title') {
            setError({...error, title: ""});
        } else if (e.target.className === 'boardWrite-content') {
            setError({...error, content: ""});
        }
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

    const handleInputChange = (e) => {
        let {name, value} = e.target;
        setState({...state, [name]: value});

    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const enteredTitle = titleInput.current.value;
        const enteredContent = contentInput.current.value;
        if (!validateInput(enteredTitle, enteredContent)){
            return;
        } else {
            const post = {
                id: id,
                title: enteredTitle,
                content: enteredContent,
                resortName: selectedResort,
                //create_dt: originalPost.create_dt,
            }
            dispatch(updatePost(post));
            navigate("/board");
        }
    }

    useEffect(() => {
        if (id) {
            dispatch(getSinglePost(id));
        }
    }, [dispatch, id]);

    useEffect(() => {
        if(originalPost) {
            console.log(originalPost);
            setState({...originalPost});
            setSelectedResort(originalPost.resort.resortName);
        }
    }, [originalPost]);

    return (
    <Wrapper onSubmit={handleSubmit}>
        <Top>
        <div className="boardWrite-top">글 수정</div>
        <Buttons>
            {/* <button className="boardWrite-tempSave">임시 저장</button> */}
            <button className="boardWrite-save">저장</button>
        </Buttons>
        </Top>
        <Top>
        <SelectBox>
                <div className="dropdown">
                    <div className="dropdown-btn" onClick={toggleSelectBox}>{selectedResort}<IoMdArrowDropdown className="boardWrite-icon"/></div>
                    {showSelectBox && <div className="dropdown-content">
                        {
                            resortData.map(resort => (
                                <div key={resort.id} id={resort.name} className="dropdown-item" onClick={handleResortClick}>{resort.name}</div>
                            ))
                        }
                        
                    </div>}
                </div>
            </SelectBox>
            <Title>
                <input 
                    className="boardWrite-title"
                    type="text"
                    placeholder="제목"
                    ref={titleInput}
                    name="title"
                    value={state.title || ""}
                    onChange={handleInputChange}
                    onClick={resetError} />
                <TitleError className="boardWrite-error">{error.title ? error.title : null}</TitleError>
            </Title>
        </Top>
        <Content>
            <textarea 
                className="boardWrite-content"
                placeholder="내용을 입력하세요" 
                ref={contentInput} 
                name="content"
                value={state.content}
                onChange={handleInputChange}
                onClick={resetError}>
            </textarea>
            <ContentError className="boardWrite-error">{error.content ? error.content : null}</ContentError>
        </Content>
    </Wrapper>
    )
}

const Wrapper = styled.form`
    margin: 30px 20px;

    form {
        width: 100%;
        display: grid;
        //flex-direction: column;
        align-items: center;
    }


    .boardWrite-top{
        text-align: center;
        font-family: nanum-square-bold;
    }
    .boardWrite-error{
        font-size: 12px;
        color: #CD5C5C;
    }
`
const Top = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 8px;
`
const SelectBox = styled.div`
    .dropdown {
        position: relative;
        margin: 10px;
        margin-left: 0;
        width: 117px;
    }

    .dropdown-btn{
        background: #fff;
        //background-color: var(--background-color);
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        padding: 10px 13px;
        font-size: 14px;
        //font-weight: bold;
        color: gray;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: 8px;
    }

    .dropdown-content{
        position: absolute;
        padding: 12px;
        margin-top: 5px;
        margin-left: 0;
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        font-weight: bold;
        font-size: 12px;
        color: #333;
        border-radius: 8px;
        width: 90%;
    }

    .dropdown-item{
        padding: 7px;
    }

    .dropdown-item:hover{
        background: #fcfcfc;
    }
    .boardWrite-icon{
        padding-left: 4px;
    }
`

const Title = styled.div`
    display: flex;
    flex-direction: column;
    //justify-content: space-between;
    align-items: center;
    .boardWrite-title{
        width: 85%;
        border: none;
        border-bottom: 1px solid var(--button-color);
        padding: 10px 13px;
        margin: 0 10px;
        background-color: var(--background-color);
        align-self: end;
    }
    .boardWrite-title:focus{
        outline: none;
        border-color: #6B89A5;
    }
`
const TitleError = styled.div`
padding-top: 10px;
`
const Content = styled.div`
    margin-top: 0;
    width: 100%;
    display:flex;
    flex-direction: column;
    align-items: center;
    textarea {
        width: 100%;
        height: 200px;
        padding-left: 10px;
        border: none;
        margin: 10px;
        background-color: var(--background-color);
    }
    textarea:focus{
        outline: none;
        border-color: #6B89A5;
    }
`
const ContentError = styled.div`
align-self: start;
`
const Buttons = styled.div`
    display: flex;
    align-items: center;

    button{
        border-radius: 10px;
        border: 1px solid black;
        background-color: var(--background-color);
    }

    .boardWrite-tempSave{
        background-color: #FAFAFA;
        border: 1px solid #6B89A5;
        color: #6B89A5;
    }
    .boardWrite-save{
        background-color: var(--background-color);
        border: none;
        color: var(--button-color);
        font-family: nanum-square-bold;
        font-size: 15px;
    }
`