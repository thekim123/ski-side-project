import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import {GrHomeRounded} from 'react-icons/gr'
import { FaRegClipboard, FaSkiing } from 'react-icons/fa'
import { AiOutlineCar } from 'react-icons/ai'
import { RiCarLine } from 'react-icons/ri'
import { BsPeopleFill } from 'react-icons/bs'
import { FiHome } from 'react-icons/fi'

export function NavBar() {
    const navigate = useNavigate();
    const url = useParams()['*'];
    const [btnActive, setBtnActive] = useState(null);

    useEffect(() => {
        if (url === '') setBtnActive("home");
        else {
            let sliceUrl = url.slice(0, 4);
            if (sliceUrl === 'boar') setBtnActive("board"); 
            else if (sliceUrl === 'carp') setBtnActive("carpool");
            else if (sliceUrl === 'tayo') setBtnActive("tayo");
            else if (sliceUrl === 'club') setBtnActive("club");
        }
    }, [])

    return (
    <Container>
        <UL>
                <NavBox onClick={e => {navigate('/board'); setBtnActive("board")}}>
                <FaRegClipboard className={"nav-icons" + (btnActive === 'board' ? " active" : "")}/>
                <NavList className={btnActive === 'board' ? " active" : ""}>자유게시판</NavList>
                </NavBox>

                <NavBox onClick={e => {navigate('/carpool'); setBtnActive("carpool")}}>
                <RiCarLine className={'nav-icons-small' + (btnActive === 'carpool' ? " active" : "")} />
                <NavList className={btnActive === 'carpool' ? " active" : ""}>카풀</NavList>
                </NavBox>

                <NavBox onClick={e => {navigate('/'); setBtnActive("home")}}>
                <FiHome className={"nav-icons" + (btnActive === 'home' ? " active" : "")} /> 
                <NavList className={'nav-home' + (btnActive === 'home' ? " active" : "")}>HOME</NavList>
                </NavBox>

                <NavBox onClick={e => {navigate('/tayo'); setBtnActive("tayo")}}>
                <FaSkiing id='tayo' className={"nav-icons-big" + (btnActive === 'tayo' ? " active" : "")}/>
                <NavList id='tayo' className={btnActive === 'tayo' ? 'active' : ''}>같이 타요!</NavList>
                </NavBox>

                <NavBox onClick={e => {navigate('/club'); setBtnActive("club")}}>
                <BsPeopleFill className={'nav-icons' + (btnActive === 'club' ? ' active' : '')} />
                <NavList className={btnActive === 'club' ? 'active' : ''}>동호회</NavList>
                </NavBox>
        </UL>
    </Container>
    )
}
const NavBox = styled.div`
display:grid;
justify-items: center;
.nav-home{
    font-size: 13px;
    //font-weight: bold;
}
`
const Container = styled.div`
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    height: 80px;
    background-color: #FAFAFA;
    //border-top: 0.01rem solid #CCCCCC;
    border-radius: 20px 20px 0 0;

    .nav-icons{
        width: 1.4rem;
        height: 1.4rem;
        color: var(--button-sub-color);
        padding-bottom: 5px;
    }
    .nav-icons-small{
        width: 1.5rem;
        height: 1.5rem;
        color: var(--button-sub-color);
        padding-bottom: 4px;
    }
    .nav-icons-big{
        width: 1.3rem;
        height: 1.3rem;
        color: var(--button-sub-color);
        padding-bottom: 7px;
    }
`



const NavList = styled.li`
    list-style-type: none;
    color: var(--button-sub-color);
    font-size: 12px;

    .navBar-icon{
        //position: fixed;
        //bottom: 50;
        width: 1.6rem;
        height: 1.6rem;
        //background-color: #C2CFD8;
        //border-radius: 20px;
        //color: #6B89A5;
    }
`

const UL = styled.ul`
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    padding-top: 5px;
    padding-left: 10px;
    padding-right: 10px;
    .nav-link {
        text-decoration: none;
    }
    .active{
        color: var(--button-color);
        font-family: "nanum-square-bold";
        font-weight: 900;
        NavList {
            color: red;
        }
    }

    NavList{
        color: red;
    }
`