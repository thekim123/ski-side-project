import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom'
import { loadClubs } from '../action/club';
import styled from 'styled-components'
import { ClubListForm } from '../components/Club/ClubListForm'

export function Club() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const clubs = useSelector(state => state.club.clubs);
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    const [filteredResorts, setFilteredResorts] = useState(clubs); 
    const [clubNum, setClubNum] = useState([]);

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }

    const showDetail = (e) => {
        //만약 동호회가 오픈방이면 detail, 비밀방이면 secret 페이지로 이동.
        let clubId = e.target.id;
        let isOpen = clubs.filter(club => club.id == clubId)[0].openYn;
        if (isOpen) {
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
        <ClubList>
            {filteredResorts && filteredResorts.map((club, i) => (
                <ClubListItem key={club.id} id={club.id} onClick={showDetail}>
                    <Order id={club.id}>{i + 1}</Order>
                    <MemCnt id={club.id}>{club.memCnt}명</MemCnt>
                    <ClubName id={club.id}>{club.club_nm}</ClubName>
                    <ClubResort id={club.id}>{club.resort_id}</ClubResort>
                </ClubListItem>
            ))}
        </ClubList>
    </ClubContainer>
    )
}

const ClubContainer = styled.div`
    margin-top: 20px;
`
const ClubList = styled.div`
    padding: 250px 50px;
    display:flex;
    flex-direction: column;
    justify-content: center;
`
const ClubListItem = styled.div`
align-items: center;
border-bottom: 1px solid #CCCCCC;
display: grid;
grid-template-columns: 1fr 1fr 4fr 3fr;
padding: 7px 0;
`
const Order = styled.div`
font-size: 25px;
font-weight: bold;
padding: 5px;
`
const MemCnt = styled.div`
padding: 5px;
font-size: 14px;
`
const ClubName= styled.div`
padding: 5px 20px;
font-weight: bold;
`
const ClubResort = styled.div`
padding: 5px;
font-size: 14px;
`