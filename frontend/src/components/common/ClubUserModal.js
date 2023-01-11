import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import styled from 'styled-components'
import { asyncAdmitUser } from '../../action/club';

export function ClubUserModal(props) {
    const dispatch = useDispatch();
    //const [btnClicked, setBtnClicked] = useState(false);
    const [clickedArr, setClickedArr] = useState(Array.from({length: props.userList.length}, () => false));

    const clickOutside = (e) => {
        if (e.target.className === "openModal skiModal") {
            props.close();
        }
    }
    const clickX = () => { 
        props.close();
    }

    const admitUser = (e, idx, userId) => {
        let admitYn;
        if (e.target.innerText === '수락') {
            admitYn = true;
        } else {
            admitYn = false;
        }
        let data = {
            clubId: props.clubId,
            userId,
            admitYn
        }
        //setBtnClicked(e.target.innerText);
        let arr = [...clickedArr];
        arr[idx] = e.target.innerText + "완료";
        setClickedArr(arr);
        dispatch(asyncAdmitUser(data));
    }

    return (
        <Wrapper>
        <div className={props.open ? "openModal skiModal" : "skiModal"} onClick={clickOutside}>
        {props.open ? (
            <Section>
                <XButton onClick={clickX}>&times;</XButton>
                {props.type === 'showUser' ? 
                <UserList>
                    {props.userList
                        .sort(function(a, b) {
                            return a.order - b.order;
                        })
                        .map(user => 
                            <UserRow>
                            <div className='user-name'>{user.nickname.split("_")[0]}</div>
                            {user.role === 'ADMIN' ? 
                                <div>방장</div> :
                                <SubRow>
                                    {/* <RowBtn>퇴출</RowBtn> */}
                                    {/* <RowBtn>관리자 임명</RowBtn> */}
                                </SubRow>
                            }
                            </UserRow>
                        )}
                </UserList> : 
                <UserList>
                    {props.userList.length <= 0 ? <NoOutside><div>신청자가 없습니다</div></NoOutside> :
                    <>
                    {props.userList.map((user, idx) => 
                    <UserRow>
                        <div className='user-name'>{user.nickname.split("_")[0]}</div>
                        {clickedArr[idx] ? <div>{clickedArr[idx]}</div> :
                        <SubRow>
                            <RowBtn onClick={(e) => admitUser(e, idx, user.id)}>수락</RowBtn>
                            <RowBtn onClick={(e) => admitUser(e, idx, user.id)}>거절</RowBtn>
                        </SubRow>}
                    </UserRow>
                    )}</>}
                </UserList>
                }
            </Section>
        ): null}
        </div>
        </Wrapper>
    )
}

const Wrapper = styled.div`
display: grid;
justify-items: center;
    .skiModal {
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
        justify-items: center;
        animation: modal-bg-shadow 0.3s;
    }
`
const Section = styled.div`
    display: grid;
    width: 100%;
    margin: 70px 50px;
    border-radius: 13px;
    background-color: #FAFAFA;
    overflow: hidden;
    padding: 20px;
    padding-top: 25px;
    justify-self: center;
`
const XButton = styled.div`
font-size: 28px;
justify-self: end;
color: gray;
`
const UserList = styled.div`

`
const UserRow = styled.div`
display: flex;
justify-content: space-between;
margin: 10px 0;
align-items: center;
.user-name{
    font-family: nanum-square-bold;
}
`
const SubRow = styled.div`
display: flex;
`
const RowBtn = styled.div`
cursor: pointer;
background-color: var(--button-color);
border-radius: 7px;
padding: 10px 8px;
margin-left: 10px;
color: #FAFAFA;
font-size: 14px;
`
const NoOutside = styled.div`
display: flex;
margin: 20px;
`