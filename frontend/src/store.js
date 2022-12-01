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

//createAsyncThunk는 비동기 작업을 처리하는 action을 만들어준다.
//createAsyncThunk를 사용하면 pending, fulfilled, rejected가 자동으로 만들어짐.
//동기적인 작업은 reducers를 사용 - 
//비동기적인 작업은 extraReducers를 사용 - 

import { configureStore } from '@reduxjs/toolkit';
import boardSlice from './slice/board';
import clubSlice from './slice/club';
import resortSlice from './slice/resort';
//import { apiSlice } from './app/api/apiSlice';
//import authSlice from './features/auth/authSlice';
import authSlice from './slice/auth';
import tayoSlice from './slice/tayo';
import bookmarkSlice from './slice/bookmark';
import carpoolSlice from './slice/carpool';
import clubBoardSlice from './slice/clubBoard';

const store = configureStore({
    reducer: {
        resort: resortSlice.reducer,
        board: boardSlice.reducer,
        club: clubSlice.reducer,
        clubBoard: clubBoardSlice.reducer,
        tayo: tayoSlice.reducer,
        carpool: carpoolSlice.reducer,
        bookmarks: bookmarkSlice.reducer,
        auth: authSlice.reducer,
    },
    // middleware: getDefaultMiddleware =>
    //     getDefaultMiddleware().concat(apiSlice.middleware),
    // devTools: true
});

export default store;