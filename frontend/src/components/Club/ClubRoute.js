import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { ClubDetail } from './ClubDetail';
import { ClubSecret } from './ClubSecret';
import { getSingleClub, getClubUser } from '../../action/club';

export function ClubRoute() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.club.users);
    const user = useSelector(state => state.auth.user);
    const [isMember, setIsMember] = useState(false); 
    //const club = useLocation().state;
    const {id} = useParams();

    useEffect(() => {
        //dispatch(getSingleClub(id));
        dispatch(getClubUser(id));
    }, [dispatch, id])

    useEffect(() => {
        if (users.length > 0) {
            const test = users.find(u => u.username === user);
            //test !== undefined ? setIsMember(true) : setIsMember(false);
            test !== undefined ? navigate(`/club/detail/${id}`) : navigate(`/club/secret/${id}`);
        }
    }, [users])
    return (
    <div>
    </div>
    )
}

