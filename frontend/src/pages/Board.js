import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loadPosts } from '../action/board';
import styled from 'styled-components';
import BoardListForm from '../components/Board/BoardListForm';
import BoardListItem from '../components/Board/BoardListItem';

export function Board() {
    const dispatch = useDispatch();
    const posts = useSelector(state => state.board.posts);
    const [selectedResort, setSelectedResort] = useState("[전체]"); 
    const [filteredResorts, setFilteredResorts] = useState(posts); 

    const changeSelection = (resort) => {
        setSelectedResort(resort);
    }

    
    useEffect(() => {
        dispatch(loadPosts());
    }, [dispatch]);

    
    useEffect(() => {
        if (selectedResort === "[전체]") {setFilteredResorts(posts);}
        else {
            let filteredResort = posts.filter(post => post.resortName === selectedResort);
            setFilteredResorts(filteredResort);
        }

    }, [selectedResort, posts])

    return(
        <BoardContainer>
            <BoardListForm change={changeSelection} />
            {/* <POSTS>
            {filteredResorts.length > 0 && filteredResorts.map((post) => (
                <BoardListItem key={post.id} {...post} />
            ))
            }
            </POSTS> */}
        </BoardContainer>
    )
}

const BoardContainer = styled.div`
    color: black;
    padding-top: 20px;
`
const POSTS = styled.div`
    padding-top: 110px;
`