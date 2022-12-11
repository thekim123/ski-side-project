import React, { useState, useRef, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { addPost } from '../../action/board';
import styled from 'styled-components'
import { IoMdArrowDropdown } from 'react-icons/io';
import resorts from '../../data/resort.json'
import shortid from 'shortid';

export function BoardWrite() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    // const resorts = useSelector(state => state.resort.resorts);
    const [showSelectBox, setShowSelectBox] = useState(false);
    const [selectedResort, setSelectedResort] = useState("스키장 선택");
    const titleInput = useRef();
    const contentInput = useRef();
    const imgRef = useRef();
    const [img, setImg] = useState('');
    const resortData = resorts.filter(resort => resort.id !== null);
    //const resortsData = resortData.splice(0, 0, {id: 100, name: "전체"});
    const [error, setError] = useState({
        title: "",
        content: ""
    });

    const toggleSelectBox = () => {
        setShowSelectBox(!showSelectBox);
    };

    const handleResortClick = (e) => {
        console.log(e.target.id);
        setSelectedResort(e.target.id);
        setShowSelectBox(false);
    }

    const onUploadImg = e => {
        console.log(imgRef.current);
        console.log(e.target.files);
        setImg(e.target.files[0])
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

    const handleSubmit = (e) => {
        e.preventDefault();
        const enteredTitle = titleInput.current.value;
        const enteredContent = contentInput.current.value;
        if (!validateInput(enteredTitle, enteredContent)){
            return;
        } else {
            let postedResort = selectedResort === ("스키장 선택" || "전체") ? null : selectedResort;
            //console.log(postedResort)
            //let sendImg = img !== '' ? img : null;
            //const formData = new FormData()
            //formData.append('image', sendImg);
            const post = {
                title: enteredTitle,
                content: enteredContent,
                resortName: postedResort,
                //formData,
                //create_dt: new Date(),
                // 여기서부턴 백엔드와 연결 후 지울 예정
                //cnt: 0,
                //like: 0,
                //comments: [],
            }
            console.log(post);
            dispatch(addPost(post));
            navigate("/board");
        }
    }

    return (
    <Wrapper onSubmit={handleSubmit}>
        <MostTop>
        <div className="boardWrite-top">글 작성</div>
        <Buttons>
            {/* <button className="boardWrite-tempSave">임시 저장</button> */}
            <button className="boardWrite-save">저장</button>
        </Buttons>
        </MostTop>
        <Top>
            <SelectBox>
                <div className="dropdown">
                    <div className="dropdown-btn" onClick={toggleSelectBox}>{selectedResort}<IoMdArrowDropdown className="boardWrite-icon"/></div>
                    {showSelectBox && <div className="dropdown-content">
                        {
                            resortData.map(resort => (
                                resort.id ?
                                <div key={resort.id} id={resort.name} className="dropdown-item" onClick={handleResortClick}>{resort.name}</div>
                                : null
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
                    onClick={resetError} />
                <TitleError className="boardWrite-error">{error.title ? error.title : null}</TitleError>
            </Title>
        </Top>
        <Content>
            <textarea 
                className="boardWrite-content"
                placeholder="내용을 입력하세요" 
                ref={contentInput} 
                onClick={resetError}>
            </textarea>
            <ContentError className="boardWrite-error">{error.content ? error.content : null}</ContentError>
        </Content>
        <ImgBox>
            {/* <input type="file" accept="image/*" ref={imgRef} onChange={onUploadImg} /> */}
        </ImgBox>
    </Wrapper>
    )
}
const ImgBox = styled.div`

`

const Wrapper = styled.form`
    margin-top: 20px;
    display: grid;
    justify-items: center;
    form {
        width: 100%;
        display:flex;
        flex-direction: column;
        align-items: center;
    }


    .boardWrite-top{
        text-align: center;
        align-self: center;
        font-weight: bold;
    }
    .boardWrite-error{
        font-size: 12px;
        color: #CD5C5C;
    }
`
const MostTop = styled.div`
display: flex;
justify-content: space-between;
align-items: center;
width: 90%;
font-family: nanum-square-bold;
`
const Top = styled.div`
    display: grid;
    width: 90%;
    grid-template-columns: 130px 1fr;
`
const SelectBox = styled.div`
    .dropdown {
        position: relative;
        margin: 10px;
        margin-left: 0;
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
    input{
        background-color: var(--background-color);
    }
    .boardWrite-title{
        width: 85%;
        border: none;
        border-bottom: 1px solid var(--button-color);
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
        width: 90%;
        height: 300px;
        padding: 10px;
        border: none;
        border-radius: 10px;
        background-color: var(--background-color);
    }
    textarea:focus{
        outline: none;
        border-color: #6B89A5;
    }
`
const ContentError = styled.div`
align-self: start;
margin-left: 20px;
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
        border: 1px solid var(--button-color);
        color: var(--button-color);
    }
    .boardWrite-save{
        background-color: var(--background-color);
        border: none;
        color: var(--button-color);
        font-size: 14px;
        font-family: nanum-square-bold;
    }
`