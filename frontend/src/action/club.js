import { clubActions } from '../slice/club';
import Send from '../components/common/Send';
import { createAsyncThunk } from '@reduxjs/toolkit';

export const asyncGetClub = createAsyncThunk(
    'clubSlice/asyncGetClub',
    async (id) => {
        const resp = await Send({
            url:`/club/detail/${id}`,
            method: 'get',
        })
        //const data = await resp;
        console.log("resp", resp);
        console.log("in action");
        return resp.data.data;
    }
)
export const asyncGetClubUser = createAsyncThunk(
    'clubSlice/asyncGetClubUser',
    async (clubId) => {
        const resp = await Send({
            url: `/club/${clubId}/user`,
            method: 'get',
            params: {
                page: 0,
                size: 1000
            }, 
        })
        console.log("resp", resp);
        console.log("user - in action");
        return resp.data.data.content;
    }
)
export const asyncEditClub = createAsyncThunk(
    'clubSlice/asyncEditClub',
    async (club) => {
        const resp = await Send({
            url: `/club/update/${club.id}`,
            method: 'put',
            data: club,
        })
        console.log("resp", resp);
        return resp.data.data;
    }
)

export const asyncDeleteClub = createAsyncThunk(
    'clubSlice/asyncDeleteClub',
    async (clubId) => {
        const resp = await Send({
            url: `/club/delete/${clubId}`,
            method: 'delete',
        })
        console.log("resp", resp);
        return resp.data;
    }    
)

export const asyncEnrollClub = createAsyncThunk(
    'clubSlice/asyncEnrollClub',
    async (id) => {
        const resp = await Send({
            url:`/club/${id.userId}/enroll/${id.clubId}`,
            method: 'post',
        }) 
        console.log("resp", resp);
        return resp.data;
    }
)

export const asyncGetWaitingUser = createAsyncThunk(
    'clubSlice/asyncGetWaitingUser',
    async (id) => {
        const resp = await Send({
            url: `/club/${id}/waiting`,
            method: 'get',
        })
        console.log("대기자 명단 조회", resp);
        return resp.data.data;
    }
)

export const asyncGetUserByClub = createAsyncThunk(
    'clubSlice/asyncGetUserByClub',
    async (id) => {
        const resp = await Send({
            url: `/club/${id}/user`,
            method: 'get',
        })
        console.log("동호회별 유저 리스트 조회", resp);
        return resp.data.data;
    }
)

export const asyncAdmitUser = createAsyncThunk(
    'clubSlice/asyncAdmitUser',
    async (data) => {
        const resp = await Send({
            url: `/club/joining/${data.clubId}/${data.userId}/${data.admitYn}`,
            method: 'put',
        })
        console.log("가입 대기자 승인/거절", resp);
        return resp.data;
    }
)

export const loadClubs = () => {
    return function (dispatch) {
        Send({
            url: '/club',
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log(resp);
            dispatch(clubActions.getClubs(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const regClub = (post) => {
    return function (dispatch) {
        Send({
            url:'/club',
            method: 'post',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubActions.createClub());
            dispatch(loadClubs())
        })
        .catch(error => console.log(error));
    }
}

export const getSingleClub = (id) => { 
    return function (dispatch) {
        Send({
            url:`/club/detail/${id}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubActions.getClub(resp.data.data));
        })
        .catch(error => console.log(error));
    }
}

export const editClub = (clubId, club) => {
    return function (dispatch) {
        Send({
            url: `/club/update/${clubId}`,
            method: 'put',
            data: club,
        }).then(resp => {
            console.log("resp", resp);
            dispatch(clubActions.editClub());
        })
        .catch(error => console.log(error));
    }
}

export const getClubUser = (clubId) => {
    return function (dispatch) {
        Send({
            url: `/club/${clubId}/user`,
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubActions.getUsers(resp.data.data.content))
        })
        .catch(error => console.log(error));
    }
}

export const deleteClub = (clubId) => {
    return function (dispatch) {
        Send({
            url: `/club/delete/${clubId}`,
            method: 'delete',
        })
        .then((resp) => {
            console.log("resp", resp);
            //dispatch(clubActions.deleteClub());
            loadClubs();

        })
        .catch(error => console.log(error));
    }
}

export const enrollClub = (userId, clubId) => {
    return function (dispatch) {
        Send({
            url:`/${userId}/enroll/${clubId}`,
            method: 'post',
        }).then((resp) => {
            console.log("resp", resp);
            //dispatch(clubActions.enrollClub());
            //dispatch(getClubUser(clubId));
        })
        .catch(error => console.log(error));
    }    
}