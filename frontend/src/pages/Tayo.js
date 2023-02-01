import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loadTayos } from '../action/tayo';
import styled from 'styled-components';
import TayoListItem from '../components/Tayo/TayoListItem';
import { MapModal } from '../components/common/MapModal'
import { FiFilter } from 'react-icons/fi';
import { HiPlus } from 'react-icons/hi';
import shortid from 'shortid'
import { Switch } from '../components/common/Switch';

export function Tayo() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const posts = useSelector(state => state.tayo.posts);
    const [tayos, setTayos] = useState([]); 
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    const [filteredResorts, setFilteredResorts] = useState(posts); 
    const [modalOpen, setModalOpen] = useState(false);
    const [switchBoard, setSwitchBoard] = useState(false);
    const [tayoByType, setTayoByType] = useState(posts);
    const resort_kor = ["하이원", "대명", "곤지암", "베어스", "지산", "덕유산", "에덴벨리", "비발디", "휘닉스", "웰리힐리", "용평", "엘리시안"];

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }

    const clickPlus = e => {
        navigate('/tayo/write');
    }

    const openModal = () => {
        setModalOpen(true);
    }
    const closeModal = () => {
        setModalOpen(false);
    }

    const toggleRequest = () => {
        setSwitchBoard(!switchBoard);
        const origin = [...posts];
        if (switchBoard) {
            setTayoByType(origin.filter(tayo => tayo.rideDevice === '보드'))
        } else {
            setTayoByType(origin.filter(tayo => tayo.rideDevice === '스키'))
        }
        
    }

    
    useEffect(() => {
        dispatch(loadTayos());
    }, [dispatch]);
    useEffect(() => {
        if (posts) {
            const origin = [...posts];
            if (switchBoard) {
                setTayoByType(origin.filter(tayo => tayo.rideDevice === '스키'))
            } else {
                setTayoByType(origin.filter(tayo => tayo.rideDevice === '보드'))
            }
        }
    }, [posts])

    return(
        <Container>
            <TopWrapper>
                <Top>
                <Title>벙개</Title> 
                <Icons>
                    <FiFilter className="tayo-filter" onClick={openModal}/>
                    <HiPlus className="tayo-plus" onClick={clickPlus}/>
                </Icons>
                </Top>
                <MapModal open={modalOpen} close={closeModal} page="tayo"/>
                <SwitchBox>
                <SwitchText style={{color: switchBoard && '#002060'}}>스키</SwitchText>
                
                <Switch
                    isOn={switchBoard}
                    func={toggleRequest}/>
                <SwitchText style={{color: !switchBoard && '#005C00'}}>보드</SwitchText>
            </SwitchBox>
            </TopWrapper>

            <POSTS>
            {tayoByType.length > 0 && posts.map((post) => (
                <TayoListItem key={post.tayo_id} {...post} />
            ))
            }
            </POSTS>
        </Container>
    )
}

const Container = styled.div`
//background-color: white;
    color: black;
    padding-top: 20px;
    margin-bottom: 50px;
`

const TopWrapper = styled.div`
padding: 60px 20px 10px 20px;
background-color: var(--background-color);
position: fixed;
top: 0;
left: 0;
right: 0;
`
const Top = styled.div`
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
text-align: right;
.tayo-filter, .tayo-plus{
    width: 1.1rem;
    height: 1.1rem;
    background-color: var(--button-color);
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px;
}
`

const POSTS = styled.div`
    padding-top: 95px;
`
const Resorts = styled.div`
display:grid;
grid-template-columns: 1fr 1fr 1fr 1fr;
align-items: center;
//background-color: white;
position: fixed;
margin-top: 60px;
top: 0;
left: 0;
right: 0;
`
const Resort = styled.div`
font-size: 12px;
text-align: center;
margin: 5px;
border-bottom: 1px solid #CCCCCC;
padding: 5px;
width: 70%;
`

//switch box
const SwitchBox = styled.div`
display: flex;
justify-items: center;
align-items: end;
`
const SwitchText = styled.div`
font-size: 13px;
font-family: nanum-square-bold;
color: var(--button-sub-color);
padding-bottom: 5px;
`