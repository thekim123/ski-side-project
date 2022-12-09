import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loadPosts, loadPostsByPage } from '../action/board';
import styled from 'styled-components';
import BoardListForm from '../components/Board/BoardListForm';
import BoardListItem from '../components/Board/BoardListItem';

export function Board() {
    const dispatch = useDispatch();
    const posts = useSelector(state => state.board.posts);
    const [input, setInput] = useState("");
    
    useEffect(() => {
        dispatch(loadPosts());
        //dispatch(loadPostsByPage(1));
    }, [dispatch]);

    return(
        <BoardContainer>
            <BoardListForm func={setInput} input={input} />
            <POSTS>
                {posts.length > 0 && posts.filter(post => {
                    if (input === "") return post;
                    else if (post.title.toLowerCase().includes(input.toLowerCase())) return post;
                    else if (post.content.toLowerCase().includes(input.toLowerCase())) return post;
                    else if (post.resort.resortName.includes(input)) return post;
                }).map(post => 
                    <BoardListItem key={post.id} {...post} />
                )}
            </POSTS>
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