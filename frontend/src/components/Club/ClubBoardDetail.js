import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components'
import { addComment, editPost, getPost } from '../../action/clubBoard';
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import OkButtonModal from '../common/OkButtonModal';
import { FiSend } from 'react-icons/fi'
import { BsFillPersonFill } from 'react-icons/bs'

export default function ClubBoardDetail() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const post = useSelector(state => state.clubBoard.clubBoard);
    const user = useSelector(state => state.auth.user);
    const [delOpen, setDelOpen] = useState(false);
    const [commentInput, setCommentInput] = useState("");
    const [commentDelOpen, setCommentDelOpen] = useState(false);
    const clubNm = useLocation().state;
    const {id} = useParams();

    const handlePencil = () => {
        navigate(`/club/board/edit/${id}`, {state: clubNm});
    }
    const handleTrash = () => {
        console.log("clubBoard date ", post.createDt);
        console.log(post);
        setDelOpen(true);
    }
    const handleCommentTrash = (commentId) => {
        //dispatch(deleteComment(commentId, id));
        setCommentDelOpen(true);
    }

    const closeDel = () => {
        setDelOpen(false);
    }
    const closeCommentDel = () => {
        setCommentDelOpen(false);
    }

    const handleInputChange = e => {
        setCommentInput(e.target.value);
    }

    const handleSubmit = e => {
        e.preventDefault();
        const sendComment = {
            clubBoardId: id,
            reply: commentInput
        }
        dispatch(addComment(sendComment))
        setCommentInput("");
    }

    useEffect(() => {
        
        dispatch(getPost(id));
    }, [dispatch])

    //임시
    useEffect(() => {
        if (post) {
            console.log(post.replies);
        }
    })

    return (
    <>
    {post && <Wrapper>
        <MostTop>
            <NameType>
                <ClubName>{clubNm}</ClubName>
                {post.sortScope === "notice" && <Type>공지</Type>}
            </NameType>

            <InfoIcon>
                <div>
                <Title>{post.title}</Title>
                <DateWho>
                    <SDate>{post.createDt[0]}.{post.createDt[1]}.{post.createDt[2]} {post.createDt[3]}:{post.createDt[4]}</SDate>
                    <Who>{post.sortScope === "notice" && "관리자"} {}</Who>
                </DateWho>
                </div>
                {/* 글쓴이에게만 보이게 */}
                <Icon>
                    <HiPencil className="boardDetail-icon" onClick={handlePencil}/>
                    <BsTrashFill className="boardDetail-icon" onClick={handleTrash}/>
                    <OkButtonModal 
                        open={delOpen}
                        close={closeDel}
                        message={"게시글을 삭제하시겠습니까?"}
                        ok={"삭제"}
                        usage={"clubBoardDel"}
                        targetId={id}
                        targetId2={post.clubId}/>
                </Icon>  
            </InfoIcon>
        </MostTop>

        <Content>
            {post.content}
        </Content>

        <CommentList>
        {post &&
                    post.replies.map(c => (
                        <Comment key={c.id}>
                            <ComNameIcon>
                                <ComNameBox>
                                <SBsFillPersonFill />
                                {/* <ComName>{} {c.user.nickname.split("_")[0]}</ComName> */}
                                </ComNameBox>
                                <NotiBox>
                                    {/* 다른 사람의 댓글일 때 신고 버튼 */}
                                {/* {c.user.username !== user.username ? <ComNoti>신고</ComNoti> : null} */}
                                {/* 내 댓글일 때 삭제 버튼 */}
                                {/* {c.user.username === user.username ? <SBsTrashFill onClick={() => handleCommentTrash(c.id)}/> : null} */}
                                </NotiBox>
                            </ComNameIcon>
                            <ComContent>{c.reply}</ComContent>
                            {/* <ComDate>{new Date(c.createdDate).getMonth()+1}/{new Date(c.createdDate).getDate()} {c.createdDate.slice(11, 16)}</ComDate> */}
                            <OkButtonModal 
                            open={commentDelOpen}
                            close={closeCommentDel}
                            message={"댓글을 삭제하시겠습니까?"}
                            ok={"삭제"}
                            usage={"clubCommentDel"}
                            targetId={c.id}
                            targetId2={id}
                            />
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
                    autoComplete='off'
                    onChange={handleInputChange}
                />
                <button><FiSend className='boardDetail-sendIcon' /></button>
            </Form>
    </Wrapper>}
    </>
    )
}
const Content = styled.div`
padding: 10px;
margin-top: 30px;
margin-bottom: 200px;
`
const Wrapper = styled.div`
margin: 10px;
margin-top: 20px;
`
const MostTop = styled.div`
padding: 10px;
padding-bottom: 8px;
border-bottom: 1px solid var(--button-color);
`
const NameType = styled.div`
display: flex;
padding-bottom: 15px;
`
const ClubName = styled.div`
font-family: nanum-square-bold;
font-size: 13px;
color: var(--button-color);
align-self: center;
`
const Type = styled.div`
font-size: 12px;
background-color: var(--button-color);
border-radius: 13px;
color: #FAFAFA;
padding: 6px;
margin-left: 7px;
`

const InfoIcon = styled.div`
display: flex;
justify-content: space-between;
`
const Title = styled.div`
font-family: nanum-square-bold;
font-size: 20px;
padding-bottom: 5px;
`
const DateWho = styled.div`
display: flex;
justify-content: space-between;
font-size: 12px;
color: gray;
`
const SDate = styled.span`

`
const Who = styled.span`
padding-left: 8px;
`
const Icon = styled.div`
align-self: end;
    .boardDetail-icon {
        width: 1.4rem;
        height: 1.4rem;
        //color: #6B89A5;
        color: var(--button-color);
        padding-left: 8px;
    }
`

const CommentList = styled.div`
//margin: 15px 20px 10px 20px;
margin: 10px
`

const Form = styled.form`
    display: flex;
    background-color: var(--background-color);
    padding: 0px 20px;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding-bottom: 95px;

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