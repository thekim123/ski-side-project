import axios from 'axios';
import { resortActions } from '../slice/resort';
import Send from '../components/common/Send';

/*
export const loadResorts = () => {
    return function (dispatch) {
        axios
            .get(`${process.env.REACT_APP_API}/resort`)
            .then((resp) => {
                dispatch(resortActions.getResorts(resp.data));
            })
            .catch(error => console.log(error));

    }
}*/

export const addMyResort = (id) => {
    return function (dispatch) {
        Send({
            url: `/bookmark/${id}`,
            method: 'post',
        }).then((resp) => {
            console.log(resp);
            dispatch(resortActions.addMyResort());
        })
        .catch(error => console.log(error));
    }
}

export const deleteMyResort = (id) => {
    return function (dispatch) {
        Send({
            url: `/bookmark/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log(resp);
            dispatch(resortActions.deleteMyResort());
        })
        .catch(error => console.log(error));
    }
}