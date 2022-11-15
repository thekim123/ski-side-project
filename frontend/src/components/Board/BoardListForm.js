import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { Link, useNavigate } from 'react-router-dom'
import { MapModal } from '../common/MapModal'
import styled from 'styled-components'
import { BiSearch } from 'react-icons/bi'
import shortid from 'shortid'
import { FiFilter } from 'react-icons/fi';
import { HiPlus } from 'react-icons/hi';

function BoardListForm(props) {
    const navigate = useNavigate();
    //const resorts = useSelector(state => state.resort.resorts);
    const resort_kor = ["[하이원]", "[대명]", "[곤지암]", "[베어스]", "[지산]", "[덕유산]", "[에덴벨리]", "[비발디]", "[휘닉스]", "[웰리힐리]", "[용평]", "[엘리시안]"];
    const [input, setInput] = useState('');
    const [modalOpen, setModalOpen] = useState(false);

    const changeParent = e => {
        props.change(e.target.innerText);
    }

    const clickPlus = e => {
        navigate('/board/write');
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

    const openModal = () => {
        setModalOpen(true);
    }

    const closeModal = () => {
        setModalOpen(false);
    }
    return (
        <Wrapper>
            {/* <ResortBtn>
                <div></div><Resort onClick={changeParent}>[전체]</Resort><Link to="/board/write"><Button>글쓰기</Button></Link>
                {resort_kor.map(resort => (
                    <Resort key={shortid.generate()} onClick={changeParent}>{resort}</Resort>
                ))}
            </ResortBtn> */}
            <Top>
                <Title>자유게시판</Title>
                <Icons>
                    <FiFilter className="tayo-filter" onClick={openModal}/>
                    <HiPlus className="tayo-plus" onClick={clickPlus}/>
                </Icons>
            </Top>
            <MapModal open={modalOpen} close={closeModal} />
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
padding: 50px 20px 20px 20px;
position: fixed;
top: 0;
left: 0;
right: 0;

`
const Top = styled.div`
display: flex;
justify-content: space-between;
background-color: #E1EEFF;
`
const Title = styled.div`
padding-top: 10px;
font-size: 19px;
font-weight: 200;
`
const Icons = styled.div`
.tayo-filter, .tayo-plus{
    width: 1.1rem;
    height: 1.1rem;
    background-color: #6B89A5;
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px 0 4px 4px;
}
.tayo-plus{
    margin-left: 10px;
}
`
const ResortBtn = styled.div`
display:grid;
grid-template-columns: 1fr 1fr 1fr;
align-items: center;
`
const Resort = styled.div`
font-size: 12px;
text-align: center;
margin: 5px;
border-bottom: 1px solid #CCCCCC;
padding: 5px;
width: 70%;
`
const Button = styled.button`
`

const Form = styled.form`
    display: flex;
    padding-top: 10px;
    padding-bottom: 20px;
    background-color: #E1EEFF;

    .boardForm-input {
        flex: 1 1;
        height: 30px;
        padding: 3px 7px;
        background-color: #FAFAFA;
        border: none;
        border-radius: 5px;
        box-shadow: 5px 2px 7px -2px rgba(17, 20, 24, 0.15);
    }

    button{
        background-color:#6B89A5;
        border: none;
        border-radius: 5px;
        width: 2rem;
        margin-left: 7px;
    }

    .board-searchBtn {
        color: #FAFAFA;
    }
`

export default BoardListForm
