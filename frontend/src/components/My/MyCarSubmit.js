import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import styled from 'styled-components'
import { getSubmits } from '../../action/carpool';

export default function MyCarSubmit(props) {
    const dispatch = useDispatch();
    const submits = useSelector(state => state.carpool.submits);

    useEffect(() => {
        //dispatch(getSubmits(props.id));
        //console.log(submits);
    }, [])

    useEffect(() => {

    }, [submits])

    return (
    <div>
    </div>
    )
}
