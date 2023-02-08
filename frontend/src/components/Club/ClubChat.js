import React from 'react'
import Room from '../Chat/Room'
import styled from 'styled-components'

export default function ClubChat() {
  return (
    <Wrapper>
      <Room />
    </Wrapper>
  )
}

const Wrapper = styled.div`
margin-top: 15px;
`