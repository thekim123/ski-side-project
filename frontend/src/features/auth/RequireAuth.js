import { useLocation, Navigate, Outlet} from "react-router-dom"
import { useSelector } from 'react-redux'

export function RequireAuth () {
    const token = useSelector(state => state.auth.token);
    const { pathname } = useLocation();

    return (
        token
            ? <Outlet />
            : <Navigate to="/login" state={pathname} />
    )
}