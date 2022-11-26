import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    posts: [],
    post: null,
}
const boardSlice = createSlice({
    name: 'board',
    initialState,
    reducers: {
        getBoards(state, action) {
            state.posts = action.payload;
        },
        addBoard(state) {

        },
        getPost(state, action) {
            state.post = action.payload;
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
            
        }
    }
});

export const boardActions = boardSlice.actions;

export default boardSlice;