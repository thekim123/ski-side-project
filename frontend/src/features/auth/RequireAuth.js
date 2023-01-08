import { useLocation, Navigate, Outlet} from "react-router-dom"
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react';
import { asyncGetUser, getUser } from '../../action/auth';
import Send from '../../components/common/Send'

export function RequireAuth () { 
    const dispatch = useDispatch();
    const isAuth = useSelector(state => state.auth.isAuth);
    const { pathname } = useLocation();

    /*
    useEffect(() => {
        const getUserInfo = async () => {
            const result = await dispatch(asyncGetUser()).unwrap();
        }
        let token = localStorage.getItem('access_token');
        if (token) {
            Send.defaults.headers.common['Authorization'] = token;
            setIsAut(true);
            dispatch(getUser()); 
            //getUserInfo();
            console.log(isAut);
        }
    }, [])*/

    return (
        isAuth
            ? <Outlet />
            : <Navigate to="/login" state={pathname} />
    )
}