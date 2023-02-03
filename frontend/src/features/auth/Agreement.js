import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components'

export function Agreement() {
    const [agreement, setAgreement] = useState(false);
    const [disAgreement, setDisAgreement] = useState(false);
    const navigate = useNavigate();
    const user = useSelector(state => state.auth.user);

    const changeAgreement = () => {
        if (disAgreement) {
            setDisAgreement(false);
        }
        setAgreement(!agreement);
    }
    const changeDisAgreement = () => {
        if (agreement) {
            setAgreement(false);
        }
        setDisAgreement(!disAgreement);
    }
    const gotoNext = () => {
        if (agreement) {
            navigate("/user");
        }
    }

    useEffect(() => {
        if (user) {
            if (user.nickname) {
                navigate("/");
            }
        }
    }, [user])

    return (
        <> {!user ? <div></div> : 
        <>
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
        <Bottom>
            <Text>위 개인정보 수집&#183;이용에 동의합니다.</Text>
            <CheckWrapper>
                <div>
                <input type="checkbox" checked={agreement} onChange={changeAgreement}/>
                <label>YES</label>
                </div>
                <div>
                <input type="checkbox" checked={disAgreement} onChange={changeDisAgreement}/>
                <label>NO</label>
                </div>
            </CheckWrapper>
        </Bottom>
    </Wrapper>
    <Button onClick={gotoNext} style={{backgroundColor: agreement ? '#002060' : '#CCCCCC'}}><div>다음</div></Button>
    </>}
    </>
    )
}
const Bottom = styled.div`
display: flex;
justify-content: space-between;
font-size: 11px;
align-items: center;
`
const Text = styled.div`
font-family: nanum-square-bold;
`
const CheckWrapper = styled.div`
display: flex;
div {
    display: flex;
    align-items: center;
    padding-left: 5px;
}
`
const Button = styled.div`
display: grid;
justify-items: center;
align-items: center;
height: 45px;
background-color: var(--button-color);
color: #FAFAFA;
border-radius: 0 0 10px 10px;
margin: 0 10px;
`
const Wrapper = styled.div`
margin: 130px 10px 0 10px;
background-color: #FAFAFA;
box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
border-radius: 10px 10px 0 0;
padding: 30px 15px;
display: grid;
justify-items: center;
align-items: center;

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
padding: 17px 7px;
margin: 20px 0;
border: 1px solid #CCCCCC;
font-size: 13px;
width: 100%;
td, th {
    border: 1px solid #CCCCCC;
    padding: 10px 0;
}
th {
    font-size: 14px;
    font-family: nanum-square-bold;
}
td {
    text-align: center;
}
border-collapse: collapse;
`
const Caution = styled.div`
font-size: 10px;
`