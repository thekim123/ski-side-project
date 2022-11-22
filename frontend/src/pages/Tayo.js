import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loadTayos } from '../action/tayo';
import styled from 'styled-components';
import TayoListItem from '../components/Tayo/TayoListItem';
import { FiFilter } from 'react-icons/fi';
import { HiPlus } from 'react-icons/hi';
import shortid from 'shortid'

export function Tayo() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const posts = useSelector(state => state.tayo.posts);
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    const [filteredResorts, setFilteredResorts] = useState(posts); 
    const resort_kor = ["하이원", "대명", "곤지암", "베어스", "지산", "덕유산", "에덴벨리", "비발디", "휘닉스", "웰리힐리", "용평", "엘리시안"];

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }

    const clickPlus = e => {
        navigate('/tayo/write');
    }

    
    useEffect(() => {
        dispatch(loadTayos());
    }, [dispatch]);

    useEffect(() => {
        console.log(posts);
        if (selectedResort === "[전체]") {setFilteredResorts(posts); console.log(filteredResorts.length)}
        else {
            let filteredResort = posts.filter(post => post.resortName === selectedResort);
            setFilteredResorts(filteredResort);
        }

    }, [selectedResort, posts])

    return(
        <Container>
            {/* <Resorts>
            {resort_kor.map(resort => (
                    <Resort key={shortid.generate()}>{resort}</Resort>
                ))}
            </Resorts> */}
            <Icons>
                <FiFilter className="tayo-filter" />
                <HiPlus className="tayo-plus" onClick={clickPlus} />
            </Icons>
            <POSTS>
            {posts.length > 0 && posts.map((post) => (
                <TayoListItem key={post.id} {...post} />
            ))
            }
            </POSTS>
        </Container>
    )
}
const Icons = styled.div`
text-align: right;
padding-top: 20px;
padding-right: 20px;
.tayo-filter, .tayo-plus{
    width: 1.1rem;
    height: 1.1rem;
    background-color: #6B89A5;
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px;
}
`


const Container = styled.div`
//background-color: white;
    color: black;
    padding-top: 20px;
`

const POSTS = styled.div`
    //padding-top: 20px;
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