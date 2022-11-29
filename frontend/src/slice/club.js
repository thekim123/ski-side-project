import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    clubs: [],
    club: null,
    users: [],
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
    }
});

export const clubActions = clubSlice.actions;

export default clubSlice;