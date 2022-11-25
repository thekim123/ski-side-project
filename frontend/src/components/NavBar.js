import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import {GrHomeRounded} from 'react-icons/gr'
import { FaRegClipboard, FaSkiing } from 'react-icons/fa'
import { AiOutlineCar } from 'react-icons/ai'
import { RiCarLine } from 'react-icons/ri'
import { BsPeopleFill } from 'react-icons/bs'
import { FiHome } from 'react-icons/fi'

export function NavBar() {
    const [btnActive, setBtnActive] = useState("home");
    const toggleActive = e => {
        //id로 해서 해당 id를 state에 넣기.
        console.log(e.target.id)
        setBtnActive(e.target.id);
        console.log(btnActive);
    }

    return (
    <Container>
        <UL>
            {/* {
                Menus.map((menu,i) => (
                    <li key={i}>
                        
                    </li>
                ))
            } */}
        
            <Link to="/board" className="nav-link">
                <NavBox id="board" onClick={toggleActive}>
                <FaRegClipboard className={"nav-icons" + (btnActive === 'board' ? " active" : "")} id="board" />
                <NavList id="board" className={btnActive === 'board' ? " active" : ""}>자유게시판</NavList>
                </NavBox>
            </Link>
            <Link to="/carpool" className="nav-link">
                <NavBox id='carpool' onClick={toggleActive}>
                <RiCarLine className={'nav-icons-small' + (btnActive === 'carpool' ? " active" : "")} id='carpool'/>
                <NavList id='carpool' className={btnActive === 'carpool' ? " active" : ""}>카풀</NavList>
                </NavBox>
            </Link>
            <Link to="/" className="nav-link">
                <NavBox id="home" onClick={toggleActive}>
                <FiHome className={"nav-icons" + (btnActive === 'home' ? " active" : "")} id="home"/> 
                <NavList className={'nav-home' + (btnActive === 'home' ? " active" : "")} id="home">HOME</NavList>
                </NavBox>
            </Link>
            <Link to="/tayo" className="nav-link">
                <NavBox onClick={toggleActive} id='tayo'>
                <FaSkiing id='tayo' className={"nav-icons-big" + (btnActive === 'tayo' ? " active" : "")}/>
                <NavList id='tayo' className={btnActive === 'tayo' ? 'active' : ''}>같이 타요!</NavList>
                </NavBox>
            </Link>
            <Link to="/club" className="nav-link">
                <NavBox id='club' onClick={toggleActive}>
                <BsPeopleFill id='club' className={'nav-icons' + (btnActive === 'club' ? ' active' : '')} onClick={toggleActive}/>
                <NavList id='club' className={btnActive === 'club' ? 'active' : ''}>동호회</NavList>
                </NavBox>
            </Link>
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
    }
`