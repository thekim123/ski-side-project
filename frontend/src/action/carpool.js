import Send from '../components/common/Send';
import { carpoolActions } from '../slice/carpool';

export const loadCarpools = () => {
    return function (dispatch) {
        Send({
            url: '/carpool',
            method: 'get',
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
            //dispatch(loadCarpools());
        })
        .catch((error) => console.log(error));
    }
}