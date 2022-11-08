import React, { useRef, useState, useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'

import { useDispatch, useSelector } from 'react-redux'
//import { setCredentials } from './authSlice'
//import { useLoginMutation } from './authApiSlice'
//import { loginAction } from '../../action/test'
import { login } from '../../action/auth';

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

    const dispatch = useDispatch();

    /* 계속 rendering되는 error 발생.
    useEffect(() => {
        userRef.current.focus();
    }, []);

    useEffect(() => {
        setErrMsg('');
    }, [user, pw]);*/

    useEffect(() => {
        /*
        if (!isLoading) {
            if (error) {
                setErrMsg(error);
            }
            //원래 가려던 페이지가 있다면 그곳으로, 없다면 홈으로.
            else if (state) {
                navigate(state);
            } else {
                navigate('/');
            }
        }*/
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
    }

    const handleUserInput = (e) => setUser(e.target.value);

    const handlePwInput = (e) => setPw(e.target.value);

    const content = //isLoading ? <h1>Loading...</h1> : (
        <section className="login">
            <p ref={errRef} className={errMsg ? "reemsg": "offscreen"} aria-live="assertive">{errMsg}</p>
        
            <h1>Login</h1>

            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username:</label>
                <input 
                    type="text"
                    id="username"
                    ref={userRef}
                    value={user}
                    onChange={handleUserInput}
                    autoComplete="off"
                    required/>
                <br />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    onChange={handlePwInput}
                    value={pw}
                    required
                />
                <br/>
                <button>Sign In</button>
            </form>
        </section>
    //)

    return content
}

