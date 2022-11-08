import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
    name: 'auth',
    //initialState: { user: null, token: null },
    initialState: { user: {id: "1", username: "syj0396"}, token: "eyejsil"},
    reducers: {
        setCredentials: (state, action) => {
            const { user, accessToken } = action.payload;
            state.user = user;
            state.token = accessToken;
        },
        logOut: (state, action) => {
            state.user = null;
            state.token = null;
        }
    },
})

// export const { setCredentials, logOut } = authSlice.actions;

// export default authSlice;

// export const selectCurrentUser = (state) => state.auth.user;
// export const selectCurrentToken = (state) => state.auth.token;