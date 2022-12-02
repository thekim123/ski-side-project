import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    posts: [],
    post: null,
}
const tayoSlice = createSlice({
    name: 'tayo',
    initialState,
    reducers: {
        getTayos(state, action) {
            state.posts = action.payload;
        },
        addTayo(state) {

        },
        getPost(state, action) {
            state.post = action.payload;
        },
        editPost(state) {

        },
        deletePost(state) {
            
        }
    }
});

export const tayoActions = tayoSlice.actions;

export default tayoSlice;