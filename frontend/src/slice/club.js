import { createSlice } from '@reduxjs/toolkit'
import { asyncDeleteClub, asyncEditClub, asyncEnrollClub, asyncGetClub, asyncGetClubUser } from '../action/club';

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
        editClub(state) {},
        deleteClub(state) {},
        getUsers(state, action) {
            state.users = action.payload;
        },
        enrollClub(state) {

        },
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
        builder.addCase(asyncEditClub.fulfilled, (state, action) => {
            
        })
        builder.addCase(asyncDeleteClub.fulfilled, (state) => {
            
        })
        builder.addCase(asyncEnrollClub.fulfilled, (state) => {
            
        })
    }
});

export const clubActions = clubSlice.actions;

export default clubSlice;