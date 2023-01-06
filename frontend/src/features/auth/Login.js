import React, { useRef, useState, useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'

import { useDispatch, useSelector } from 'react-redux'
//import { setCredentials } from './authSlice'
//import { useLoginMutation } from './authApiSlice'
//import { loginAction } from '../../action/test'
import { login } from '../../action/auth';
import styled from 'styled-components'
import {FaSkiing} from 'react-icons/fa'
import { BsFillChatFill } from 'react-icons/bs'

const { Kakao } = window;

export function Login() {
    const userRef = useRef();
    const errRef = useRef();
    const [user, setUser] = useState('');
    const [pw, setPw] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const navigate = useNavigate();
    const { state } = useLocation();
    const isAuth = useSelector(state => state.auth.isAuth);
    const error = useSelector(state => state.auth.error);

    const REDIRECT_URI = 'http://15.165.81.194:3000/kakaoLogin';
    const REST_API_KEY = '30d8d88d8914487594ffefdce38681cc'

    //const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code`
    const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`

    const handleLogin = () => {
        window.location.href=KAKAO_AUTH_URL;
    }

    /*
    useEffect(() => {
        if (isAuth) {
            if (state) {
                navigate(state);
            } else {
                navigate('/');
            }
        }
        else if (error) {
            setErrMsg(error);
        }
    }, [isAuth, error]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const userData = {
            username: user, 
            password: pw
        };
        dispatch(login(userData));
        setUser('');
        setPw('');
    }*/

    
    return (
        <Wrapper>
            <MySki>
                <FaSkiing className="home-ski-icon" />
            </MySki>
            <AppName>같이 타요!</AppName>
            <Button onClick={handleLogin}><BsFillChatFill className='login-icon'/><span>카카오로 시작하기</span></Button>
        </Wrapper>
    )
}

const Wrapper = styled.div`
display: grid;
align-items: center;
justify-items: center;
margin-top: 170px;
`
const MySki = styled.button`
background-color: var(--button-color);
box-shadow: 0 0 1px 1px rgba(17, 20, 24, 0.1);
border-radius: 50%;
margin-bottom: 10px;
border: none;
width: 110px;
height: 110px;
text-align: center;

.home-ski-icon {
    width: 25px;
    height: 25px;
    color: #FAFAFA;
}
`
const AppName = styled.div`
font-family: nanum-square-bold;
font-size: 20px;
margin-bottom: 60px;
`
const Button = styled.button`
display: flex;
padding: 15px 60px;
border-radius: 10px;
border: none;
background-color: #FEE500;
font-family: nanum-square-bold;
.login-icon{
    justify-self: center;
    margin-right: 6px;
}
`