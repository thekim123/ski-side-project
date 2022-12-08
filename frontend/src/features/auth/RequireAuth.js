import { useLocation, Navigate, Outlet} from "react-router-dom"
import { useSelector } from 'react-redux'

export function RequireAuth () {
    //const token = useSelector(state => state.auth.token);
    const isAuth = useSelector(state => state.auth.isAuth);
    const { pathname } = useLocation();

    return (
        isAuth
            ? <Outlet />
            : <Navigate to="/login" state={pathname} />
    )
}