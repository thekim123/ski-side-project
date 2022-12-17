import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom'
import { asyncGetClub, asyncGetClubUser, getClubUser, getSingleClub, loadClubs } from '../action/club';
import styled from 'styled-components'
import { ClubListForm } from '../components/Club/ClubListForm'
import resorts from '../data/resort.json'
import { GiRank1 } from 'react-icons/gi'
import { IoIosArrowDroprightCircle } from 'react-icons/io'
import { loadPosts } from '../action/clubBoard';

export function Club() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const clubs = useSelector(state => state.club.clubs);
    const user = useSelector(state => state.auth.user);
    const resortData = resorts.filter(resort => resort.id !== null);
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    //const [filteredResorts, setFilteredResorts] = useState(clubs); 
    const [sortedClubs, setSortedClubs] = useState(clubs);
    const top3Clubs = clubs.slice(0, 3);
    const fromFourthClubs = clubs.slice(3, );
    const [clubNum, setClubNum] = useState([]);

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }


    const showDetail = async (e, club) => {
        //유저 목록을 불러올 때까지 기다렸다가, 불러온 이후 유저가 동호회 멤버인지 체크 후 다른 페이지 랜더링.
        try {
            //const users = await dispatch(asyncGetClubUser(club.id)).unwrap();
            //const isMember = users.find(u => u.username === user.username);
            const isMember = undefined;
            if (isMember !== undefined) {
                navigate(`/club/detail/${club.id}`)
            } else {
                navigate(`/club/secret/${club.id}`);
            }
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => { 
        dispatch(loadClubs());
    }, [dispatch]);

    
    useEffect(() => { 
        if (clubs.length > 0) {
            const newArr = [...clubs];
            newArr.sort(function(a, b) {
            return b.memberCnt - a.memberCnt
            })
            setSortedClubs(newArr);
        }

    }, [selectedResort, clubs]);
    return (
        <ClubContainer>
            <ClubListForm change={changeSelection} />
            <ClubTop>
                {sortedClubs.length > 0 &&
                <Top3> 
                    <Rank1 id={sortedClubs[0].id} className="club-top3" onClick={e => showDetail(e, sortedClubs[0])}>
                        <Wrap>
                        <RankCnt>
                        <Rank id={sortedClubs[0].id}>1</Rank>
                        {/* <TopCnt id={sortedClubs[0].id}>{sortedClubs[0].memberCnt}명</TopCnt> */}
                        </RankCnt>
                        <TopName id={sortedClubs[0].id}>{sortedClubs[0].clubNm}</TopName>
                        <TopResort id={sortedClubs[0].id}>{resortData.find(resort => resort.id === sortedClubs[0].resortId).name}</TopResort>
                        </Wrap>
                        <SIoIosArrowDroprightCircle />
                    </Rank1>
                    {sortedClubs.length > 1 &&
                    <Rank2 id={sortedClubs[1].id} className="club-top3" onClick={e => showDetail(e, sortedClubs[1])}>
                        <Wrap>
                        <RankCnt>
                        <Rank id={sortedClubs[1].id}>2</Rank>
                        {/* <TopCnt id={sortedClubs[1].id}>{sortedClubs[1].memberCnt}명</TopCnt> */}
                        </RankCnt>
                        <TopName id={sortedClubs[1].id}>{sortedClubs[1].clubNm}</TopName>
                        <TopResort id={sortedClubs[1].id}>{resortData.find(resort => resort.id === sortedClubs[1].resortId).name}</TopResort>
                        </Wrap>
                        <SIoIosArrowDroprightCircle />
                    </Rank2>}
                    
                    {sortedClubs.length > 2 &&
                    <Rank3 id={sortedClubs[2].id} className="club-top3" onClick={e => showDetail(e, sortedClubs[2])}>
                        <Wrap>
                        <RankCnt>
                        <Rank id={sortedClubs[2].id}>3</Rank>
                        {/* <TopCnt id={sortedClubs[2].id}>{sortedClubs[2].memberCnt}명</TopCnt> */}
                        </RankCnt>
                        <TopName id={sortedClubs[2].id}>{sortedClubs[2].clubNm}</TopName>
                        <TopResort id={sortedClubs[2].id}>{resortData.find(resort => resort.id === sortedClubs[2].resortId).name}</TopResort>
                        </Wrap>
                        <SIoIosArrowDroprightCircle />
                    </Rank3>}
                </Top3>}
                <div>

                </div>
            </ClubTop>

            <ClubList>
                {sortedClubs.length > 3 && sortedClubs.slice(3, ).map((club, i) => (
                <ClubListItem key={i} id={club.id} onClick={e => showDetail(e, club)}>
                    <Wrap>
                        <RankCnt>
                        <Order id={club.id}>{i + 4}</Order>
                        {/* <MemCnt id={club.id}>{club.memberCnt}명</MemCnt> */}
                        </RankCnt>
                        <ClubName id={club.id}>{club.clubNm}</ClubName>
                        <ClubResort id={club.id}>{resortData.find(resort => resort.id === club.resortId).name}</ClubResort>
                    </Wrap>
                    <S2IoIosArrowDroprightCircle />
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
padding-top: 0;
display: grid;
justify-items: center;
`
const Top3 = styled.div`
display: grid;
//grid-template-columns: 1fr 1fr 1fr;
justify-items: center;
font-size: 18px;
width: 90%;
margin-top: 10px;
.club-top3{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    margin: 0 5px;
    width: 100%;
    box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    background-color: #FAFAFA;
    border-radius: 10px;
    margin-bottom: 10px;
}
`
const Wrap = styled.div`
display: flex;
align-items: center;
`
const Rank1 = styled.div`
`
const Rank2 = styled.div`
`
const Rank3 = styled.div`
`
const RankCnt = styled.div`
display: grid;
justify-items: center;
`
const Rank = styled.div`
font-size: 30px;
padding: 10px;
padding-bottom: 0;
color: var(--button-color);
`
const TopName = styled.div`
padding: 10px;
`
const TopResort = styled.div`
font-size: 13px;
color: gray;
`
const TopCnt = styled.div`
font-size: 12px;
color: gray;
padding: 4px;
//background-color: var(--button-sub-color);
//border-radius: 4px;
`
const SIoIosArrowDroprightCircle = styled(IoIosArrowDroprightCircle)`
width: 25px;
height: 25px;
color: var(--button-sub-color);
`

// 4위부터
const ClubList = styled.div`
    padding: 20px;
    padding-top: 0;
    //display:grid;
    //justify-content: center;
    //justify-items: center;
`
const ClubListItem = styled.div`
align-items: center;
border-bottom: 1px solid #CCCCCC;
display: flex;
justify-content: space-between;
padding: 7px 0;
width: 100%;
`
const Order = styled.div`
font-size: 22px;
font-weight: bold;
padding: 5px;
padding-bottom: 0;
`
const MemCnt = styled.div`
padding: 5px;
padding-bottom: 0;
font-size: 12px;
color: gray;
`
const ClubName= styled.div`
padding: 5px 20px;
font-weight: bold;
font-size: 16px;
`
const ClubResort = styled.div`
padding: 5px;
font-size: 13px;
color: gray;
`
const S2IoIosArrowDroprightCircle = styled(IoIosArrowDroprightCircle)`
width: 25px;
height: 25px;
color: gray;
`