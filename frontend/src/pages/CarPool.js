import React, { useState } from 'react'
import styled from 'styled-components'
import { GoSearch } from 'react-icons/go'
import { FaHouseUser, FaLongArrowAltRight, FaSkiing } from 'react-icons/fa'
import { IoMdArrowDropdown } from 'react-icons/io';
import resorts from '../data/resort.json'
import shortid from 'shortid'

export function CarPool() {
    const [routeType, setRouteType] = useState("house");
    const [selectedResort, setSelectedResort] = useState("--");
    const [showSelectBox, setShowSelectBox] = useState(false);
    const resortsData = resorts.filter(resort => resort.id !== null);

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
        <SearchWrapper>
            <SearchHeader>
                {/* <SearchTitle>Car Pool </SearchTitle> */}
                {/* <TGoSearch /> */}
                카풀 조회
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
                            <FaLongArrowAltRight className='carpool-root-arrow'/>
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
                            <FaLongArrowAltRight className='carpool-root-arrow'/>
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
    </Wrapper>
    )
}

const SearchWhat = styled.div`
font-size: 13px;
font-weight: bold;
padding: 8px;
`
const Wrapper = styled.div`
margin-top: 30px;
`
const SearchWrapper = styled.div`

`
const SearchHeader = styled.div`
//background-color: #6B89A5;
background-color: #C2CFD8;
display:flex;
flex-direction: column;
align-items: center;
color: black;
padding: 15px;
padding-top: 20px;
height: 120px;
font-size: 13px;
font-weight: bold;
`
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
margin-top: 20px;
width: 90%;
`
const SearchBoxInner = styled.div`
margin: 15px;
margin-bottom: 10px;
border: 1px solid #CCCCCC;
border-radius: 15px;
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
    color: #6B89A5;
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
background-color: #6B89A5;
color: #FAFAFA;
font-size: 14px;
margin: 15px;
margin-top: 0;
border-radius: 10px;
text-align: center;
padding: 10px;
`