import { createSlice } from '@reduxjs/toolkit'
import { asyncGetClub, asyncGetClubUser } from '../action/club';

const initialState = {
    clubs: [],
    club: null,
    users: [],
    status: 'test',
}
const clubSlice = createSlice({
    name: 'club',
    initialState,
    reducers: {
        getClubs(state, action) {
            state.clubs = action.payload;
        },
        createClub(state) {},
        getClub(state, action) {
            state.club = action.payload;
        },
        getUsers(state, action) {
            state.users = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(asyncGetClub.pending, (state, action) => {
            state.status = 'loading';
        })
        builder.addCase(asyncGetClub.fulfilled, (state, action) => {
            state.club = action.payload;
            state.status = 'complete';
        })
        builder.addCase(asyncGetClub.rejected, (state, action) => {
            state.status = 'fail';
        })
        builder.addCase(asyncGetClubUser.fulfilled, (state, action) => {
            state.users = action.payload;
        })
    }
});

export const clubActions = clubSlice.actions;

export default clubSlice;