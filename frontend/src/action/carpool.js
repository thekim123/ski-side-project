import { createAsyncThunk } from '@reduxjs/toolkit';
import Send from '../components/common/Send';
import { carpoolActions } from '../slice/carpool';

export const loadCarpools = () => {
    return function (dispatch) {
        Send({
            url: '/carpool',
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(carpoolActions.getCarpools(resp.data.data.content));
        })
        .catch((error) => console.log(error));
    }
}

export const addCarpool = (carpool) => {
    return function (dispatch) {
        Send({
            url: '/carpool',
            method: 'post',
            data: carpool, 
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(carpoolActions.addCarpool());
            dispatch(loadCarpools());
        })
        .catch((error) => console.log(error));
    }
}

export const getCarpool = (id) => {
    return function (dispatch) {
        Send({
            url: `/carpool/${id}`,
            method: 'get',
        }).then(resp => {
            console.log("resp", resp);
            dispatch(carpoolActions.getCarpool(resp.data.data));
        })
    }
}

export const editCarpool = (id, carpool) => {
    return function (dispatch) {
        Send({
            url:`/carpool/update/${id}`,
            method: 'put',
            data: carpool,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(carpoolActions.editCarpool());
        })
        .catch(error => console.log(error));
    }
} 

export const asyncGetCarpool = createAsyncThunk(
    'carpoolSlice/asyncGetCarpool',
    async (id) => {
        const resp = await Send({
            url:`/carpool/${id}`,
            method: 'get',
        })
        console.log("get carpool", resp);
        return resp.data.data;
    }
)

export const asyncEditCarpool = createAsyncThunk(
    'carpoolSlice/asyncEditCarpool',
    async (carpool) => {
        const resp = await Send({
            url: `/carpool/update/${carpool.id}`,
            method: 'put',
            data: carpool,
        })
        console.log("resp", resp);
        return resp.data;
    }    
)

export const deleteCarpool = (id) => {
    return function (dispatch) {
        Send({
            url: `/carpool/delete/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            //dispatch(carpoolActions.get());
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error));        
    }
}

export const submitCarpool = (id) => {
    return function (dispatch) {
        Send({
            url: `/submit/${id}`,
            method: 'post',
        }).then((resp) => {
            console.log("resp", resp);
            //dispatch(carpoolActions.get());
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error));
    }    
}

export const getSubmits = (carpoolId) => {
    return function (dispatch) {
        Send({
            url: `/submit/${carpoolId}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(carpoolActions.getSubmits(resp.data.data));
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error));        
    }
}

export const asyncGetSubmits = createAsyncThunk(
    'carpoolSlice/asyncGetSubmits', 
    async (carpoolId) => {
        const resp = await Send({
            url: `/submit/${carpoolId}`,
            method: 'get',
        })
        console.log("resp", resp);
        return resp.data.data;
    }
)

export const admitSubmit = (data) => {
    return function (dispatch) {
        Send({
            url: `/submit/admit`,
            method: 'put',
            data: data,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(getSubmits(data.toCarpoolId))
            //dispatch(carpoolActions.getSubmits(resp.data.data));
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error)); 
    }
}

export const refuseSubmit = (data) => {
    return function (dispatch) {
        Send({
            url: `/submit/refuse`,
            method: 'put',
            data: data,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(getSubmits(data.toCarpoolId))
            //dispatch(carpoolActions.getSubmits(resp.data.data));
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error)); 
    }
}

export const getMyCarSubmit = () => {
    return function (dispatch) {
        Send({
            url: `/submit/getMySubmit`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(carpoolActions.getMySubmit(resp.data.data));
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error)); 
    }    
}