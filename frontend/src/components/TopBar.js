import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { BiArrowBack, BiUser, BiBell } from "react-icons/bi"

export function TopBar() {
    const navigate = useNavigate();

    const goback = () => {
        navigate(-1);
    }
    return(
        <Container>
            <LeftSide>
                <BiArrowBack className="backBtn" onClick={goback} />
                <PageName></PageName>
            </LeftSide>
            <RightSide>
                <BiBell className="bellBtn"/>
                <BiUser className="myBtn"/>
            </RightSide>
        </Container>
    )
}

const Container = styled.div`
position: fixed;
    left: 0;
    right: 0;
    top: 0;
    z-index: 99;
    background-color: #E1EEFF;
    background-color: #EEF3F7;
    //border-bottom: 0.01rem solid #CCCCCC;
    height: 48px;
    margin-top: 0;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .backBtn {
        width: 1rem;
        height: 1rem;
        margin: 7px;
        color: #543A3A;
        //color: #6B89A5;
    }
`

const LeftSide = styled.div`
    display:flex;
`

const RightSide = styled.div`
    display: flex;
    margin: 7px;

    .bellBtn, .myBtn{
        margin-right: 4px;
        width: 1.5rem;
        height: 1.5rem;
        color: #543A3A;
        //color: #6B89A5;
    }
`

const PageName = styled.div`
    margin: 0;
    padding-top: 4px;
`