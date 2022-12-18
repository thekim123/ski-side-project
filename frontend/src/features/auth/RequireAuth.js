import { useLocation, Navigate, Outlet} from "react-router-dom"
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react';
import { asyncGetUser, getUser } from '../../action/auth';
import Send from '../../components/common/Send'

export function RequireAuth () { 
    const dispatch = useDispatch();
    //const token = useSelector(state => state.auth.token);
    //const user = useSelector(state => state.auth.user);
    const isAuth = useSelector(state => state.auth.isAuth);
    //const [isAut, setIsAut] = useState(null);
    //const isAut = localStorage.getItem('access_token');
    //const isAut = null;
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