import React from 'react'
import styled from 'styled-components'

export default function Alarm() {
    return (
    <Wrapper>
        <Title>알림</Title>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 20px; 
margin-top: 30px;
`
const Title = styled.div`
font-size: 19px;
font-family: nanum-square-bold;
`