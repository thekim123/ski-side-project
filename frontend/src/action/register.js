import axios from 'axios';

export const register = () => {
    return function(dispatch) {
        axios(
            {
                url: '/api/user/join',
                method: 'post',
                baseURL: 'http://localhost:8080',
                withCredentials: true,
                data: {
                    username: "syj0395",
                    password: "1234",
                    email: "syj0395@naver.com"
                }
            }
        ).then(resp => {
            console.log("resp", resp);
        })
        .catch(error => console.log(error));
    }
}