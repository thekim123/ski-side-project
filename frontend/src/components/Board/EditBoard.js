import React, { useState, useRef, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { updatePost, getSinglePost } from '../../action/board';
import styled from 'styled-components'
import { IoMdArrowDropdown } from 'react-icons/io';

export function EditBoard() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const resorts = useSelector(state => state.resort.resorts);
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
                title: enteredTitle,
                content: enteredContent,
                resortName: selectedResort,
                //create_dt: originalPost.create_dt,
            }
            dispatch(updatePost(post, id));
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
            setSelectedResort(originalPost.resortName);
        }
    }, [originalPost]);

    return (
    <Wrapper>
        <div className="boardWrite-top">글 작성</div>
        <form onSubmit={handleSubmit}>
        <Top>
            <SelectBox>
                <div className="dropdown">
                    <div className="dropdown-btn" onClick={toggleSelectBox}>{selectedResort}<IoMdArrowDropdown className="boardWrite-icon"/></div>
                    {showSelectBox && <div className="dropdown-content">
                        {
                            resorts.map(resort => (
                                <div key={resort.id} id={resort.name} className="dropdown-item" onClick={handleResortClick}>{resort.name}</div>
                            ))
                        }
                        
                    </div>}
                </div>
            </SelectBox>
            {/* <SelectBox toggle={toggleSelectBox} state={showSelectBox} list={resorts} /> */}
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
        <Buttons>
            <button className="boardWrite-tempSave">임시 저장</button>
            <button className="boardWrite-save">저장</button>
        </Buttons>
        </form>
    </Wrapper>
    )
}

const Wrapper = styled.div`
    margin-top: 30px;

    form {
        width: 100%;
        display:flex;
        flex-direction: column;
        align-items: center;
    }


    .boardWrite-top{
        text-align: center;
        font-weight: bold;
        margin-bottom: 10px;
    }
    .boardWrite-error{
        font-size: 12px;
        color: #CD5C5C;
    }
`
const Top = styled.div`
    display: flex;

`
const SelectBox = styled.div`
    .dropdown {
        position: relative;
        margin: 10px;
    }

    .dropdown-btn{
        background: #fff;
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
    align-items: center;
    .boardWrite-title{
        width: 85%;
        border: none;
        border-bottom: 1px solid gray;
        padding: 10px 13px;
        margin: 10px;
    }
    .boardWrite-title:focus{
        outline: none;
        border-color: #6B89A5;
    }
`
const TitleError = styled.div`

`
const Content = styled.div`
    margin: 10px;
    width: 100%;
    display:flex;
    flex-direction: column;
    align-items: center;
    textarea {
        width: 70%;
        height: 200px;
        padding: 10px;
        border: 1px solid gray;
        margin: 10px;
    }
    textarea:focus{
        outline: none;
        border-color: #6B89A5;
    }
`
const ContentError = styled.div`

`
const Buttons = styled.div`
    display: flex;
    align-items: center;

    button{
        padding: 10px;
        border-radius: 15px;
        border: 1px solid black;
        margin: 5px;
    }

    .boardWrite-tempSave{
        background-color: #FAFAFA;
        border: 1px solid #6B89A5;
        color: #6B89A5;
    }
    .boardWrite-save{
        background-color: #6B89A5;
        border: 1px solid #FAFAFA;
        color: #FAFAFA;
    }
`