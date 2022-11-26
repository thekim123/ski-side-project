import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    bookmarks: [],
}
const bookmarkSlice = createSlice({
    name: 'bookmark',
    initialState,
    reducers: {
        getBookmarks(state, action) {
            state.bookmarks = action.payload;
        },
        addBookmark(state) {

        },
        deleteBookmark(state){

        }
    }
})

export const bookmarkActions = bookmarkSlice.actions;

export default bookmarkSlice;