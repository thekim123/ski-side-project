/*
import { createStore, applyMiddleware } from "redux";
import logger from "redux-logger";
import reduxThunk from "redux-thunk";
import rootReducer from './reducers/rootReducer';

const middlewares = [reduxThunk];

if (process.env.NODE_ENV === "development") {
    middlewares.push(logger)
}

const store = createStore(rootReducer, applyMiddleware(...middlewares));

export default store;*/

import { configureStore } from '@reduxjs/toolkit';
import boardSlice from './slice/board';
import clubSlice from './slice/club';
import resortSlice from './slice/resort';
//import { apiSlice } from './app/api/apiSlice';
//import authSlice from './features/auth/authSlice';
import authSlice from './slice/auth';

const store = configureStore({
    reducer: {
        resort: resortSlice.reducer,
        board: boardSlice.reducer,
        club: clubSlice.reducer,
        //[apiSlice.reducerPath]: apiSlice.reducer,
        auth: authSlice.reducer,
    },
    // middleware: getDefaultMiddleware =>
    //     getDefaultMiddleware().concat(apiSlice.middleware),
    // devTools: true
});

export default store;