import React, { useState, useRef } from 'react'
import { SelectBox } from '../common/SelectBox';
import styled from 'styled-components'
import resorts from '../../data/resort.json'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import { ko } from 'date-fns/esm/locale'
import setHours from "date-fns/setHours";
import setMinutes from "date-fns/setMinutes";
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { addTayo } from '../../action/tayo';

export function TayoWrite() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const type = ["스키", "보드"];
    const resort_name = resorts.map(resort => resort.name);
    const age = ["연령 무관", "10대", "20대", "30대", "40대", "50대", "60대", "70대", "80대", "90대"]
    const [selectedType, setSelectedType] = useState("--");
    const [selectedResort, setSelectedResort] = useState("--");
    const [startDate, setStartDate] = useState(new Date());
    const [showDate, setShowDate] = useState(false);
    const [startTime, setStartTime] = useState(null);
    const [endTime, setEndTime] = useState(null);
    const cntInput = useRef();
    const [cntBtn, setCntBtn] = useState(false);
    const [selectedAge, setSelectedAge] = useState("--");
    const titleInput = useRef();
    const contentInput = useRef();
    const [error, setError] = useState({
        type: "",
        resort: "",
        date: "",
        time: "",
        cnt: "",
        age: "",
        title: "",
        content: "",
    })
    const [isDone, setIsDone] = useState({
        type: false,
        resort: false,
        date: false,
        time: false,
        cnt: false,
        age: false,
        title: false,
        content: false
    })
    const toggleDate = e => {
        setError({...error, date: ""})
        setShowDate(!showDate);
    }

    const onSelect = (time) => {
        setStartTime(time);
        setEndTime(null);
        setError({...error, time: null});
    }

    // set Selection & reset SelectBox error
    const reflectType = (selection) => {
        if (type.indexOf(selection) !== -1) {setSelectedType(selection); setError({...error, type: ""}); setIsDone({...isDone, type: true})}
    }
    const reflectResort = (selection) => {
        if (resort_name.indexOf(selection) !== -1) {setSelectedResort(selection); setError({...error, resort: ""}); setIsDone({...isDone, resort: true})}
    }
    const reflectAge = (selection) => {
        if (age.indexOf(selection) !== -1) {setSelectedAge(selection); setError({...error, age: ""}); setIsDone({...isDone, age: true})}
    }

    // reset Input error
    const resetCntError = (e) => {
        setError({...error, cnt: ""})
    }
    const resetTitleError = (e) => {
        setError({...error, title: ""})
    }
    const resetContentError = (e) => {
        setError({...error, content: ""})
    }

    // 입력 유효성 검사
    const validateInput = (title, content, cnt) => {
        let local_error = {
            type: "",
            resort: "",
            date: "",
            time: "",
            cnt: "",
            age: "",
            title: "",
            content: "",
        }
        
        // 제목, 홍보 문구, 최대 인원
        if (title.trim() === '') {
            local_error.title = "제목을 입력하세요.";
        } else if (title.length > 50) {
            local_error.title = "제목은 50자 이하여야 합니다."
        } 
        if (content.trim() === '') {
            local_error.content = "홍보 문구를 입력하세요."
        } else if (content.length > 1000) {
            local_error.content = "홍보 문구는 1000자 이하여야 합니다."
        }
        if (!cntBtn) {
            if (cnt.trim() === '') local_error.cnt = "최대 인원을 입력하세요.";
            else {
                const digitRegex = /\d+/g;
                let replacedCnt = cnt.replace(/[0-9]/g, '');
                //console.log(replacedCnt.search(digitRegex) > -1)
                //console.log('hmm'.search(digitRegex) > -1);
                if (replacedCnt.length !== 0) local_error.cnt = "숫자만 입력 가능합니다.";
            }
        }

        //날짜, 시간
        if (!startDate) local_error.date = "날짜를 선택하세요."
        if (!startTime) local_error.time = "시작 시간을 선택하세요."
        else if (!endTime) local_error.time = "종료 시간을 선택하세요."

        if (selectedType === '--') local_error.type = "스키/보드를 선택하세요."
        if (selectedResort === '--') local_error.resort = "스키장을 선택하세요.";
        if (selectedAge === '--') local_error.age = "연령대를 선택하세요.";

        setError({...local_error});
        for (let c in local_error) {
            if (local_error[c]) return false;
        }
        return true;
    }

    // 제출 시
    const handleSubmit = e => {
        e.preventDefault();

        const enteredTitle = titleInput.current.value;
        const enteredContent = contentInput.current.value;
        const enteredCnt = cntInput.current.value;
        if (!validateInput(enteredTitle, enteredContent, enteredCnt)) return;

        //body에 넣을 data
        let cnt = cntBtn ? "제한 없음" : enteredCnt;
        const tayo = {
            selectedType,
            selectedResort,
            startDate,
            startTime,
            endTime,
            cnt,
            selectedAge,
            enteredTitle,
            enteredContent
        }
        console.log(tayo);
        //dispatch(addTayo(tayo));
        //navigate("/tayo");
    }

    //선택된 날짜가 오늘인지 확인
    function isDateToday() {
        const todayDate = new Date();
        if (startDate.getDate() === todayDate.getDate() &&
            startDate.getMonth() === todayDate.getMonth() &&
            startDate.getFullYear() === todayDate.getFullYear()) 
            return true;
        else return false;
    }

    // calculate hour, minutes
    const calMinute = () => {
        let curMinute = new Date().getMinutes();
        if (curMinute < 30) return 30;
        else return 0;
    }
    //시작 시간 조정. 날짜가 오늘일 경우에만 해당하고, 그렇지 않으면 9시
    const calMinHour = () => {
        /*
        if (isDateToday()) {
            if (new Date().getHours() < 22) {
                let curHour = new Date().getHours();
                if (curHour < 9) return 9;
                let curMin = new Date().getMinutes();
                if (curMin < 30) return curHour;
                else return curHour+1;
            }
            return 24;
        }
        else return 9;*/
        if (isDateToday()) {
        let curHour = new Date().getHours();
        let curMin = new Date().getMinutes();
        if (curMin < 30) return curHour;
            else return curHour+1;
        }
        else return 0;
    }
    const calMaxHour = () => {
        //선택된 날짜가 오늘이고 밤 10시를 넘었다면 모두 불가능.
        /*
        if (isDateToday() && new Date().getHours >= 22)
            return 24; 
        else return 21;*/
        return 24;
    }

    const setCntDone = e => {
        if (e.target.value !== "") {
            setIsDone({...isDone, cnt: true})
        } else setIsDone({...isDone, cnt: false})
    }

    return (
    <Wrapper>
        <Title><div className="clubReg-top">같이타요 등록</div></Title>
        <form onSubmit={handleSubmit}>
            <SelectBox list={type} label="스키/보드" func={reflectType} state={selectedType} />
            <Error><Dummy></Dummy><div>{error.type ? error.type : null}</div></Error>

            {isDone.type && 
            <SelectBox list={resort_name} label="활동 스키장" func={reflectResort} state={selectedResort} />}
            <Error><Dummy></Dummy><div>{error.resort ? error.resort : null}</div></Error>

            {isDone.resort &&
            <DatePick>
                <label>날짜</label>
                <span onClick={toggleDate}><SDatePicker 
                    className="tayoWrite-date"
                    selected={startDate} 
                    dateFormat="yyyy-MM-dd"
                    minDate={new Date()}
                    locale={ko}
                    onChange={date => setStartDate(date)} 
                    
                    /></span>
            </DatePick>}
            <Error><Dummy></Dummy><div>{error.date ? error.date : null}</div></Error>

            {isDone.resort && 
            <DatePick>
                <label>시간</label>
                <div>
                <SDatePicker
                    selected={startTime}
                    onChange={onSelect}
                    locale={ko}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={30}
                    //minTime={setHours(setMinutes(new Date(), calMinute()), calMinHour())}
                    minTime={setHours(setMinutes(new Date(), calMinute()), calMinHour())}
                    maxTime={setHours(setMinutes(new Date(), 30), 23)}
                    dateFormat="aa h:mm 부터"
                    placeholderText='시작 시간'
                    className='tayoWrite-startT'
                />
                {startTime ? <SDatePicker
                    selected={endTime}
                    onChange={time => {setEndTime(time); setIsDone({...isDone, time: true})}}
                    locale={ko}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={30}
                    minTime={startTime}
                    maxTime={setHours(setMinutes(new Date(), 30), 23)}
                    dateFormat="aa h:mm 까지"
                    placeholderText='종료 시간'
                    className='tayoWrite-endT'
                /> : 
                    <SDatePicker 
                    placeholderText='종료 시간'
                    disabled
                    className='tayoWrite-endT'
                />}
                </div>
            </DatePick>}
            <Error><Dummy></Dummy><div>{error.time ? error.time : null}</div></Error>
            
            {isDone.time &&
            <Input>
                <label>최대 인원</label>
                <input 
                    type="text" 
                    ref={cntInput} 
                    onChange={setCntDone}
                    onClick={resetCntError} 
                    placeholder="입력 예시) 5"
                    disabled={cntBtn ? true : false}
                    />
                <CheckBox>
                    <Dummy></Dummy>
                    <input
                        type="checkbox"
                        onChange={e => {console.log(cntBtn); setCntBtn(!cntBtn); }}
                    />
                    <label>제한 없음</label>
                </CheckBox>
            </Input>}
            <Error><Dummy></Dummy><div>{error.cnt ? error.cnt : null}</div></Error>

            {(cntBtn || isDone.cnt) &&
            <SelectBox list={age} label="연령" func={reflectAge} state={selectedAge} />}
            <Error><Dummy></Dummy><div>{error.age ? error.age : null}</div></Error>

            {isDone.age &&
            <Input><label>제목</label><input type="text" ref={titleInput} onClick={resetTitleError} /></Input>}
            <Error><Dummy></Dummy><div>{error.title ? error.title : null}</div></Error>

            {isDone.age &&
            <Input><label>홍보 문구</label><textarea type="text" ref={contentInput} onClick={resetContentError} /></Input>}
            <Error><Dummy></Dummy><div>{error.content ? error.content : null}</div></Error>

            {isDone.age &&
            <BtnWrap><Button>개설하기</Button></BtnWrap>}
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
const Error = styled.div`
font-size: 12px;
color: #CD5C5C;
display: grid;
grid-template-columns: 120px 1fr;
`
const Dummy = styled.div`

`
const TimePick = styled.div`
display: grid;
align-items: center;
grid-template-columns: 110px 1fr 1fr;
`
const DatePick = styled.div`
display: grid;
align-items: center;
grid-template-columns: 110px 1fr;
margin: 10px;
label {
    text-align: center;
    padding-right: 10px;
}
.tayoWrite-date:focus, .tayoWrite-startT:focus, .tayoWrite-endT:focus{
    outline: none;
}
.react-datepicker__triangle{
    display: none;
}
.react-datepicker-popper {
    padding-top: 13px;
}
.react-datepicker {
    border: none;
    box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
    border-radius: 18px;
}
.react-datepicker__month-container {
    border: none;
}
.react-datepicker__header {
    background-color: #FAFAFA;
    border: none;
    border-radius: 18px;
}
.react-datepicker__current-month{
    padding: 8px 0;
    font-weight: 500;
}
.react-datepicker__navigation{
    padding-top:18px;
}
.react-datepicker__day--selected{
    border-radius: 40%;
    background-color: #6B89A5;
}

.tayoWrite-startT, .tayoWrite-endT {
    margin-top: 10px;
}
.tayoWrite-endT {
    margin-bottom: 10px;
}
`
const SDatePicker = styled(DatePicker)`
border: none;
border-radius: 8px;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
padding: 10px 13px;
font-size: 14px;
color: gray;
height: 18.7px;
`
const SDatePicker_T = styled(DatePicker)`
border: none;
border-radius: 8px;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
padding: 10px 13px;
font-size: 14px;
color: gray;
height: 18.7px;
`

const Input = styled.div`
    display: grid;
    align-items: center;
    grid-template-columns: 110px 1fr;
    margin: 10px;
    margin-top: 10px;
    label {
        text-align: center;
        padding-right: 10px;
    }
    input, textarea {
        padding: 12px;
        margin-top: 5px;
        margin-left: 0;
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        border-radius: 8px;
        width: 90%;
        border: none;
    }
    textarea{
        height: 150px;
    }
    input:focus{
        outline: none;
    }
`
const CheckBox = styled.div`
display: grid;
grid-template-columns: 110px 20px 1fr;
padding-top: 7px;
input {
    width: 15px;
    height: 15px;
    box-shadow: none;
}
label {
    width: 200px;
    text-align: left;
    font-size: 12px;
    padding-top: 3px;
}
`
const BtnWrap = styled.div`
display: flex;
justify-content: center;
margin: 30px;
`
const Button = styled.button`
background-color:var(--button-color);
color: #FAFAFA;
padding: 13px 20px;
border-radius: 19px;
border: none;
`