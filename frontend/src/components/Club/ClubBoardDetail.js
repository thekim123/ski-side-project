import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components'
import { getPost } from '../../action/clubBoard';
import { HiPencil } from 'react-icons/hi'
import { BsTrashFill } from 'react-icons/bs'
import OkButtonModal from '../common/OkButtonModal';
import { FiSend } from 'react-icons/fi'

export default function ClubBoardDetail() {
    const dispatch = useDispatch();
    const post = useSelector(state => state.clubBoard.clubBoard);
    const [delOpen, setDelOpen] = useState(false);
    const [commentInput, setCommentInput] = useState("");
    const clubNm = useLocation().state;
    const {id} = useParams();

    const handlePencil = () => {

    }
    const handleTrash = () => {

    }
    const closeDel = () => {
        setDelOpen(false);
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
        //dispatch() clubBoard 댓글 작성
        setCommentInput("");
    }

    useEffect(() => {
        dispatch(getPost(id));
    }, [dispatch])

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
                    <SDate>2022-12-05</SDate>
                    <Who>관리자</Who>
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
                        targetId={id}/>
                </Icon>  
            </InfoIcon>
        </MostTop>

        <Content>
            {post.content}
        </Content>

        <CommentList>

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