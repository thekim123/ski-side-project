import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import { loadPosts } from '../../action/board';
import { loadCarpools } from '../../action/carpool';
import { loadClubPosts } from '../../action/clubBoard';
import { loadTayos } from '../../action/tayo';
import BoardListItem from '../Board/BoardListItem';
import { CarPoolListItem } from '../CarPool/CarPoolListItem';
import { TayoListItem } from '../Tayo/TayoListItem';
export default function MyPageDetail() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [myPostPage, setMyPostPage] = useState("자유게시판");
    const [myPosts, setMyPosts] = useState([]);
    const [myCarpools, setMyCarpools] = useState([]);
    const [myTayos, setMyTayos] = useState([]);
    const [myClubs, setMyClubs] = useState([]);
    const boards = useSelector(state => state.board.posts);
    const carpools = useSelector(state => state.carpool.carpools);
    const tayos = useSelector(state => state.tayo.posts);
    const clubBoards = useSelector(state => state.clubBoard.clubBoards);
    const user = useSelector(state => state.auth.user);

    const changeMyPost = e => {
        let page = e.target.innerText;
        setMyPostPage(page);
        if (page === '자유게시판') {
            dispatch(loadPosts());
        } else if (page === '카풀') {
            dispatch(loadCarpools());
        } else if (page === '벙개') {
            dispatch(loadTayos());
        } else if (page === '동호회') {
            //dispatch(lo)
            //dispatch(loadClubPosts());
        }
    }

    const showDetail = (id) => {
        navigate(`/carpool/detail/${id}`)
    }

    useEffect(() => {
        dispatch(loadPosts());
    }, [])

    useEffect(() => {
        if (boards.length > 0) {
            setMyPosts(boards.filter(board => board.user.id === user.id));
        }
    }, [boards])

    useEffect(() => {
        if (carpools.length > 0) {
            console.log(user);
            setMyCarpools(carpools.filter(carpool => carpool.user.id === user.id));
        }
        console.log("test", myCarpools)
    }, [carpools])

    useEffect(() => {
        if (tayos.length > 0) {
            console.log(tayos);
            setMyTayos(tayos.filter(tayo => tayo.userId === user.id));
        }
    }, [tayos])

    useEffect(() => {
        if (clubBoards.length > 0) {
            console.log(clubBoards)
            //setMyClubs(clubBoards.)
        }
    }, [clubBoards])

    return (
        <Wrapper>
            <MyWrapper>
            <SubmitTitle>내가 쓴 글</SubmitTitle>
            <ButtonWrap>
                <Btn onClick={changeMyPost} className={myPostPage === '자유게시판' ? 'selected' : ''}>자유게시판</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '카풀' ? 'selected' : ''}>카풀</Btn>
                <Btn onClick={changeMyPost} className={myPostPage === '벙개' ? 'selected' : ''}>벙개</Btn>
                {/* <Btn onClick={changeMyPost} className={myPostPage === '동호회' ? 'selected' : ''}>동호회</Btn> */}
            </ButtonWrap>
            </MyWrapper>

            <ItemWrapper>
                
                    {(myPostPage === '자유게시판') && myPosts.length > 0 && myPosts.map(post => <BoardListItem key={post.id} {...post} />)}
                    <CarWrap>{myPostPage === '카풀' && myCarpools.length > 0 && myCarpools.map(carpool => <CarPoolListItem key={carpool.id} {...carpool} func={showDetail}/>)}</CarWrap>
                    {(myPostPage === '벙개') && myTayos.length > 0 && myTayos.map(tayo => <TayoListItem key={tayo.id} {...tayo} />)}
            </ItemWrapper>
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
const CarWrap = styled.div`
margin: 15px;
`