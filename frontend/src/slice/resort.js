import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    resorts: [],
    my: null,
}
const resortSlice = createSlice({
    name: 'resort',
    initialState,
    reducers: {
        getResorts(state, action) {
            state.resorts = action.payload;
        },
        addMyResort(state) {

        },
        deleteMyResort(state) {
            
        }
    }
});

export const resortActions = resortSlice.actions;

export default resortSlice;