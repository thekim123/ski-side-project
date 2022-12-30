import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://15.165.81.194:8080/api',
    withCredentials: true,
});

export default instance;