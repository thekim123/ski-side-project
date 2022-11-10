import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    user: null,
    token: null,
}
const testSlice = createSlice({
    name: 'test',
    initialState,
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
    }
});

export const testActions = testSlice.actions;

export default testSlice;