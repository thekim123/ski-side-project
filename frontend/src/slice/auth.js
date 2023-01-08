import { createSlice } from '@reduxjs/toolkit'
import { asyncGetUser } from '../action/auth';

const initialState = {
    token: null,
    isAuth: false,
    //isAuth: true,
    user: null,
    /*
    user: {
        id: 8,
        nickname: "projects",
        username: "projects"
    },*/
    error: "",
}

const authSlice = createSlice({ 
    name: 'auth',
    initialState,
    reducers: {
        setCredentials: (state, action) => {
            state.token = action.payload;
            state.isAuth = true;
        },
        logOut: (state) => {
            state.token = null;
            state.isAuth = false;
            state.error = "";
        },
        setUser: (state, action) => {
            state.user = action.payload;
            //state.isAuth = true;
            state.error = "";
        },
        fail: (state) => {
            state.error = "아이디 또는 비밀번호가 존재하지 않습니다.";
        }
    },
    extraReducers: (builder) => {
        builder.addCase(asyncGetUser.fulfilled, (state, action) => {
            state.user = action.payload;
        })
    }
})

export const authActions = authSlice.actions;

export default authSlice;