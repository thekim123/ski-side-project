import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components'
import { loadPosts } from '../../action/board';
import BoardListItem from '../Board/BoardListItem';
import { CarPoolListItem } from '../CarPool/CarPoolListItem';
export default function MyPageDetail() {
    const dispatch = useDispatch();
    const [myPostPage, setMyPostPage] = useState("자유게시판");
    const [myPosts, setMyPosts] = useState([]);
    const boards = useSelector(state => state.board.posts);

    const changeMyPost = e => {
        let page = e.target.innerText;
        setMyPostPage(page);
        if (page === '자유게시판') {
            dispatch(loadPosts());
        } else if (page === '카풀') {

        }
    }

    useEffect(() => {
        dispatch(loadPosts());
    }, [])

    useEffect(() => {
        if (boards.length > 0) {
            setMyPosts(boards.filter(board => board.user.id === 2));
        }
    }, [boards])

    return (
        <Wrapper>
            <MyWrapper>
            <SubmitTitle>내가 쓴 글</SubmitTitle>
            <ButtonWrap>
                <Btn onClick={changeMyPost} className={myPostPage === '자유게시판' ? 'selected' : ''}>자유게시판</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '카풀' ? 'selected' : ''}>카풀</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '같이타요' ? 'selected' : ''}>같이타요</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '동호회' ? 'selected' : ''}>동호회</Btn>
            </ButtonWrap>
            </MyWrapper>

            {myPosts.length > 0 && <ItemWrapper>
                
                    {(myPostPage === '자유게시판') && myPosts.map(post => <BoardListItem key={post.id} {...post} />)}
                    {myPostPage === '카풀' && myPosts.map(post => <CarPoolListItem />)}
            </ItemWrapper>}
        </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 20px 0px 50px 0px;
`
const PageName = styled.div`
font-family: nanum-square-bold;
font-size: 19px;
padding: 0 20px;
margin-bottom: 15px;
`
const SubmitTitle = styled.div`
font-family: nanum-square-bold;
`
const MyWrapper = styled.div`
padding: 10px 20px;
padding-bottom: 10px;
`
const ButtonWrap = styled.div`
display: flex;
padding: 20px 0 10px 0;
border-bottom: 1px solid var(--button-color);
.selected {
    color: var(--button-color)
}
`
const Btn = styled.div`
font-size: 13px;
font-family: nanum-square-bold;
color: var(--button-sub-color);
padding-right: 16px;
`

const ItemWrapper = styled.div`
`