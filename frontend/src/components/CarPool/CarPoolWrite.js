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
import { addCarpool } from '../../action/carpool';
import { BsFillCheckCircleFill } from 'react-icons/bs'

export function CarPoolWrite() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const type = ["카풀 요청", "카풀 등록"];
    const route = ["도착지가 스키장", "출발지가 스키장"];
    const resortsData = resorts.filter(resort => resort.id !== null);
    const resortName = resortsData.map(resort => resort.name);
    //const city = ["서울", "경기", "인천", "부산", "대구", "대전"]
    const smoking = ["금연 차량", "흡연 차량"];
    const [selectedType, setSelectedType] = useState("--");
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
        type: "",
        date:"",
        route: "",
        departure: "",
        destination: "",
        place: "",
        time: "",
        smoking: "",
        cnt: "",
        content: "",
    })
    const [isDone, setIsDone] = useState({
        type: false,
        startEnd: false,
        time: false,
        smoking: false,
        cnt: false,
    })
    const [negotiate, setNegotiate] = useState({
        departure: false,
        destination: false,
        departTime: false,
        boardingPlace: false,
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

    const reflectType = (selection) => {
        if (type.indexOf(selection) !== -1) {
            setSelectedType(selection); 
            setError({...error, type: ""})
            setIsDone({...isDone, type: true})
        }
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
    const resetDepartureError = e => {
        setError({...error, departure: ""})
    }
    const resetDestinationError = e => {
        setError({...error, destination: ""})
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

    // 입력 유효성 검사
    const validateInput = (startEnd, place, content, cnt) => {
        let local_error = {
            date:"",
            route: "",
            departure: "",
            destination: "",
            place: "",
            time: "",
            smoking: "",
            cnt: "",
            content: "",
        }
        
        // 제목, 홍보 문구, 최대 인원
        if (startEnd.trim() === '') {
            if (selectedRoute === '출발지가 스키장') {
                local_error.destination = "도착지를 입력하세요."
            } else local_error.departure = "출발지를 입력하세요."
        } else if (startEnd.length > 30) {
            if (selectedRoute === '출발지가 스키장') {
                local_error.destination = "30자 이하여야 합니다."
            } else local_error.departure = "30자 이하여야 합니다."
        } 
        if (place.trim() === '') {
            local_error.place = "탑승 장소를 입력하세요."
        } else if (place.length > 30) {
            local_error.place = "30자 이하여야 합니다."
        }
        if (content.trim() === '') {
            local_error.content = "추가 사항을 입력하세요."
        } else if (content.length > 1000) {
            local_error.content = "추가 사항은 1000자 이하여야 합니다."
        }
        if (cnt.trim() === '') local_error.cnt = "탑승 가능 인원을 입력하세요.";
        else {
            let replacedCnt = cnt.replace(/[0-9]/g, '');
            //console.log(replacedCnt.search(digitRegex) > -1)
            //console.log('hmm'.search(digitRegex) > -1);
            if (replacedCnt.length !== 0) local_error.cnt = "숫자만 입력 가능합니다.";
        }

        //날짜, 시간, 게시글 종류
        if (!selectedType) local_error.type = '요청 / 등록을 선택하세요.'
        if (!date) local_error.date = "날짜를 선택하세요."
        if (!selectedRoute) local_error.route = "경로를 선택하세요."
        if (!selectedResort) {
            if (selectedRoute === '출발지가 스키장')
                local_error.departure = "출발지를 선택하세요."
            else local_error.destination = "도착지를 선택하세요."
        }
        if (!startTime) local_error.time = "출발 시간을 선택하세요."
        if (!selectedSmoking) local_error.smoking = "흡연 / 금연을 선택하세요."

        setError({...local_error});
        for (let c in local_error) {
            if (local_error[c]) return false;
        }
        return true;
    }

    const handleSubmit = e => {
        e.preventDefault();

        const enteredStartEnd = startEndInput.current.value;
        const enteredPlace = placeInput.current.value;
        const enteredContent = contentInput.current.value;
        const enteredCnt = cntInput.current.value;
        if (!validateInput(enteredStartEnd, enteredPlace, enteredContent, enteredCnt)) return;

        const postType = selectedType === '카풀 요청' ? '요청' : '등록';
        const departure = selectedRoute === '도착지가 스키장' ? enteredStartEnd : selectedResort;
        const destination = selectedRoute === '도착지가 스키장' ? selectedResort : enteredStartEnd;
        const isSmoker = selectedSmoking === '흡연 차량' ? true : false;

        const realTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), startTime.getHours(), startTime.getMinutes());
        const krTime = new Date(realTime.getTime() + 9*60*60*1000);
        const carpool = {
            request: postType,
            departure: departure,
            destination: destination,
            departTime: krTime,
            boarding: enteredPlace,
            smoker: isSmoker,
            passenger: enteredCnt,
            memo: enteredContent,
            negotiate,
        }
        console.log(carpool)
        dispatch(addCarpool(carpool));
        navigate('/carpool');
    }

    const startPlace = selectedRoute === route[0] ?
        <Row>
        <Input>
            <label>출발지</label>
            <input
                type="text"
                ref={startEndInput}
                onClick={resetDepartureError}
                onChange={startEndDone}
                placeholder="입력 예시) 서울"
                />
        </Input>
        <SBsFillCheckCircleFill 
            value={"departure"}
            className={negotiate.departure ? "negotiate-true" : ""}
            onClick={() => setNegotiate({...negotiate, departure: !negotiate.departure })}/>
        </Row> :
        <Row>
        <SelectBox list={resortName} label="출발지" func={reflectResort} state={selectedResort} />
        <SBsFillCheckCircleFill 
            className={negotiate.departure ? "negotiate-true" : ""}
            onClick={() => setNegotiate({...negotiate, departure: !negotiate.departure })}/>
        </Row>

    const endPlace = selectedRoute === route[0] ?
        <Row>
        <SelectBox list={resortName} label="도착지" func={reflectResort} state={selectedResort} />
        <SBsFillCheckCircleFill 
            className={negotiate.destination ? "negotiate-true" : ""}
            onClick={() => setNegotiate({...negotiate, destination: !negotiate.destination })}/>
        </Row> :
        <Row>
        <Input>
            <label>도착지</label>
            <input
                type="text"
                ref={startEndInput}
                onClick={resetDestinationError}
                onChange={startEndDone}
                placeholder="입력 예시) 서울"
                />
        </Input>
        <SBsFillCheckCircleFill 
            className={negotiate.destination ? "negotiate-true" : ""}
            onClick={() => setNegotiate({...negotiate, destination: !negotiate.destination })}/>        
        </Row>

    return (
    <Wrapper> 
        <Title><div className="clubReg-top">카풀 등록</div></Title>
        <form onSubmit={handleSubmit}>
            <Row>
                <SelectBox list={type} label="요청/등록" func={reflectType} state={selectedType} />
                <div></div>
            </Row>
            <Error><Dummy></Dummy><div>{error.type ? error.type : null}</div></Error>

            {selectedType !== '--' && <Row>
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
            <div></div>
            </Row>}
            <Error><Dummy></Dummy><div>{error.date ? error.date : null}</div></Error>

            {selectedType !== '--' && <Row>
            <SelectBox list={route} label="경로" func={reflectRoute} state={selectedRoute} />
            <div></div>
            </Row>}
            <Error><Dummy></Dummy><div>{error.route ? error.route : null}</div></Error>

            {selectedRoute !== '--' && <TopRow>
            <div></div>
            <Explain>협의 가능</Explain>
            </TopRow>}
            {selectedRoute !== '--' && startPlace}
            <Error><Dummy></Dummy><div>{error.departure ? error.departure : null}</div></Error>
            {selectedRoute !== '--' && endPlace}
            <Error><Dummy></Dummy><div>{error.destination ? error.destination : null}</div></Error>
            
            {isDone.startEnd && 
            <Row>
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
            <SBsFillCheckCircleFill 
                className={negotiate.departTime ? "negotiate-true" : ""}
                onClick={() => setNegotiate({...negotiate, departTime: !negotiate.departTime })}/>         
            </Row>
            }<Error><Dummy></Dummy><div>{error.time ? error.time : null}</div></Error>

            {isDone.time &&
            <Row>
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
            <SBsFillCheckCircleFill 
                className={negotiate.boardingPlace ? "negotiate-true" : ""}
                onClick={() => setNegotiate({...negotiate, boardingPlace: !negotiate.boardingPlace })}/>   
            </Row>
            }<Error><Dummy></Dummy><div>{error.place ? error.place : null}</div></Error>

            {isDone.place &&
            <Row>
            <SelectBox list={smoking} label="흡연 / 금연" func={reflectSmoke} state={selectedSmoking} />
            <div></div>
            </Row>}
            <Error><Dummy></Dummy><div>{error.smoking ? error.smoking : null}</div></Error>

            {isDone.smoking &&
            <Row>
            <Input>
            {selectedType === '등록' ? <label>탑승 가능 인원</label> : <label>탑승 희망 인원</label>}
            <input
                type="text"
                ref={cntInput}
                onClick={resetCntError}
                onChange={cntDone}
                placeholder="입력 예시) 4"
                />
            </Input>
            <div></div>
            </Row>
            }<Error><Dummy></Dummy><div>{error.cnt ? error.cnt : null}</div></Error>

            {isDone.cnt &&
            <Row>
            <Input><label>추가 사항</label><textarea type="text" ref={contentInput} onClick={resetContentError} /></Input>
            <div></div>
            </Row>}
            <Error><Dummy></Dummy><div>{error.content ? error.content : null}</div></Error>

            {isDone.cnt &&
            <BtnWrap><Button>개설하기</Button></BtnWrap>
            }
        </form>
    </Wrapper>
    )
}

const TopRow = styled.div`
display: grid;
grid-template-columns: 1fr 50px;
padding-top: 14px;
`
const Explain = styled.div`
font-size: 12px;
justify-self: end;
`
const SBsFillCheckCircleFill = styled(BsFillCheckCircleFill)`
width: 20px;
height: 20px;
color: #CCCCCC;
align-self: center;
`
const Row = styled.div`
display: grid;
grid-template-columns: 1fr 30px;
.negotiate-true{
    color: #005C00
}
`
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
label {
    text-align: center;
    //padding-right: 10px;
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
margin: 10px;
padding: 10px 13px;
font-size: 14px;
color: gray;
height: 18.7px;
`

const Input = styled.div`
    display: grid;
    align-items: center;
    grid-template-columns: 110px 1fr;
    //margin: 10px;
    margin-top: 10px;
    label {
        text-align: center;
        //padding-right: 10px;
    }
    input, textarea {
        padding: 12px;
        margin: 10px;
        //margin-top: 5px;
        //margin-left: 0;
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        border-radius: 8px;
        //width: 110px;
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