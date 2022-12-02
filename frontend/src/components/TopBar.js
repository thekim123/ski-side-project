import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { BiArrowBack, BiUser, BiBell } from "react-icons/bi"

export function TopBar() {
    const navigate = useNavigate();
    const t = useParams()['*'];


    const goback = () => {
        navigate(-1);
    }
    const test = e => {
        console.log(t);
    }
    return(
        <Container>
            <LeftSide>
                {t !== '' && t !== 'board' && t !== 'carpool' && t !== 'tayo' && t !== 'club' && 
                    <BiArrowBack className="backBtn" onClick={goback} />}
                <PageName></PageName>
            </LeftSide>
            <RightSide>
                <BiBell className="bellBtn" onClick={test}/>
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
        color: var(--button-color);
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
        color: var(--button-color);
    }
`

const PageName = styled.div`
    margin: 0;
    padding-top: 4px;
`