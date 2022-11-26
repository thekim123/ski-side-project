import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom'
import { loadClubs } from '../action/club';
import styled from 'styled-components'
import { ClubListForm } from '../components/Club/ClubListForm'
import resorts from '../data/resort.json'
import { GiRank1 } from 'react-icons/gi'

export function Club() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const clubs = useSelector(state => state.club.clubs);
    const resortData = resorts.filter(resort => resort.id !== null);
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    const [filteredResorts, setFilteredResorts] = useState(clubs); 
    const top3Clubs = clubs.slice(0, 3);
    const fromFourthClubs = clubs.slice(3, );
    const [clubNum, setClubNum] = useState([]);

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }

    const showDetail = (e, openYn) => {
        //만약 동호회가 오픈방이면 detail, 비밀방이면 secret 페이지로 이동.
        let clubId = e.target.id;
        if (openYn === "Y") { 
            navigate(`/club/detail/${clubId}`);
        } else {
            navigate(`/club/secret/${clubId}`);
        }
    }

    useEffect(() => {
        dispatch(loadClubs());
    }, [dispatch]);

    
    useEffect(() => { //나중에 회원 많은 순으로 정렬하는 기능 넣기
        if (selectedResort === "[전체]") setFilteredResorts(clubs);
        else {
            let filteredResort = clubs.filter(club => club.resort_id === selectedResort);
            setFilteredResorts(filteredResort);
        } 

    }, [selectedResort, clubs]);
    return (
    <ClubContainer>
        <ClubListForm change={changeSelection} />
        {filteredResorts.length > 3 && <ClubTop>
            <Top3>
                <Rank2 id={filteredResorts[1].id} className="club-top3" onClick={e => showDetail(e, filteredResorts[1].openYn)}>
                    <Rank id={filteredResorts[1].id}>2</Rank>
                    <div id={filteredResorts[1].id}>{filteredResorts[1].clubNm}</div>
                    <TopResort id={filteredResorts[1].id}>{resortData.find(resort => resort.id === filteredResorts[1].resortId).name}</TopResort>
                    <TopCnt id={filteredResorts[1].id}>{filteredResorts[1].memberCnt}명</TopCnt>
                </Rank2>
                <Rank1 id={filteredResorts[0].id} className="club-top3" onClick={e => showDetail(e, filteredResorts[0].openYn)}>
                    <Rank id={filteredResorts[0].id}>1</Rank>
                    <div id={filteredResorts[0].id}>{filteredResorts[0].clubNm}</div>
                    <TopResort id={filteredResorts[0].id}>{resortData.find(resort => resort.id === filteredResorts[0].resortId).name}</TopResort>
                    <TopCnt id={filteredResorts[0].id}>{filteredResorts[0].memberCnt}명</TopCnt>
                </Rank1>
                <Rank3 id={filteredResorts[2].id} className="club-top3" onClick={e => showDetail(e, filteredResorts[2].openYn)}>
                    <Rank id={filteredResorts[2].id}>3</Rank>
                    <div id={filteredResorts[2].id}>{filteredResorts[2].clubNm}</div>
                    <TopResort id={filteredResorts[2].id}>{resortData.find(resort => resort.id === filteredResorts[2].resortId).name}</TopResort>
                    <TopCnt id={filteredResorts[2].id}>{filteredResorts[2].memberCnt}명</TopCnt>
                </Rank3>
            </Top3>
        </ClubTop>}
        <ClubList>
            {fromFourthClubs.length > 0 && fromFourthClubs.map((club, i) => (
                <ClubListItem key={i} id={club.id} onClick={e => showDetail(e, club.openYn)}>
                    <Order id={club.id}>{i + 4}</Order>
                    <ClubName id={club.id}>{club.clubNm}</ClubName>
                    <ClubResort id={club.id}>{resortData.find(resort => resort.id === club.resortId).name}</ClubResort>
                    <MemCnt id={club.id}>{club.memberCnt}명</MemCnt>
                </ClubListItem>
            ))}
        </ClubList>
    </ClubContainer>
    )
}

const ClubContainer = styled.div`
    margin-top: 20px;
`
const ClubTop = styled.div`
margin-top: 80px;
padding: 20px;
display: grid;
justify-items: center;
background-color: #FAFAFA;
`
const Top3 = styled.div`
display: grid;
grid-template-columns: 1fr 1fr 1fr;
justify-items: center;
font-size: 18px;
.club-top3{
    display: grid;
    justify-items: center;
    border-radius: 10px;
    padding: 10px;
    margin: 0 5px;
}
`
const Rank1 = styled.div`
//background-color: var(--button-color);
//color: #FAFAFA;
padding-bottom: 5px;
`
const Rank2 = styled.div`
//background-color: var(--button-sub-color);
`
const Rank3 = styled.div`
//background-color: #FAFAFA;
`
const Rank = styled.div`
font-size: 30px;
//background-color: var(--button-color);
//color: #FAFAFA;
padding: 15px 20px;
border-radius: 50%;
`
const TopResort = styled.div`
font-size: 13px;
color: gray;
padding-top: 7px;
`
const TopCnt = styled.div`
font-size: 12px;
color: gray;
padding-top: 4px;
`

// 4위부터
const ClubList = styled.div`
    padding: 20px 40px;
    display:grid;
    justify-content: center;
    justify-items: center;
`
const ClubListItem = styled.div`
align-items: center;
border-bottom: 1px solid #CCCCCC;
display: grid;
grid-template-columns: 1fr 4fr 3fr 1fr;
padding: 7px 0;
width: 100%;
`
const Order = styled.div`
font-size: 25px;
font-weight: bold;
padding: 5px;
`
const MemCnt = styled.div`
padding: 5px;
font-size: 12px;
color: gray;
`
const ClubName= styled.div`
padding: 5px 20px;
font-weight: bold;
font-size: 18px;
`
const ClubResort = styled.div`
padding: 5px;
font-size: 13px;
color: gray;
`