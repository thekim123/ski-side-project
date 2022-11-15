import React from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { HiPlus } from 'react-icons/hi';

export function ClubListForm(props) {
    //스키장 state 받아와서 grid에 표시
    //동호회 리스트   +
    const resorts = useSelector(state => state.resort.resorts);

    const changeParent = e => {
        props.change(e.target.innerText);
    }

    return (
    <Wrapper>
        {/* <ResortBtn>
            <div></div><Resort onClick={changeParent}>[전체]</Resort><div></div>
            {resorts.map(resort => (
                <Resort key={resort.id} onClick={changeParent}>{resort.name}</Resort>
            ))}
        </ResortBtn> */}
        <Top>
            <div></div><div className="clubList-title">동호회 리스트</div><Link to="/club/register"><HiPlus className="clubList-plus" /></Link>
        </Top>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 60px 40px;
position: fixed;
top: 0;
left: 0;
right: 0;
`
const ResortBtn = styled.div`
display:grid;
grid-template-columns: 1fr 1fr 1fr;
`
const Resort = styled.div`
font-size: 12px;
text-align: center;
margin: 5px;
border-bottom: 1px solid #CCCCCC;
padding: 5px;
`
const Top = styled.div`
display: flex;
justify-content: space-between;
padding: 20px 50px;
.clubList-title{
    font-weight: 200;
    padding-top: 13px;
    padding-left: 13px;
    font-size: 18px;
}
.clubList-plus{
    width: 1.1rem;
    height: 1.1rem;
    background-color: #6B89A5;
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px;
    margin-top: 7px;
}
`