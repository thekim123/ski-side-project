import React, { useState } from 'react'
import styled from 'styled-components'
import { IoMdArrowDropdown } from 'react-icons/io';

export function SelectBox(props) {
    //props: toggleSelectBox(), showSelectBox state, 선택지 배열. default value
    //const [selectedItem, setSelectedItem] = useState("--");
    const [showSelectBox, setShowSelectBox] = useState(false);
    const toggleSelectBox = () => {
        setShowSelectBox(!showSelectBox);
    };
    const handleItemClick = (e) => {
        //setSelectedItem(e.target.id);
        setShowSelectBox(false);
        props.func(e.target.id);
    }
    return (
    <Wrapper>
        <label>{props.label}</label>
        <div className="dropdown">
            <div className="dropdown-btn" onClick={toggleSelectBox}>{props.state}<IoMdArrowDropdown className="boardWrite-icon"/></div>
            {showSelectBox && <div className="dropdown-content">
                {
                    props.list.map(item => (
                        <div id={item} className="dropdown-item" onClick={handleItemClick}>{item}</div>
                    ))
                }
                
            </div>}
        </div>
    </Wrapper>
    )
}

const Wrapper = styled.div`
display: grid;
align-items: center;
grid-template-columns: 110px 1fr;
    label {
        text-align: center;
    }
    .dropdown {
        position: relative;
        margin: 10px;
    }

    .dropdown-btn{
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        padding: 10px 13px;
        font-size: 14px;
        //font-weight: bold;
        color: gray;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: 8px;
    }

    .dropdown-content{
        position: absolute;
        padding: 12px;
        margin-top: 5px;
        margin-left: 0;
        background: #fff;
        box-shadow: 3px 3px 10px 6px rgba(0, 0, 0, 0.06);
        font-weight: bold;
        font-size: 12px;
        color: #333;
        border-radius: 8px;
        width: 90%;
        z-index: 99;
    }

    .dropdown-item{
        padding: 7px;
    }

    .dropdown-item:hover{
        background: #fcfcfc;
    }
    .boardWrite-icon{
        padding-left: 4px;
    }
`