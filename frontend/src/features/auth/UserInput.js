import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { asyncIsDuplicated, asyncUpdateUser } from '../../action/auth';
import { IoMdArrowDropdown } from 'react-icons/io';
import shortid from 'shortid'

export function UserInput() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector(state => state.auth.user);
    const inputLabel = ["앱에서 사용할 닉네임을 입력해주세요.", "성별을 선택해주세요.", "나이를 입력해주세요."];
    const [answer, setAnswer] = useState(["", "", ""]);
    const [idx, setIdx] = useState(0);
    const [errMsg, setErrMsg] = useState('');
    const genderList = ["남", "여"];
    const [selectedGender, setSelectedGender] = useState("남");
    const [showSelectBox, setShowSelectBox] = useState(false);
    const toggleSelectBox = () => {
        setShowSelectBox(!showSelectBox);
    };
    const handleItemClick = (e) => {
        setShowSelectBox(false);
        setSelectedGender(e.target.id);
    }

    const onChange = e => {
        const {name, value} = e.target;
        let copy = [...answer];
        copy[name] = value;
        setAnswer(copy);
    }
    const onSubmit = e => {
        e.preventDefault();
        increaseIdx();
    }

    const reflectSelection = (selection) => {
        setSelectedGender(selection);
    }
    
    const resetErr = e => {
        setErrMsg("");
    }

    const decreaseIdx = e => {
        setIdx(idx - 1);
    }

    const increaseIdx = async e => {
        if (idx === 0) {
            if (answer[0].trim().length < 2) setErrMsg("닉네임은 공백 제외 2자 이상이어야 합니다.");
            else if (answer[0].trim().length >= 10) setErrMsg("닉네임은 공백 제외 10자 미만이어야 합니다.");
            else {
                let isDuplicated = await dispatch(asyncIsDuplicated(answer[0])).unwrap();
                if (isDuplicated) setErrMsg("사용 중인 닉네임입니다.");
                else {
                    setIdx(idx + 1);
                    resetErr();
                }
            }
        }
        else if (idx === 2) {
            let ageInput = answer[2].replace(/[0-9]/g, '');
            if (ageInput.length !== 0) {
                setErrMsg("숫자만 입력 가능합니다.");
            } else if (answer[2] < 20 || answer[2] > 80) {
                setErrMsg("20세부터 80세까지 이용 가능합니다.");
            } else {
                console.log(answer);
                let data = {
                    username: user.username,
                    password: "skiproject",
                    nickname: answer[0],
                    agreement: true,
                    gender: selectedGender === '남' ? 'MEN' : 'WOMEN',
                    age: answer[2]
                }
                resetErr();
                dispatch(asyncUpdateUser(data));
                navigate('/');
            }
        } else {
            setIdx(idx + 1);
        }

    }

    useEffect(() => {
        if (user) {
            if (user.nickname) {
                navigate("/");
            }
        }
    }, [user])

    return (
    <Container>
        <InputLabel>{inputLabel[idx]}</InputLabel>
        <form onSubmit={onSubmit}>
        {idx === 0 ? <Dummy> </Dummy> : <PrevBtn className="user-button" onClick={decreaseIdx}><div>이전</div></PrevBtn>}
        {idx === 1 ? 
        <Wrapper>
        <div className="dropdown">
            <div className="dropdown-btn" onClick={toggleSelectBox}>{selectedGender}<IoMdArrowDropdown className="boardWrite-icon"/></div>
            {showSelectBox && <div className="dropdown-content">
                {
                    genderList.map(item => (
                        <div key={shortid.generate()} id={item} className="dropdown-item" onClick={handleItemClick}>{item}</div>
                    ))
                }
            </div>}
        </div>
        </Wrapper>
        : <Input 
            className="user-input"
            type="text"
            name={idx}
            onChange={onChange}
            onClick={resetErr}
            value={answer[idx]}
            />}
        {idx === 1 ? <NextBtnB className="user-button" onClick={increaseIdx}><div>다음</div></NextBtnB>
        : <NextBtn className="user-button" onClick={increaseIdx}><div>{idx === 2 ? "제출" : "다음"}</div></NextBtn>}
        </form>
        <Error>{errMsg}</Error>
    </Container>
    )
}

const Container = styled.div`
display: grid;
align-items: center;
justify-items: center;
margin-top: 170px;

form {
    display: flex;
    width: 300px;
    align-items: center;
    justify-items: center;
}
.user-input{
    width: 130px;
    border: none;
    border-bottom: 1px solid var(--button-color);
    background-color: var(--background-color);
    padding: 10px 13px;
    margin: 10px;
}
.user-input:focus{
    outline: none;
    border-color: #6B89A5;
}
.user-button{
    text-align: center;
    background-color: var(--button-color);
    color: #FAFAFA;
    border-radius: 8px;
    padding: 4px;
    width: 40px;
    height: 30px;
    display:grid;
    align-items: center;
    justify-items: center;
}
`
const Dummy = styled.div`
width: 30px;
`
const InputLabel = styled.div`
font-size: 16px;
`
const Input = styled.input`

`
const NextBtn = styled.div`
margin-left: 8px;
`
const NextBtnB = styled.div`
margin-left: 48px;
`
const PrevBtn = styled.div`
margin-right: 8px;
`
const Error = styled.div`
font-size: 14px;
color: red;
`

const Wrapper = styled.div`
display: grid;
align-items: center;
width: 130px;
//grid-template-columns: 110px 1fr;
    .dropdown {
        //position: relative;
        margin: 10px;
    }

    .dropdown-btn{
        width: 130px;
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        padding: 10px 13px;
        font-size: 14px;
        //font-weight: bold;
        color: black;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: 8px;
    }

    .dropdown-content{
        width: 130px;
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
        //width: 90%;
        z-index: 99;
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