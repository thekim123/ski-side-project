import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    my: null,
}
const resortSlice = createSlice({
    name: 'resort',
    initialState,
    reducers: {
        addMyResort(state) {

        },
        deleteMyResort(state) {
            
        }
    }
});

export const resortActions = resortSlice.actions;

export default resortSlice;