import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    resorts: [],
}
const resortSlice = createSlice({
    name: 'resort',
    initialState,
    reducers: {
        getResorts(state, action) {
            state.resorts = action.payload;
        }
    }
});

export const resortActions = resortSlice.actions;

export default resortSlice;