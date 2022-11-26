import React from 'react'
import styled from 'styled-components'

export function Loading() {
    return (
    <Wrapper>
        <Text>로딩 중 ...</Text>
    </Wrapper>
    )
}

const Wrapper = styled.div`
height: 407px;
display: grid;
align-items: center;
justify-items: center;
`
const Text = styled.div`

`