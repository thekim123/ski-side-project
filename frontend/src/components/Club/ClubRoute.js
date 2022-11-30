import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useLocation } from 'react-router-dom';
import { ClubDetail } from './ClubDetail';
import { ClubSecret } from './ClubSecret';

export function ClubRoute() {
    const users = useSelector(state => state.club.users);
    const user = useSelector(state => state.auth.user);
    const [isMember, setIsMember] = useState(false); 
    const club = useLocation().state;

    useEffect(() => {
        if (users.length > 0) {
            const test = users.find(u => u.username === user);
            test !== undefined ? setIsMember(true) : setIsMember(false);
        }
    }, [users])
    return (
    <div>
        {isMember ? <ClubDetail {...club} /> : <ClubSecret />}
    </div>
    )
}

