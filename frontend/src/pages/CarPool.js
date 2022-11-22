import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { GoSearch } from 'react-icons/go'
import { FiFilter } from 'react-icons/fi';
import { HiPlus } from 'react-icons/hi';
import { FaHouseUser, FaLongArrowAltRight, FaSkiing } from 'react-icons/fa'
import { BsArrowRight } from 'react-icons/bs'
import { IoMdArrowDropdown } from 'react-icons/io';
import resorts from '../data/resort.json'
import shortid from 'shortid'
import { CarPoolListItem } from '../components/CarPool/CarPoolListItem';

export function CarPool() {
    const navigate = useNavigate();
    const [routeType, setRouteType] = useState("house");
    const [selectedResort, setSelectedResort] = useState("--");
    const [showSelectBox, setShowSelectBox] = useState(false);
    const resortsData = resorts.filter(resort => resort.id !== null);

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
    }
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
                            <FaHouseUser className='carpool-root-icon'/>
                            <BsArrowRight className='carpool-root-arrow'/>
                            <FaSkiing className='carpool-root-icon'/>
                        </RouteWrapper>
                        <RouteWrapper>
                            <input 
                                value="resort"
                                type="radio"
                                checked={routeType === "resort"}
                                onChange={handleRouteChange}
                            />
                            <FaSkiing className='carpool-root-icon'/>
                            <BsArrowRight className='carpool-root-arrow'/>
                            <FaHouseUser className='carpool-root-icon'/>
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
                <Button>조회하기</Button>
            </SearchBox>
            </SearchHeader>
        </SearchWrapper>

        <Posts>
            <CarPoolListItem />
            {/* <CarPoolListItem /> */}
        </Posts>
    </Wrapper>
    )
}

const SearchWhat = styled.div`
font-size: 14px;
font-weight: bold;
padding: 8px;
`
const Wrapper = styled.div`
margin-top: 20px;
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
font-weight: 200;
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
justify-content: center;
padding: 5px;
`
const RouteWrapper = styled.div`
padding: 10px;
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
background-color: #002060;
color: #FAFAFA;
font-size: 14px;
margin: 15px;
margin-top: 0;
border-radius: 10px;
text-align: center;
padding: 12px;
`

//POSTS
const Posts = styled.div`
margin: 160px 20px 0 20px;
`