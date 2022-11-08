import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { BiSearch } from 'react-icons/bi'

function BoardListForm(props) {
    const resorts = useSelector(state => state.resort.resorts);
    const [input, setInput] = useState('');

    const changeParent = e => {
        props.change(e.target.innerText);
    }

    const handleChange = e => {
        setInput(e.target.value);
    }

    const handleSubmit = e => {
        e.prefentDefault();

        {/*}
        props.onSubmit({
            //id: 어떻게..? auto-increment 이런거 있으면 좋을텐데
            text: input
        })*/}
        setInput('');

    }
    return (
        <Wrapper>
            <ResortBtn>
                <div></div><Resort onClick={changeParent}>[전체]</Resort><Link to="/board/write"><Button>글쓰기</Button></Link>
                {resorts.map(resort => (
                    <Resort key={resort.id} onClick={changeParent}>{resort.name}</Resort>
                ))}
            </ResortBtn>
            <Form onSubmit={handleSubmit}>
                <input
                    type='text'
                    placeholder='스키장 이름, 글 제목, 단어 검색'
                    value={input}
                    name='text'
                    className='boardForm-input'
                    onChange={handleChange}
                />
                <button><BiSearch className='board-searchBtn' /></button>
            </Form>
        </Wrapper>
        
    )
}

const Wrapper = styled.div`
padding: 60px 20px 20px 20px;
position: fixed;
top: 0;
left: 0;
right: 0;

`
const ResortBtn = styled.div`
display:grid;
grid-template-columns: 1fr 1fr 1fr;
align-items: center;
background-color: white;
`
const Resort = styled.div`
font-size: 12px;
text-align: center;
margin: 5px;
border-bottom: 1px solid #CCCCCC;
padding: 5px;
`
const Button = styled.button`
`

const Form = styled.form`
    display: flex;
    padding-top: 10px;
    background-color: white;
    padding-bottom: 20px;

    .boardForm-input {
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

    .board-searchBtn {
        color: #FAFAFA;
    }
`

export default BoardListForm
