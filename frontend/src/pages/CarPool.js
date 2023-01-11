import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { GoSearch } from 'react-icons/go'
import { FiFilter } from 'react-icons/fi';
import { HiPlus } from 'react-icons/hi';
import { FaHouseUser, FaLongArrowAltRight, FaSkiing } from 'react-icons/fa'
import { BsArrowRight } from 'react-icons/bs'
import { IoMdArrowDropdown } from 'react-icons/io';
import { BsFillCheckCircleFill } from 'react-icons/bs'
import resorts from '../data/resort.json'
import shortid from 'shortid'
import { CarPoolListItem } from '../components/CarPool/CarPoolListItem';
import { useDispatch, useSelector } from 'react-redux';
import { loadCarpools } from '../action/carpool';
import { CgDanger } from 'react-icons/cg';
import { Switch } from '../components/common/Switch';

export function CarPool() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const carpools = useSelector(state => state.carpool.carpools);
    const [requestPost, setRequestPost] = useState(false);
    const [filteredCarpools, setFilteredCarpools] = useState([]);
    const [carpoolsByType, setCarpoolsByType] = useState([]);
    const [routeType, setRouteType] = useState("house");
    const [selectedResort, setSelectedResort] = useState("--");
    const [showSelectBox, setShowSelectBox] = useState(false);
    const resortsData = resorts.filter(resort => resort.id !== null);
    const [isClicked, setIsClicked] = useState(false);
    const [canShow, setCanShow] = useState(false);
    const prevPageId = useLocation();

    const clickPlus = e => {
        navigate('/carpool/write');
    }

    const handleRouteChange = e => {
        setRouteType(e.target.value);
    }
    const toggleSelectBox = () => {
        setShowSelectBox(!showSelectBox);
    };
    const handleItemClick = (e) => {
        setSelectedResort(e.target.id);
        setShowSelectBox(false);
        setIsClicked(true);
    }

    const showCarpool = () => {
        if (selectedResort !== '--') {
            setCanShow(true);
            let result;
            if (routeType === 'house') {
                result = carpools.filter(carpool => carpool.destination === selectedResort)
                //setFilteredCarpools(carpools.filter(carpool => carpool.destination === selectedResort))
            } else {
                result = carpools.filter(carpool => carpool.departure === selectedResort)
                //setFilteredCarpools(carpools.filter(carpool => carpool.departure === selectedResort))
            }
            const filtered = [...filteredCarpools];
            const type = requestPost === false ? '요청' : '등록';
            setFilteredCarpools(result);
            setCarpoolsByType(result.filter(carpool => carpool.request === type));
        }
    }

    const toggleRequest = () => {
        console.log(requestPost);
        setRequestPost(!requestPost);
        const origin = [...filteredCarpools];
        if (requestPost) {
            setCarpoolsByType(origin.filter(carpool => carpool.request === '요청'))
        } else {
            setCarpoolsByType(origin.filter(carpool => carpool.request === '등록'))
        }
        
    }

    const showDetail = (id) => {
        navigate(`/carpool/detail/${id}`)
    }

    useEffect(() => {
        //앞 창이 카풀 디테일 페이지라면 그 아이디에 맞게 1, 2번 세팅하고 showCarpool함수 실행.
        console.log(prevPageId);
        dispatch(loadCarpools());
    }, [dispatch]);
    return (
    <Wrapper>
        <Top>
            <Title>카풀</Title>
            <Icons>
                <HiPlus className="tayo-plus" onClick={clickPlus}/>
            </Icons>
        </Top>
        <SearchWrapper>
            <SearchHeader>
                {/* <SearchTitle>Car Pool </SearchTitle> */}
                {/* <TGoSearch /> */}
                {/* <Title>카풀 조회</Title> */}
                <SearchBox>
                <SearchBoxInner>
                    <SearchWhat>1. 경로를 선택하세요.</SearchWhat>
                    <SearchContent>
                        <RouteWrapper>
                            <input 
                                value="house"
                                type="radio"
                                checked={routeType === "house"}
                                onChange={handleRouteChange}
                            />
                            <span>도착지가 스키장</span>
                            {/* <FaHouseUser className='carpool-root-icon'/>
                            <BsArrowRight className='carpool-root-arrow'/>
                            <FaSkiing className='carpool-root-icon'/> */}
                        </RouteWrapper>
                        <RouteWrapper>
                            <input 
                                value="resort"
                                type="radio"
                                checked={routeType === "resort"}
                                onChange={handleRouteChange}
                            />
                            <span>출발지가 스키장</span>
                            {/* <FaSkiing className='carpool-root-icon'/>
                            <BsArrowRight className='carpool-root-arrow'/>
                            <FaHouseUser className='carpool-root-icon'/> */}
                        </RouteWrapper>
                    </SearchContent>

                    <SearchWhat>2. 스키장을 선택하세요.</SearchWhat>
                    <SearchContent>
                    <SelectWrapper>
                        <div className="dropdown">
                            <div className="dropdown-btn" onClick={toggleSelectBox}>{selectedResort}<IoMdArrowDropdown className="boardWrite-icon"/></div>
                            {showSelectBox && <div className="dropdown-content">
                                {
                                    resortsData.map(item => (
                                        <div key={shortid.generate()} id={item.name} className="dropdown-item" onClick={handleItemClick}>{item.name}</div>
                                    ))
                                }
                                
                            </div>}
                        </div>
                    </SelectWrapper>
                    </SearchContent>
                </SearchBoxInner>
                <Button onClick={showCarpool} className={isClicked ? "clicked" : ""}>조회하기</Button>
            </SearchBox>
            </SearchHeader>
        </SearchWrapper>

        {/* {canShow &&  */}
        <Posts>
            {canShow && <SwitchBox>
                <SwitchText style={{color: requestPost && '#002060'}}>등록만 보기</SwitchText>
                
                <Switch 
                    isOn={requestPost}
                    func={toggleRequest}/>
                <SwitchText style={{color: !requestPost && '#005C00'}}>요청만 보기</SwitchText>
            </SwitchBox>}
            {carpoolsByType.length > 0 && <>
            <TagBox>
                <div></div>
                <div className='tagBox-inside'>
                <SBsFillCheckCircleFill />
                <div>협의 가능</div>
                </div>
            </TagBox>
            {carpoolsByType.map(carpool => (
                <CarPoolListItem  key={carpool.id}{...carpool} func={showDetail}/>
            ))}</>}

            {selectedResort === '--' ? <div></div> : canShow && carpoolsByType.length <= 0 &&
            <NoPostInside>
                <div><CgDanger className='noPost-icon'/></div>
                <div>조건에 맞는 게시글이 없습니다.</div>
            </NoPostInside>}
        </Posts>
        {/* {selectedResort === '--' ? <div></div> : canShow && filteredCarpools.length <= 0 &&
        <NoPost>
            <div><CgDanger className='noPost-icon'/></div>
            <div>조건에 맞는 게시글이 없습니다.</div>
        </NoPost>} */}
    </Wrapper>
    )
}
const NoPost = styled.div`
margin-top: 250px;
display:grid;
justify-items: center;
//align-items: center;
.noPost-icon {
    width: 40px;
    height: 40px;
    padding-bottom: 15px;
}
div{
    color: var(--button-sub-color);
}
`
const NoPostInside = styled.div`
display:grid;
margin-top: 60px;
justify-items: center;
//align-items: center;
.noPost-icon {
    width: 40px;
    height: 40px;
    padding-bottom: 15px;
}
div{
    color: var(--button-sub-color);
}
`
const SearchWhat = styled.div`
font-size: 14px;
font-weight: bold;
padding: 8px;
`
const Wrapper = styled.div`
margin-top: 20px;
margin-bottom: 50px;
`
const Top = styled.div`
padding: 0 35px;
display: flex;
justify-content: space-between;
background-color: #EEF3F7;
`
const Title = styled.div`
padding-top: 10px;
font-size: 19px;
font-family: nanum-square-bold;
`
const Icons = styled.div`
.tayo-filter, .tayo-plus{
    width: 1.1rem;
    height: 1.1rem;
    background-color: #6B89A5;
    background-color: var(--button-color);
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px 0 4px 4px;
}
.tayo-plus{
    margin-left: 10px;
    margin-top: 7px;
}
`
const SearchWrapper = styled.div`

`
const SearchHeader = styled.div`
//background-color: #333F50;
//background-color: #C2CFD8;
display:flex;
flex-direction: column;
align-items: center;
color: black;
padding: 15px;
padding-top: 10px;
height: 120px;
font-size: 13px;
font-weight: bold;
`
/*
const Title = styled.div`
color: black;
font-weight: 300;
font-size: 18px;
`*/
const TGoSearch = styled(GoSearch)`
width:2rem;
height:2rem;
padding: 10px;
z-index: 100;
color: #FAFAFA;
`
const SearchBox = styled.div`
background-color: #FAFAFA;
border-radius: 15px;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
//margin: 30px;
//margin-top: 20px;
width: 90%;
.clicked{
    background-color: var(--button-color);
}
`
const SearchBoxInner = styled.div`
margin: 15px;
margin-bottom: 10px;
border: 1px solid #CCCCCC;
border-radius: 15px;
padding: 5px;
`
const SearchContent = styled.div`
display:flex;
justify-content: space-between;
padding: 5px;
`
const RouteWrapper = styled.div`
padding: 10px;
display: flex;
align-items: center;
.carpool-root-icon{
    width:1.2rem;
    height:1.2rem;
    color: #002060;
    padding: 0 1px;
}
.carpool-root-arrow{
    color: black;
    padding: 0 1px;
}
span{
    color: gray;
}
`

//스키장 select box
const SelectWrapper = styled.div`
width: 100%;
grid-template-columns: 110px 1fr;
    label {
        text-align: center;
    }
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

//button
const Button = styled.div`
//background-color: #6B89A5;
//background-color: rgb(59, 59, 59);
//background-color: #002060;
background-color: gray;
color: #FAFAFA;
font-size: 14px;
margin: 15px;
margin-top: 0;
border-radius: 10px;
text-align: center;
padding: 12px;
`

//switch box
const SwitchBox = styled.div`
display: flex;
justify-items: center;
align-items: end;
margin: 10px 0px;
`
const SwitchText = styled.div`
font-size: 13px;
font-family: nanum-square-bold;
color: var(--button-sub-color);
padding-bottom: 5px;
`

//POSTS
const Posts = styled.div`
margin: 160px 20px 0 20px;
`
const TagBox = styled.div`
display: flex;
justify-content: space-between;
padding: 0 0 10px 5px;
div{
    //color: gray;
    font-size: 12px;
}
.tagBox-inside {
    display: flex;
}
`
const SBsFillCheckCircleFill = styled(BsFillCheckCircleFill)`
align-self: center;
margin-right: 3px;
width: 13px;
height: 13px;
color: #005C00;
`