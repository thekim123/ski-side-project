import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    clubs: [],
    club: null,
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
    }
});

export const clubActions = clubSlice.actions;

export default clubSlice;