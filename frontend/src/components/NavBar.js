import React from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import {GrHomeRounded} from 'react-icons/gr'

export function NavBar() {

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
                <NavList>자유게시판</NavList>
            </Link>
            <Link to="/board" className="nav-link">
                <NavList>카풀</NavList>
            </Link>
            <Link to="/" className="nav-link">
                <NavList><GrHomeRounded className="navBar-icon" /></NavList>
            </Link>
            <Link to="/board" className="nav-link">
                <NavList>같이 타요!</NavList>
            </Link>
            <Link to="/club" className="nav-link">
                <NavList>동호회</NavList>
            </Link>
        </UL>
    </Container>
    )
}

const Container = styled.div`
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    height: 4rem;
    background-color: #FAFAFA;
    border-top: 0.01rem solid #CCCCCC;
    border-radius: 20px 20px 0 0;
    align-items: center;
`

const UL = styled.ul`
    display: flex;
    justify-content: space-between;
    padding-top: 5px;
    padding-left: 30px;
    padding-right: 30px;
    
    .nav-link {
        text-decoration: none;
    }
`

const NavList = styled.li`
    list-style-type: none;
    color: gray;
    font-size: 0.9rem;

    .navBar-icon{
        //position: fixed;
        //bottom: 50;
        width: 1.6rem;
        height: 1.6rem;
        //background-color: #C2CFD8;
        //border-radius: 20px;
        color: #86B0B4;
    }
`

