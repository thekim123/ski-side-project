import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    token: null,
    isAuth: false,
    user: null,
    error: "",
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        setCredentials: (state, action) => {
            state.token = action.payload;
        },
        logOut: (state) => {
            state.token = null;
            state.isAuth = false;
            state.error = "";
        },
        setUser: (state, action) => {
            state.user = action.payload;
            state.isAuth = true;
            state.error = "";
        },
        fail: (state) => {
            state.error = "아이디 또는 비밀번호가 존재하지 않습니다.";
        }
    }
})

export const authActions = authSlice.actions;

export default authSlice;