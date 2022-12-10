import React from 'react'

export default function Popup(props) {

    return (
    <div>
        {props.open && <div>신청 완료</div>}
    </div>
    )
}
