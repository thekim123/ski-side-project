import React from 'react'
import styled from 'styled-components'

export function Agreement() {
    return (
    <Wrapper>
        <Title>개인정보 수집 동의</Title>
        <Content>[같이타요!] 서비스 회원가입을 위해 <br />아래와 같이 개인정보를 수집&#183;이용합니다.</Content>
        <Table>
            <thead>
                <th>수집 목적</th>
                <th>수집 항목</th>
                <th>보유&#183;이용 기간</th>
            </thead>
            <tbody>
                <tr>
                    <td>회원 식별 및 서비스 제공</td>
                    <td>성별, 출생 연도</td>
                    <td>이용 목적 달성 시</td>
                </tr>
            </tbody>
        </Table>
        <Caution>*동의를 거부할 권리가 있습니다. 다만, 필수 동의 거부 시 서비스가 제한될 수 있습니다.</Caution>
    </Wrapper>
    )
}

const Wrapper = styled.div`
margin: 130px 20px 0 20px;
background-color: #FAFAFA;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
border-radius: 10px;
padding: 30px 15px;
`

const Title = styled.div`
font-size: 17px;
font-family: nanum-square-bold;
padding-bottom: 20px;
`
const Content = styled.div`
font-size: 14px;
text-align: center;
`
const Table = styled.table`
padding: 15px;
`
const Caution = styled.div`
font-size: 10px;
`