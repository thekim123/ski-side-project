import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    carpools: [],
    carpool: null,
}
const carpoolSlice = createSlice({
    name: 'carpool',
    initialState,
    reducers: {
        getCarpools(state, action) {
            state.carpools = action.payload;
        },
        addCarpool(state) {
            
        },
        editCarpool(state) {
            
        },
        getCarpool(state, action) {
            state.carpool = action.payload;
        }
    }
})

export const carpoolActions = carpoolSlice.actions;

export default carpoolSlice;