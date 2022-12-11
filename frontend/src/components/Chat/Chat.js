import React, { useEffect, useState } from 'react'
import { MdKeyboardCapslock } from 'react-icons/md';
import styled from 'styled-components'
import { FiSend } from 'react-icons/fi'
import { BsFillPersonFill } from 'react-icons/bs'
import shortid from 'shortid'
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { BsChatDots } from 'react-icons/bs'

export default function Chat() {
    const dispatch = useDispatch();
    const [prevChat, setPrevChat] = useState([])
    const [commentInput, setCommentInput] = useState("");
    const [listening, setListening] = useState(false);
    const [meventSource, setmEventSource] = useState(undefined);
    let eventSource = undefined;
    const {room} = useParams();
    const user = useSelector(state => state.auth.user);
    const nickname = user.nickname.split("_")[0]

    const handleInputChange = e => {
        setCommentInput(e.target.value);
    }

    async function addMessage() {
        let chat = {
            sender: nickname,
            msg: commentInput,
            roomName: room,
        }

        fetch("http://localhost:8040/chat/save", {
            method: "post",
            body: JSON.stringify(chat),
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            }
        })
    }

    const sendMsg = () => {
        addMessage();
        setCommentInput("");
    }

    useEffect(() => {
        let eventSource;
        if (!listening) {
            //eventSource = new EventSource(`http://localhost:8040/chat/room/yejinsh`)
            eventSource = new EventSource(`http://localhost:8040/chat/room/${room}`)
            setmEventSource(eventSource)

            eventSource.onopen = event => {
                console.log("connection opened")
            }
            eventSource.onmessage = (event) => {
                const data = JSON.parse(event.data);
                setPrevChat(old => [...old, data]);
            }
            eventSource.onerror = event => {
                console.log(event.target.readyState);
                if (event.target.readyState === EventSource.CLOSED) {
                    console.log("eventsource closed (" + event.target.readyState + ")")
                }
                eventSource.close();
            }
            setListening(true); 
        }

        return () => {
            if (eventSource) {
            eventSource.close();
            console.log("eventsource closed");
            }
        }
    //}
    }, []);

    useEffect(() => {
        console.log(prevChat);
    }, [prevChat]);

    //나의 id와 roomName 속 내 id가 일치할 경우에만 보이도록.
    //roomName에 따라 채팅 위의 게시글 종류, 버튼 종류 등등 다르게 보이도록.
    return (
    <Wrapper>
        {prevChat.length === 0 && 
            <NoneWrapper>
                <div className='none-inside'>
                <SHiChatBubbleLeftEllipsis />
                <Text>채팅을 시작해보세요!</Text>
                </div>
            </NoneWrapper>}
        {prevChat.length > 0 && 
        <ChatWrapper>
            {prevChat.map(chat => (
                chat.sender === nickname ? 
                <MeWrapper key={shortid.generate()}>
                    <Dummy></Dummy>
                    <BubbleLine>
                        <Time><div>{chat.createdAt.slice(5, 7)}/{chat.createdAt.slice(8, 10)}</div>{chat.createdAt.slice(11, 16)}</Time>
                        <Me className='chat-bubble'>{chat.msg}</Me>
                    </BubbleLine> 
                </MeWrapper>
                : <YouWrapper key={shortid.generate()}>
                    <SBsFillPersonFill />
                    <NameBubble>
                        <Name>{chat.sender}</Name>
                        <BubbleLine>
                        <You className='chat-bubble'>{chat.msg}</You>
                        <Time><div>{chat.createdAt.slice(5, 7)}/{chat.createdAt.slice(8, 10)}</div>{chat.createdAt.slice(11, 16)}</Time>
                        </BubbleLine>
                    </NameBubble>
                </YouWrapper>
            ))}
        </ChatWrapper>}

        <InputWrapper>
        <textarea
                    type='text'
                    placeholder=''
                    value={commentInput}
                    name='text'
                    className='boardDetail-input'
                    onChange={handleInputChange}
                />
                <button onClick={sendMsg}><FiSend className='boardDetail-sendIcon' /></button>
        </InputWrapper>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 10px; 
//margin-top: 30px;
`
const NoneWrapper = styled.div`
height: 300px;
display: grid;
align-items: center;
justify-items: center;
color: var(--button-sub-color);
.none-inside {
    display: grid;
    justify-items: center;
}
`
const SHiChatBubbleLeftEllipsis = styled(BsChatDots)`
width: 70px;
height: 70px;
margin-bottom: 10px;
`
const Text = styled.div`

`
const ChatWrapper = styled.div`
display: grid;
margin-bottom: 100px;
.chat-bubble {
    padding: 11px 15px;
    //margin: 10px;
    margin-bottom: 3px;
}
`
const MeWrapper = styled.div`
display: grid;
grid-template-columns: 50px 1fr;
margin: 10px;
margin-right: 0;
`
const YouWrapper = styled.div`
display: grid;
grid-template-columns: 38px 1fr 50px;
align-items: start;
`
const Dummy = styled.div`

`
const Me = styled.div`
background-color: var(--button-color);
color: #FAFAFA;
border-radius: 10px 0 10px 10px;

`
const NameBubble = styled.div`
margin: 10px;
margin-top: 0px;
margin-left: 13px;
`
const Name = styled.div`
margin-bottom: 3px;
color: gray;
`
const BubbleLine = styled.div`
display: flex;
justify-self: end;
`
const Time = styled.div`
align-self: end;
color:#AFABAB;
font-size 11px;
padding: 5px;
`
const You = styled.div`
justify-self: start;
background-color: #FAFAFA;
//color: var(--button-color);
border-radius: 0px 10px 10px 10px;
display:inline-block;
`
const SBsFillPersonFill = styled(BsFillPersonFill)`
background-color: var(--button-sub-color);
color: #FAFAFA;
border-radius: 15px;
width: 30px;
height: 30px;
padding: 6px;
`
const InputWrapper = styled.div`
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
        padding-top: 5px;
        background-color: #FAFAFA;
        border: none;
        border-radius: 5px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }
    textarea:focus{
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