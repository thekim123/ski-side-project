import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import { getSinglePost, deletePost } from '../../action/board';
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import { FiSend } from 'react-icons/fi'
import { AiOutlineLike, AiFillLike, AiOutlineDislike, AiFillDislike } from 'react-icons/ai'
import styled from 'styled-components'

export function BoardDetail(props) {
    const post = useSelector(state => state.board.post);
    const user = useSelector(state => state.auth.user);
    const [date, setDate] = useState(null);
    const [isMine, setIsMine] = useState(true);
    const [commentCnt, setCommentCnt] = useState(0);
    const [like, setLike] = useState(false);
    const [dislike, setDislike] = useState(false);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    let {id} = useParams();

    const handlePencil = e => {
        navigate(`/board/edit/${id}`);
    }

    const handleTrash = e => {
        navigate("/board")
        dispatch(deletePost(id));
    }

    const toggleLike = e => {
        setLike(!like);
    }

    const toggleDislike = e => {
        setDislike(!dislike);
    }

    const handleSubmit = e => {
        e.preventDefault();
    }

    useEffect(() => {
        if (id) {
            dispatch(getSinglePost(id));
        }
    }, [dispatch, id]);
    
    useEffect(() => {
        if (post) {
            const dt = post.create_dt.slice(0, 10);
            const t = new Date(post.create_dt).toString().slice(16, 21);
            setDate(dt + " " + t);
        }
    }, [post])
    
    return (
        <>
        {post && 
        <Container>
            <Top>
                <Resort>[{post.resortName}]</Resort>
                <Title>{post.title}</Title>
                <NameDate>
                    <div className="boardDetail-nd">
                        <Name>{user.username}</Name>
                        <DateForm>{date}</DateForm>
                    </div>
                    <Icon>
                        {isMine && <HiPencil className="boardDetail-icon" onClick={handlePencil}/>}
                        {isMine && <BsTrashFill className="boardDetail-icon" onClick={handleTrash}/>}
                    </Icon>
                </NameDate>
            </Top>
            <PostInfo>
                <div className="boardDetail-info"><Cnt>조회수 ({post.cnt})</Cnt><Like>좋아요 ({post.like})</Like><CommentCnt>댓글 ({commentCnt})</CommentCnt></div>
                <div></div>
            </PostInfo>
            <Content>
                {post.content}
            </Content>
            <ContentBottom>
                <LikeDislike>
                    <div onClick={toggleLike}>{like ? <AiFillLike className="boardDetail-likeIcon"/> 
                        : <AiOutlineLike className="boardDetail-likeIcon"/>}</div>
                    <div onClick={toggleDislike}>{dislike ? <AiFillDislike className="boardDetail-likeIcon"/> 
                        : <AiOutlineDislike className="boardDetail-likeIcon"/>}</div>
                </LikeDislike>
            </ContentBottom>
            <Form onSubmit={handleSubmit}>
                <input
                    type='text'
                    placeholder='댓글 입력'
                    //value={input}
                    name='text'
                    className='boardDetail-input'
                    //onChange={handleChange}
                />
                <button><FiSend className='boardDetail-sendIcon' /></button>
            </Form>
            <CommentList>
                <Comment>
                    <div className="boardDetail-combox">
                        <ComName>닉네임</ComName>
                        <ComDate>12.23 22:32</ComDate>
                    </div>
                    <ComContent>댓글!! 같이 타요</ComContent>
                    <ComNoti>신고</ComNoti>
                </Comment>
                <Comment>
                    <div className="boardDetail-combox">
                        <ComName>닉네임</ComName>
                        <ComDate>12.23 22:32</ComDate>
                    </div>
                    <ComContent>댓글 임시</ComContent>
                    <ComNoti>신고</ComNoti>
                </Comment>
            </CommentList>
        </Container>
        }
        </>
    )
}

const Container = styled.div`
    margin-top: 30px;
    padding: 20px;
`
const Top = styled.div`
padding-bottom: 4px;
border-bottom: 1px solid #CCCCCC;
`
const Resort = styled.div`

`
const Title = styled.div`
font-weight: bold;
font-size: 20px;
padding-top: 10px;
padding-bottom: 19px;
`
const NameDate = styled.div`
display: flex;
justify-content: space-between;
font-size: 13px;
font-weight: bold;
color: gray;
.boardDetail-nd{
    display:flex;
}
`
const Name = styled.div`
padding-right: 20px;
`
const DateForm = styled.div`

`
const Icon = styled.div`
    .boardDetail-icon {
        width: 1.2rem;
        height: 1.2rem;
        color: black;
    }
`
const PostInfo = styled.div`
display: flex;
justify-content: space-between;
padding-top: 7px;
font-size: 12px;
.boardDetail-info{
    display: flex;
}
`
const Cnt = styled.div`
padding-right:15px;
`
const Like = styled.div`
padding-right:15px;
`
const CommentCnt = styled.div`

`
const Content = styled.div`
padding-top: 40px;
padding-bottom: 130px;

`
const ContentBottom = styled.div`
border-bottom: 1px solid #CCCCCC;
padding-bottom: 15px;
.boardDetail-likeIcon{
    width: 2rem;
    height: 2rem;
    color: black;
    padding: 5px;
}
`
const LikeDislike = styled.div`
display:flex;
justify-content: center;
`
const Form = styled.form`
    display: flex;
    padding-top: 10px;
    background-color: white;
    padding-bottom: 10px;

    .boardDetail-input {
        flex: 1 1;
        height: 30px;
        padding: 3px 7px;
        background-color: #FAFAFA;
        border: 1px solid #CCCCCC;
        border-radius: 5px;
    }

    button{
        background-color:#543A3A;
        border: #CCCCCC;
        border-radius: 5px;
        width: 2rem;
        margin-left: 7px;
    }

    .boardDetail-sendIcon {
        color: #FAFAFA;
        width: 1.1rem;
        height: 1.1rem;
    }
`
const CommentList = styled.div`

`
const Comment = styled.div`
display:grid;
grid-template-columns: 70px 6fr 1fr;
align-items: center;
border-bottom: 1px solid #CCCCCC;
padding-bottom: 10px;
padding-top: 10px;
.boardDetail-combox {
    color: gray;
    padding-right:15px;
}
`
const ComName = styled.div`
font-size:12px;
text-align:center;
`
const ComDate = styled.div`
font-size:8px;
text-align:center;
`
const ComContent = styled.div`

`
const ComNoti = styled.div`
font-size:13px;
`