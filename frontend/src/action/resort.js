import axios from 'axios';
import { resortActions } from '../slice/resort';
import Send from '../components/common/Send';

export const loadResorts = () => {
    return function (dispatch) {
        axios
            .get(`${process.env.REACT_APP_API}/resort`)
            .then((resp) => {
                dispatch(resortActions.getResorts(resp.data));
            })
            .catch(error => console.log(error));

    }
}