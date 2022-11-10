import axios from 'axios';
import { authActions } from '../slice/auth';
import Send from '../components/common/Send';

export const login = (user) => {
    return function (dispatch) {
        
        axios(
            {
                url:'/login',
                method: 'post',
                baseURL: 'http://localhost:8080',
                withCredentials: true,
                data: user,
            }
        ).then(resp => {
            console.log("resp", resp);
            const accessToken = resp.headers.authorization;
            if (!accessToken) {
                dispatch(authActions.fail());
            } else {
                dispatch(authActions.setCredentials(accessToken));
                Send.defaults.headers.common['Authorization'] = accessToken;
                dispatch(getUser());
            }
        })
        .catch(error => console.log(error));
    }
}

export const getUser = () => {
    return function (dispatch) {
        Send({
            url: '/user/get',
            method: 'get',
            //data: user,
        }).then(resp => {
            console.log("resp", resp);
            dispatch(authActions.setUser(resp.data.data.username));
        })
        .catch(error => {
            console.log(error);
            //dispatch(authActions.fail());
        });
    }
}