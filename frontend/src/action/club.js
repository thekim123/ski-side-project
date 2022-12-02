import { clubActions } from '../slice/club';
import Send from '../components/common/Send';
import { createAsyncThunk } from '@reduxjs/toolkit';

export const asyncGetClub = createAsyncThunk(
    'clubSlice/asyncGetClub',
    async (id) => {
        const resp = await Send({
            url:`/club/${id}`,
            method: 'get',
        })
        //const data = await resp;
        console.log("resp", resp);
        //console.log(data);
        return resp.data.data;
    }
)
export const asyncGetClubUser = createAsyncThunk(
    'clubSlice/asyncGetClubUser',
    async (clubId) => {
        const resp = await Send({
            url: `/club/${clubId}/user`,
            method: 'get',
        })
        console.log("resp", resp);
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

export const loadClubs = () => {
    return function (dispatch) {
        Send({
            url: '/club',
            method: 'get',
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
            url:`/club/${id}`,
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
            dispatch(clubActions.deleteClub());
            loadClubs();
        })
        .catch(error => console.log(error));
    }
}