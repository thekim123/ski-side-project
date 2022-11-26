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

export function CarPoolWrite() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const route = ["도착지가 스키장", "출발지가 스키장"];
    const resortsData = resorts.filter(resort => resort.id !== null);
    const resortName = resortsData.map(resort => resort.name);
    //const city = ["서울", "경기", "인천", "부산", "대구", "대전"]
    const space = ["적어요", "보통이에요", "많아요"];
    const smoking = ["금연 차량", "흡연 차량"];
    const [date, setDate] = useState(new Date());
    const [showDate, setShowDate] = useState(false);
    const [selectedRoute, setSelectedRoute] = useState("--");
    const [selectedResort, setSelectedResort] = useState("스키장 선택");
    const startEndInput = useRef();
    const placeInput = useRef();
    const [startTime, setStartTime] = useState(null);
    const [selectedSmoking, setSelectedSmoking] = useState("--");
    const cntInput = useRef();
    const contentInput = useRef();
    const [error, setError] = useState({
        date:"",
        route: "",
        resort: "",
        startEnd: "",
        place: "",
        time: "",
        space: "",
        smoking: "",
        cnt: "",
        content: "",
    })
    const [isDone, setIsDone] = useState({
        startEnd: false,
        time: false,
        smoking: false,
        cnt: false,
    })

    const toggleDate = e => {
        setError({...error, date: ""})
        setShowDate(!showDate);
    }
    const onSelect = (time) => {
        setStartTime(time);
        setError({...error, time: null});
        setIsDone({...isDone, time: true});
    }

    const reflectRoute = (selection) => {
        if (route.indexOf(selection) !== -1) {setSelectedRoute(selection); setError({...error, route: ""});}
    }
    const reflectResort = (selection) => {
        if (resortName.indexOf(selection) !== -1) {
            setSelectedResort(selection); 
            setError({...error, resort: ""});
            if (startEndInput.current.value !== "") {
                setIsDone({...isDone, startEnd: true})
            }
        }
    }
    const reflectSmoke = (selection) => {
        if (smoking.indexOf(selection) !== -1) {
            setSelectedSmoking(selection);
            setError({...error, smoking: ""})
            setIsDone({...isDone, smoking: true})
        }
    }

    // reset Input error
    const resetStartEndError = e => {
        setError({...error, startEnd: ""})
    }
    const resetPlaceError = e => {
        setError({...error, place: ""})
    }
    const resetCntError = e => {
        setError({...error, cnt: ""})
    }
    const resetContentError = e => {
        setError({...error, content: ""})
    }


    const startEndDone = e => {
        if (e.target.value !== "" && selectedResort !== "스키장 선택") {
            setIsDone({...isDone, startEnd: true})
        } else setIsDone({...isDone, startEnd: false})
    }
    const placeDone = e => {
        if (e.target.value !== "") {
            setIsDone({...isDone, place: true})
        } else setIsDone({...isDone, place: false})
    }
    const cntDone = e => {
        if (e.target.value !== "") {
            setIsDone({...isDone, cnt: true})
        } else setIsDone({...isDone, cnt: false})
    }

    const handleSubmit = e => {
        e.preventDefault();
        console.log(error);
    }

    const startPlace = selectedRoute === route[0] ?
        <Input>
            <label>출발지</label>
            <input
                type="text"
                ref={startEndInput}
                onClick={resetStartEndError}
                onChange={startEndDone}
                placeholder="입력 예시) 서울"
                />
        </Input> :
        <SelectBox list={resortName} label="출발지" func={reflectResort} state={selectedResort} />

    const endPlace = selectedRoute === route[0] ?
        <SelectBox list={resortName} label="도착지" func={reflectResort} state={selectedResort} /> :
        <Input>
            <label>도착지</label>
            <input
                type="text"
                ref={startEndInput}
                onClick={resetStartEndError}
                onChange={startEndDone}
                placeholder="입력 예시) 서울"
                />
        </Input>

    return (
    <Wrapper>
        <Title><div className="clubReg-top">카풀 등록</div></Title>
        <form onSubmit={handleSubmit}>
            <DatePick>
                <label>날짜</label>
                <span onClick={toggleDate}><SDatePicker 
                    className="tayoWrite-date"
                    selected={date} 
                    dateFormat="yyyy-MM-dd"
                    minDate={new Date()}
                    locale={ko}
                    onChange={date => setDate(date)} 
                    
                    /></span>
            </DatePick>
            <Error><Dummy></Dummy><div>{error.date ? error.date : null}</div></Error>

            <SelectBox list={route} label="경로" func={reflectRoute} state={selectedRoute} />
            <Error><Dummy></Dummy><div>{error.route ? error.route : null}</div></Error>

            {selectedRoute !== '--' && startPlace}
            {selectedRoute !== '--' && endPlace}
            
            {isDone.startEnd && 
            <DatePick>
                <label>출발 시간</label>
                <SDatePicker
                    selected={startTime}
                    onChange={onSelect}
                    locale={ko}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={30}
                    //minTime={setHours(setMinutes(new Date(), calMinute()), calMinHour())}
                    minTime={setHours(30, 23)}
                    maxTime={setHours(0, 0)}
                    dateFormat="aa h:mm 출발"
                    placeholderText='출발 시간'
                    className='tayoWrite-startT'
                />
            </DatePick>
            }

            {isDone.time &&
            <Input>
            <label>탑승 장소</label>
            <input
                type="text"
                ref={placeInput}
                onClick={resetPlaceError}
                onChange={placeDone}
                placeholder="입력 예시) 서울역 1번 출구"
                />
            </Input>
            }

            {isDone.place &&
            <SelectBox list={smoking} label="흡연 / 금연" func={reflectSmoke} state={selectedSmoking} />}
            <Error><Dummy></Dummy><div>{error.smoking ? error.smoking : null}</div></Error>

            {isDone.smoking &&
            <Input>
            <label>탑승 가능 인원</label>
            <input
                type="text"
                ref={cntInput}
                onClick={resetCntError}
                onChange={cntDone}
                placeholder="입력 예시) 4"
                />
            </Input>
            }

            {isDone.cnt &&
            <Input><label>추가 사항</label><textarea type="text" ref={contentInput} onClick={resetContentError} /></Input>}
            <Error><Dummy></Dummy><div>{error.content ? error.content : null}</div></Error>

            {isDone.cnt &&
            <BtnWrap><Button>개설하기</Button></BtnWrap>
            }
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