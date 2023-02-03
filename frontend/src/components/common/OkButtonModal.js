import React from 'react'
import styled from 'styled-components'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { deleteComment, deletePost } from '../../action/board';
import { deleteCbPost, deleteClubComment } from '../../action/clubBoard';
import { deleteTayo } from '../../action/tayo';
import { deleteCarpool, submitCarpool } from '../../action/carpool';
import { asyncDeleteUser } from '../../action/auth';

export default function OkButtonModal(props) {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const clickOutside = (e) => {
        if (e.target.className === "openModal skiModal") {
            props.close();
        }
    }

    const handleOK = e => {
        if (props.usage === "boardDel") {
            dispatch(deletePost(props.targetId));
            props.close();
            navigate('/board');
        }
        else if (props.usage === "commentDel") {
            dispatch(deleteComment(props.targetId, props.targetId2));
            props.close();
        }
        else if (props.usage === "clubBoardDel") {
            dispatch(deleteCbPost(props.targetId, props.targetId2));
            props.close();
            navigate(`/club/detail/${props.targetId2}`);
        }
        else if (props.usage === "clubCommentDel") {
            dispatch(deleteClubComment(props.targetId, props.targetId2));
            props.close();
        }
        else if (props.usage === "tayoDel") {
            dispatch(deleteTayo(props.targetId));
            props.close();
            navigate('/tayo')
        } else if (props.usage === "carpoolSubmit") {
            let id = props.targetId;
            dispatch(submitCarpool(id));
            props.close();
            //navigate to chat
            navigate(`/carpool/chat/${id}/carpool${id}submit${props.submitId}writer${props.writerId}/submit`)            
        } else if (props.usage === "carpoolDel") {
            dispatch(deleteCarpool(props.targetId));
            props.close();
            navigate('/carpool');
        } else if (props.usage === "userDel") {
            dispatch(asyncDeleteUser());
            props.close();
            navigate('/');
        }
    }

    return (
        <Wrapper>
        <div className={props.open ? "openModal skiModal" : "skiModal"} onClick={clickOutside}>
        {props.open ? (
            <Section>
                <Message>{props.message}</Message>
                <ButtonBox>
                    <div></div>
                    <div>
                    <CancelBtn className='button-each' onClick={() => props.close()}>취소</CancelBtn>
                    <OkBtn className='button-each' onClick={handleOK}>{props.ok}</OkBtn>
                    </div>
                </ButtonBox>
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
    margin: 70px;
    border-radius: 13px;
    background-color: #FAFAFA;
    overflow: hidden;
    padding: 20px;
    padding-top: 25px;
    justify-self: center;
`
const Message = styled.div`
color: black;
padding-bottom: 40px;
`

const ButtonBox = styled.div`
display: flex;
justify-content: space-between;
.button-each{
    border: none;
    background-color: none;
    color: var(--button-color);
    padding-left: 12px;
    font-family: nanum-square-bold;
}
`
const CancelBtn = styled.span`

`
const OkBtn = styled.span`

`