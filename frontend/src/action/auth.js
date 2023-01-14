import axios from 'axios';
import { authActions } from '../slice/auth';
import Send from '../components/common/Send';
import { createAsyncThunk } from '@reduxjs/toolkit';

export const login = (user) => {
    return function (dispatch) {
        
        axios(
            {
                url:'/login',
                method: 'post',
                baseURL: 'http://15.165.81.194:8080',
                withCredentials: true,
                data: user,
            }
        ).then(resp => {
            console.log("resp", resp);
            const accessToken = resp.headers.authorization;
            if (!accessToken) {
                dispatch(authActions.fail());
            } else {
                dispatch(authActions.setCredentials(accessToken));
                Send.defaults.headers.common['Authorization'] = accessToken;
                dispatch(getUser());
            }
        })
        .catch(error => console.log(error));
    }
}

export const kakaoLogin = (backData) => {
    return function (dispatch) {
        try {
            dispatch(authActions.setCredentials(backData.data));
            Send.defaults.headers.common['Authorization'] = 'Bearer '+backData.data;
            //localStorage.setItem('access_token', 'Bearer '+backData.data);
            //dispatch(getUser());    
            dispatch(asyncGetUser());    
        } catch (e) {
            console.log(e);
        }
    }
}

export const getUser = () => {
    return function (dispatch) {
        Send({
            url: '/user/get',
            method: 'get',
            //data: user,
        }).then(resp => {
            console.log("resp", resp);
            dispatch(authActions.setUser(resp.data.data));
        })
        .catch(error => {
            console.log(error);
            //dispatch(authActions.fail());
        });
    }
}

export const asyncGetUser = createAsyncThunk(
    'authSlice/asyncGetUser',
    async () => {
        const resp = await Send({
            url: `/user/get`,
            method: 'get',
        })
        console.log("resp", resp);
        return resp.data.data;
    }
)

export const asyncUpdateUser = createAsyncThunk(
    'authSlice/asyncUpdateUser',
    async (data) => {
        const resp = await Send({
            url: '/user/update',
            method: 'put',
            data: data,
        })
        console.log("회원 정보 수정", resp);
        return resp.data.data;
    }
)