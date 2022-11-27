import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom';
import { getSinglePost, deletePost, addComment, unlikes, likes } from '../../action/board';
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import { FiSend } from 'react-icons/fi'
import { AiOutlineLike, AiFillLike, AiOutlineDislike, AiFillDislike } from 'react-icons/ai'
import { BsFillPersonFill } from 'react-icons/bs'
import styled from 'styled-components'

export function BoardDetail() {
    const post = useSelector(state => state.board.post);
    const user = useSelector(state => state.auth.user);
    const [date, setDate] = useState(null);
    const [isMine, setIsMine] = useState(false);
    const [commentCnt, setCommentCnt] = useState(0);
    const [like, setLike] = useState(false);
    const [dislike, setDislike] = useState(false);
    const [commentInput, setCommentInput] = useState("");
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
        if (like) {
            setLike(false);
            dispatch(unlikes(id));
        }
        else {
            if (dislike) {
                setDislike(false);
                // dislike 취소
            }
            setLike(true);
            dispatch(likes(id));
        }
    }

    const toggleDislike = e => {
        if (dislike) setDislike(false);
        else {
            if (like) setLike(false);
            setDislike(true);
        }
    }

    const handleInputChange = e => {
        setCommentInput(e.target.value);
    }

    const handleSubmit = e => {
        e.preventDefault();
        const sendComment = {
            boardId: id,
            content: commentInput
        }
        dispatch(addComment(id, sendComment));
        setCommentInput("");
    }

    const splitContent = (content) => {

        let str = '": "';
        return content.split(str)[1].slice(0, -4);
    }

    const handleCommentTrash = e => {
        //댓글 삭제
    }

    useEffect(() => {
        if (id) {
            dispatch(getSinglePost(id));
        }
    }, [dispatch, id]);

    useEffect(() => {
        if (post) {
            setLike(post.likeState);
            if (user === post.user.username) setIsMine(true);
        }
    }, [post]);
    
    useEffect(() => {
        if (post) {
            const dt = post.createDate.slice(0, 10);
            const t = new Date(post.createDate).toString().slice(16, 21);
            setDate(dt + " " + t);
            
            if (post.username === user) { //나중엔 post.nickname으로 바꾸고 authSlice에서도 setUser에 nickname으로 넣기
                setIsMine(true);
            }
        }
    }, [post])
    
    return (
        <>
        {post && 
        <Container>
            <Top>
                <ResortBox><Resort>[{post.resort.resortName}]</Resort></ResortBox>
                <Title>{post.title}</Title>
                <NameDate>
                    <div className="boardDetail-nd">
                        <Name>{post.user.nickname}</Name>
                        <DateForm>{date}</DateForm>
                    </div>
                    <Icon>
                        {isMine && <HiPencil className="boardDetail-icon" onClick={handlePencil}/>}
                        {isMine && <BsTrashFill className="boardDetail-icon" onClick={handleTrash}/>}
                    </Icon>
                </NameDate>
            </Top>
            <PostInfo>
                {/* <div className="boardDetail-info"><Cnt>조회수 ({post.cnt})</Cnt><Like>좋아요 ({post.like})</Like><CommentCnt>댓글 ({commentCnt})</CommentCnt></div> */}
                <div></div>
            </PostInfo>
            <Content>
                {post.content}
            </Content>
            <ContentBottom>
                <LikeDislike>
                    <Like>
                        <div onClick={toggleLike}>{like ? <AiFillLike className="boardDetail-likeIcon"/> 
                        : <AiOutlineLike className="boardDetail-likeIcon"/>}</div>
                        <LikeCnt>{post.likeCount}</LikeCnt>
                    </Like>
                    <DisLike>
                        <div onClick={toggleDislike}>{dislike ? <AiFillDislike className="boardDetail-likeIcon"/> 
                        : <AiOutlineDislike className="boardDetail-likeIcon"/>}</div>
                        <DisLikeCnt>0</DisLikeCnt>
                    </DisLike>
                    
                </LikeDislike>
            </ContentBottom>

            <CommentList>
                {post &&
                    post.comment.map(c => (
                        <Comment key={c.id}>
                            <ComNameIcon>
                                <ComNameBox>
                                <SBsFillPersonFill />
                                <ComName>{c.user.nickname}</ComName>
                                </ComNameBox>
                                <NotiBox>
                                    {/* 다른 사람의 댓글일 때 신고 버튼 */}
                                {c.user.username !== user ? <ComNoti>신고</ComNoti> : null}
                                {/* 내 댓글일 때 삭제 버튼 */}
                                {c.user.username === user ? <SBsTrashFill onClick={handleCommentTrash}/> : null}
                                </NotiBox>
                            </ComNameIcon>
                            <ComContent>{c.content}</ComContent>
                            <ComDate>{new Date(c.createDate).getMonth()+1}/{new Date(c.createDate).getDate()} {c.createDate.slice(11, 16)}</ComDate>
                        </Comment>
                    ))
                }
            </CommentList>

            <Form onSubmit={handleSubmit}>
                <input
                    type='text'
                    placeholder='댓글 입력'
                    value={commentInput}
                    name='text'
                    className='boardDetail-input'
                    onChange={handleInputChange}
                />
                <button><FiSend className='boardDetail-sendIcon' /></button>
            </Form>
        </Container>
        }
        </>
    )
}

