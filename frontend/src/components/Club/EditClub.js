import React, { useEffect, useRef, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { asyncEditClub, editClub, getSingleClub, regClub } from '../../action/club';
import styled from 'styled-components'
import { SelectBox } from '../common/SelectBox';
import resorts from '../../data/resort.json'

export function EditClub() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const nameInput = useRef();
    const contentInput = useRef();
    const [selectedResort, setSelectedResort] = useState("--");
    const [selectedAge, setSelectedAge] = useState("--");
    const [selectedGender, setSelectedGender] = useState("--");
    const [selectedRoom, setSelectedRoom] = useState("--");
    //const resorts = useSelector(state => state.resort.resorts);
    const resortData = resorts.filter(resort => resort.id !== null);
    const resortName = resortData.map(resort => resort.name);
    const age = ["제한 없음", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대"]
    const ageData = ["ANY", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY"]
    const gender = ["남", "여", "성별 무관"];
    const genderData = ["MEN", "WOMEN", "NO"];
    const genderBack = ["MEN", "WOMEN", "NO"];
    const roomType = ["오픈방", "비밀방"];
    const roomData = ["Y", "N"];
    const [error, setError] = useState({
        name: "",
        content: "",
        resort: "",
        age: "",
        gender: "",
        room: ""
    })

    const {id} = useParams();
    const original = useSelector(state => state.club.club);
    const [newInput, setNewInput] = useState({
        name: "",
        memo: "",
    })
    const handleInputChange = e => {
        const {name, value} = e.target;
        setNewInput({...newInput, [name]: value})
    }

    const reflectSelection = (selection) => {
        if (resortName.indexOf(selection) !== -1) {setSelectedResort(selection); setError({...error, resort: ""})}
        else if (age.indexOf(selection) !== -1) {setSelectedAge(selection); setError({...error, age: ""})}
        else if (gender.indexOf(selection) !== -1) {setSelectedGender(selection); setError({...error, gender: ""})}
        else if (roomType.indexOf(selection) !== -1) {setSelectedRoom(selection); setError({...error, room: ""})}
    }

    const resetError = (e) => {
        if (e.target.className === 'clubReg-name') {
            setError({...error, name: ""});
        } else if (e.target.className === 'clubReg-content') {
            setError({...error, content: ""});
        }
    }

    //SelectBox를 공용 컴포넌트로 전환하기.
    //props로는 toggleSelectBox(), showSelectBox state
    const validateInput = (title, content) => {
        let t_error = "";
        let c_error = "";
        let resort_error="";
        let age_error = "";
        let gender_error = "";
        let room_error = "";
        if (title.trim() === '') {
            t_error = "동호회 명을 입력하세요.";
        } else if (title.length > 20) {
            t_error = "동호회 명은 20자 이하여야 합니다."
        } 
        if (content.trim() === '') {
            c_error = "홍보 문구를 입력하세요."
        } else if (content.length > 1000) {
            c_error = "홍보 문구는 1000자 이하여야 합니다."
        }
        if (selectedResort === '--') resort_error = "스키장을 선택해주세요.";
        if (selectedAge === '--') age_error = "연령대를 선택해주세요.";
        if (selectedGender === '--') gender_error = "성별을 선택해주세요.";
        if (selectedRoom === '--') room_error = "방 종류를 선택해주세요.";

        if (!t_error && !c_error) return true;
        else {
            setError({
                name: t_error,
                content: c_error,
                resort: resort_error,
                age: age_error,
                gender: gender_error,
                room: room_error
            })
            return false;
        }
    }

    const handleSubmit = async (e) => {
        try {
        e.preventDefault();
        const enteredName = nameInput.current.value;
        const enteredContent = contentInput.current.value;
        if (!validateInput(enteredName, enteredContent)){
            return;
        }
        let resortId = resortData.filter(resort => resort.name === selectedResort)[0].id;
        let openYn = selectedRoom === "오픈방" ? "Y" : "N";
        let ageGrp = age.indexOf(selectedAge);
        let genderIdx = gender.indexOf(selectedGender);
        let genderFix = genderBack[genderIdx];
        //스키장 넣기
        const club = {
            id: id,
            clubNm: enteredName,
            resortId: resortId,
            gender: genderFix,
            ageGrp: ageGrp,
            openYn: openYn,
            memo: enteredContent,
        }
        console.log(club);
        //이것도 마찬가지로 edit이 완료되기를 wait했다가 navigate
        const done = await dispatch(asyncEditClub(club)).unwrap();
        navigate(`/club/detail/${id}`);
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        dispatch(getSingleClub(id))
    }, [id]);

    useEffect(() => {
        if (original) {
            console.log(original);
            setSelectedResort(resortData.find(resort => resort.id === original.resortId).name);
            setSelectedAge(age[ageData.indexOf(original.ageGrp)]);
            setSelectedGender(gender[genderData.indexOf(original.gender)]);
            setSelectedRoom(roomType[roomData.indexOf(original.openYn)]);
            setNewInput({...newInput, name: original.clubNm, memo: original.memo});
        }
    }, [original])

    return (
    <Wrapper>
        <Title><div className="clubReg-top">동호회 수정</div></Title>
        <form onSubmit={handleSubmit}> 
            <Input><label>동호회 명</label>
                <input 
                    name="name"
                    className="clubReg-name" 
                    type="text" ref={nameInput} 
                    value={newInput.name || ""} 
                    onClick={resetError} 
                    onChange={handleInputChange}
            /></Input>
            <Error><Dummy></Dummy><div>{error.name ? error.name : null}</div></Error>
            
            <SelectBox list={resortName} label="활동 스키장" func={reflectSelection} state={selectedResort} />
            <Error><Dummy></Dummy><div>{error.resort ? error.resort : null}</div></Error>
            
            <SelectBox list={age} label="연령" func={reflectSelection} state={selectedAge} />
            <Error><Dummy></Dummy><div>{error.age ? error.age : null}</div></Error>
            
            <SelectBox list={gender} label="성별" func={reflectSelection} state={selectedGender} />
            <Error><Dummy></Dummy><div>{error.gender ? error.gender : null}</div></Error>
            
            <SelectBox list={roomType} label="오픈방/비밀방" func={reflectSelection} state={selectedRoom} />
            <Error><Dummy></Dummy><div>{error.room ? error.room : null}</div></Error>
            
            <Input><label>홍보 문구</label>
                <textarea 
                    name="memo"
                    className="clubReg-content" 
                    ref={contentInput} 
                    value={newInput.memo || ""} 
                    onClick={resetError}
                    onChange={handleInputChange}>
                </textarea></Input>
            <Error><Dummy></Dummy><div>{error.content ? error.content : null}</div></Error>
            
            <BtnWrap><Button>수정하기</Button></BtnWrap>
        </form>
    </Wrapper>
    )
}

const Wrapper = styled.div`
    padding: 20px;
    form {
        margin-top: 30px;
    }
`
const Title = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 20px;
    font-weight: bold;
`
const Input = styled.div`
    display: grid;
    align-items: center;
    grid-template-columns: 110px 1fr;
    margin: 10px;
    label {
        text-align: center;
        padding-right: 10px;
    }
    input, textarea {
        padding: 12px;
        margin-top: 5px;
        margin-left: 0;
        background: #fff;
        //box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        border-radius: 8px;
        width: 90%;
        border: 1px solid #CCCCCC;
    }
    textarea{
        height: 150px;
    }
`
const Error = styled.div`
font-size: 12px;
color: #CD5C5C;
display: grid;
grid-template-columns: 120px 1fr;
`
const Dummy = styled.div`

`
const BtnWrap = styled.div`
display: flex;
justify-content: center;
margin: 50px;
`
const Button = styled.button`
background-color:var(--button-color);
color: #FAFAFA;
padding: 13px 20px;
border-radius: 19px;
border: none;
`