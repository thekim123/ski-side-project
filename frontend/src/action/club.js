import axios from 'axios';
import { clubActions } from '../slice/club';
import Send from '../components/common/Send';

export const loadClubs = () => {
    return function (dispatch) {
        Send({
            url: '/club',
            method: 'get',
        }).then((resp) => {
            console.log(resp);
            dispatch(clubActions.getClubs(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const regClub = (post) => {
    return function (dispatch) {
        Send({
            url:'/club',
            method: 'post',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubActions.createClub());
            //dispatch(loadPosts)
        })
        .catch(error => console.log(error));
    }
}

export const getSingleClub = (id) => {
    return function (dispatch) {
        axios
            .get(`${process.env.REACT_APP_API}/club/${id}`)
            .then((resp) => {
                dispatch(clubActions.getClub(resp.data));
            })
            .catch(error => console.log(error));
    }
}