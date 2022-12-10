import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    clubBoards: [],
    clubBoard: null,
}
const clubBoardSlice = createSlice({
    name: 'clubBoard',
    initialState,
    reducers: {
        getBoards(state, action) {
            state.clubBoards = action.payload;
        },
        addBoard(state) {

        },
        getPost(state, action) {
            state.clubBoard = action.payload;
            console.log(action.payload);
        },
        editPost(state) {

        },
        deletePost(state) {
            
        },
        likes(state) {

        },
        unlikes(state) {
            
        },
        addComment(state) {
            
        },
        deleteComment(state){

        },
    }
});

export const clubBoardActions = clubBoardSlice.actions;

export default clubBoardSlice;