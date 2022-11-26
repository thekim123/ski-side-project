import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    posts: [{
        tayo_id: 2,
        age: "20대",
        createDt: "2022-10-24T16:33:44.936Z",
        tayoMemCnt: 5,
        title: "겨울방학도 했는데 스키장은 가야지!",
        resort_id: 1,
    }],
    post: {
        tayo_id: 2,
        age: "20대",
        createDt: "2022-10-24T16:33:44.936Z",
        tayoMemCnt: 5,
        title: "겨울방학도 했는데 스키장은 가야지!",
        resort_id: 1,
    }
    // null,
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