const Container = styled.div`
    margin-top: 30px;
    //padding: 20px;
`
const Top = styled.div`
padding-bottom: 4px;
//border-bottom: 1px solid #CCCCCC;
//background-color: #57748F;
padding: 20px;
padding-top: 0;
//margin: 0 20px;
//border-radius: 10px 10px 0 0;
`
const ResortBox = styled.div`
display: flex;
justify-content: center;
`
const Resort = styled.div`
font-size: 14px;
font-weight: bold;
padding-bottom: 5px;
`
const Title = styled.div`
font-weight: 900;
font-size: 19px;
//padding-top: 10px;
//padding-bottom: 19px;
padding-bottom: 7px;
//color: #E8E8E8;
color: black;
`
const NameDate = styled.div`
display: flex;
justify-content: space-between;
font-size: 13px;
//font-weight: bold;
//color: #FAFAFA;
color: black;
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
        width: 1.4rem;
        height: 1.4rem;
        //color: #6B89A5;
        color: var(--button-color);
        padding-left: 8px;
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
//padding-right:15px;
`
const LikeCnt = styled.div`
text-align: center;
font-size: 12px;
`
const DisLike = styled.div`

`
const DisLikeCnt = styled.div`
text-align: center;
font-size: 12px;
`
const CommentCnt = styled.div`

`
const Content = styled.div`
padding-top: 40px;
padding-bottom: 100px;
padding-left: 20px;
padding-right: 20px;
`
const ContentBottom = styled.div`
//border-bottom: 1px solid #CCCCCC;
margin: 15px 20px 0 20px;
.boardDetail-likeIcon{
    width: 2rem;
    height: 2rem;
    padding: 0 5px;
    color: var(--button-color);
}
`
const LikeDislike = styled.div`
display:flex;
justify-content: center;
padding-bottom: 15px;
border-bottom: 1px solid #CCCCCC;
`
const Form = styled.form`
    display: flex;
    //background-color: white;
    padding: 10px 20px;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    margin-bottom: 85px;

    .boardDetail-input {
        flex: 1 1;
        height: 40px;
        padding: 3px 7px;
        background-color: #FAFAFA;
        border: none;
        border-radius: 5px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }
    input:focus{
        outline: none;
    }

    button{
        background-color: var(--button-color);
        border: #CCCCCC;
        border-radius: 5px;
        width: 2.8rem;
        margin-left: 7px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }

    .boardDetail-sendIcon {
        color: #FAFAFA;
        width: 1.1rem;
        height: 1.1rem;
    }
`
const CommentList = styled.div`
margin: 15px 20px 10px 20px;
`
const Comment = styled.div`
display:grid;
//grid-template-columns: 70px 6fr 1fr;
align-items: center;
//border-bottom: 1px solid #CCCCCC;
padding: 12px 10px 12px 10px;
background-color: #FAFAFA;
border-radius: 10px;
margin-bottom: 10px;
box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
.boardDetail-combox {
    color: gray;
    padding-right:15px;
}
`
const ComNameIcon = styled.div`
display: flex;
justify-content: space-between;
`
const ComNameBox = styled.div`
display:flex;
align-items: center;
padding-bottom: 2px;
`
const ComName = styled.div`
font-size:12px;
text-align:center;
padding-top: 2px;
color: gray;
`
const SBsFillPersonFill = styled(BsFillPersonFill)`
color: #FAFAFA;
margin-right: 3px;
padding: 1px;
background-color: var(--button-sub-color);
border-radius: 4px;
width: 13px;
height: 13px;
`
const NotiBox = styled.div`
display: flex;
`
const SBsTrashFill = styled(BsTrashFill)`
margin-left: 6px;
color: var(--button-sub-color);
`
const ComDate = styled.div`
font-size:8px;
//text-align:center;
color: var(--button-sub-color);
`
const ComContent = styled.div`
padding: 6px 0;
font-size: 15px;
`
const ComNoti = styled.div`
font-size:13px;
color: var(--button-sub-color);
`