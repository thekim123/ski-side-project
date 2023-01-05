import { createAsyncThunk } from '@reduxjs/toolkit';
import Send from '../components/common/Send';
import { tayoActions } from '../slice/tayo';

export const loadTayos = () => {
    return function (dispatch) {
        /*
        axios
            .get(`${process.env.REACT_APP_API}/board`)
            .then((resp) => {
                dispatch(boardActions.getBoards(resp.data));
            })
            .catch(error => console.log(error));*/

        Send({
            url: '/tayo',
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log(resp);
            dispatch(tayoActions.getTayos(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const getTayoByResort = (resortName) => {
    return function (dispatch) {
        Send({
            url: `/tayo/${resortName}`,
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log(resortName)
            console.log("resp", resp);
            dispatch(tayoActions.getTayos(resp.data.data));
        })
        .catch(error => console.log(error));
        
    }    
}

export const addTayo = (post) => {
    return function (dispatch) {
        console.log("z");
        Send({
            url: '/tayo',
            method: 'post',
            data: post,
        }).then((resp) => { 
            console.log("resp", resp);
            //dispatch(tayoActions.addTayo());
            dispatch(loadTayos());
        })
        .catch((error) => console.log(error));
    }
}

export const asyncAddTayo = createAsyncThunk(
    'tayoSlice/asyncAddTayo',
    async (post) => {
        const resp = await Send({
            url: '/tayo',
            method: 'post',
            data: post,
        })
        console.log("asyncAddTayo", resp);
        return resp.data.data;
    }
)

export const asyncLoadTayos = createAsyncThunk(
    'tayoSlice/asyncLoadTayos',
    async () => {
        const resp = await Send({
            url: '/tayo',
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        })
        console.log("asyncLoadTayos", resp);
        return resp.data.data.content;
    }
)

export const getSinglePost = (id) => {
    return function (dispatch) {
        Send({
            url: `/tayo/${id}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(tayoActions.getPost(resp.data));
        })
        .catch(error => console.log(error));
        
    }
}

export const updatePost = (post, id) => {
    return function (dispatch) {
        /*
        axios
            .put(`${process.env.REACT_APP_API}/board/${id}`, post)
            .then((resp) => {
                console.log("resp", resp);
                dispatch(boardActions.editPost());
            })
            .catch(error => console.log(error));*/

        Send({
            url: '/tayo',
            method: 'put',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(tayoActions.editPost());
        })
        .catch(error => console.log(error));
    }
}

export const deleteTayo = (id) => {
    return function (dispatch) {
        Send({
            url: `/tayo/delete/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(tayoActions.deletePost());
            dispatch(loadTayos());
        })
        .catch(error => console.log(error));
    }
}