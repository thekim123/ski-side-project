import { createSlice } from '@reduxjs/toolkit';
import { asyncAddTayo, asyncGetTayoByResort, asyncLoadTayos } from '../action/tayo';

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
    },
    extraReducers: (builder) => {
        builder.addCase(asyncAddTayo.fulfilled, (state) => {
            
        })
        builder.addCase(asyncLoadTayos.fulfilled, (state, action) => {
            state.posts = action.payload;
        })
        builder.addCase(asyncGetTayoByResort.fulfilled, (state, action) => {
            state.posts = action.payload;
        })
    }
});

export const tayoActions = tayoSlice.actions;

export default tayoSlice;