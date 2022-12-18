import { createSlice } from '@reduxjs/toolkit';
import { asyncEditCarpool, asyncGetSubmits } from '../action/carpool';

const initialState = {
    carpools: [],
    carpool: null,
    submits: [],
    my: [],
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
        },
        getSubmits(state, action) {
            state.submits = action.payload;
        },
        getMySubmit(state, action) {
            state.my = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(asyncGetSubmits.fulfilled, (state, action) => {
            state.submits = action.payload;
        })
        builder.addCase(asyncEditCarpool.fulfilled, (state) => {
            
        })
    }
})

export const carpoolActions = carpoolSlice.actions;

export default carpoolSlice;