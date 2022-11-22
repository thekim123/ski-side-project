import React from 'react'
import {ResortMap} from './ResortMap'
import styled from 'styled-components'
import { FiFilter } from 'react-icons/fi';

export function MapModal(props) {
    const test = e => {
        console.log(e.target.className);
    }
    const clickOutside = (e) => {
        if (e.target.className === "openModal mapModal") {
            props.close();
        }
    }
    return (
    <Wrapper>
        <div className={props.open ? "openModal mapModal" : "mapModal"} onClick={clickOutside}>
        {props.open ?
            <Section>
                <Top>
                    <IconTitle>
                        <FiFilter className="mapModal-icon"/>
                        <Title>스키장을 선택해주세요</Title>
                    </IconTitle>
                    <Button onClick={props.close}>&times;</Button>
                </Top>
                <ResortMap />
            </Section>
        : null
        }
        </div>
    </Wrapper>
    )
}

const Wrapper = styled.div`
    .mapModal {
        display: none;
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 99;
        background-color: rgba(0, 0, 0, 0.6);
    }

    .openModal {
        display: flex;
        align-items: center;
        animation: modal-bg-shadow 0.3s;
    }
`
const Section = styled.div`
    width: 90%;
    margin: 0 auto;
    border-radius: 0.9rem;
    //animation: modal-show 0.3s;
    overflow: hidden;
    background-color: var(--background-color);
    padding-top: 15px;
    padding-left: 15px;
`
const IconTitle = styled.div`
display: flex;
align-items: center;
`
const Top = styled.div`
display: flex;
justify-content: space-between;
padding-bottom: 20px;
.mapModal-icon{
    width: 1.5rem;
    height: 1.5rem;
    color: rgba(0, 0, 0, 0.7);
}
`
const Title = styled.div`
font-size: 15px;
`
const Button = styled.button`
    outline: none;
    cursor: pointer;
    border: 0;
    background-color: transparent;
    font-size: 30px;
    padding-right: 20px;
    color: rgba(0, 0, 0, 0.7);
`