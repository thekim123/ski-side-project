import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { Link, useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { HiPlus } from 'react-icons/hi';
import { FiFilter } from 'react-icons/fi';
import { MapModal } from '../common/MapModal'

export function ClubListForm(props) {
    const navigate = useNavigate();
    const [modalOpen, setModalOpen] = useState(false);

    const clickPlus = () => {
        navigate('/club/register');
    }

    const openModal = () => {
        setModalOpen(true);
    }
    const closeModal = () => {
        setModalOpen(false);
    }

    return (
    <Wrapper>
        {/* <Top>
            <div></div><div className="clubList-title">동호회 리스트</div><Link to="/club/register"><HiPlus className="clubList-plus" /></Link>
        </Top> */}
        <Top>
            <Title>동호회</Title>
            <Icons>
                <FiFilter className="tayo-filter" onClick={openModal}/>
                <HiPlus className="tayo-plus" onClick={clickPlus}/>
            </Icons>
        </Top>
        <MapModal open={modalOpen} close={closeModal} />
    </Wrapper>
    )
}

const Wrapper = styled.div`
padding: 60px 40px 10px 40px;
background-color: var(--background-color);
position: fixed;
top: 0;
left: 0;
right: 0;
`

const Top = styled.div`
display: flex;
justify-content: space-between;
background-color: #EEF3F7;
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
    background-color: var(--button-color);
    color: #FAFAFA;
    padding: 7px;
    border-radius: 15px;
    margin: 4px 0 4px 4px;
}
.tayo-plus{
    margin-left: 10px;
}
`