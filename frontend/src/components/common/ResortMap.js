import React from 'react'
import resortData from '../../data/resort.json'
import Grid from '@material-ui/core/Grid';
import { SkiButton } from '../SkiButton'
import img from '../../imgs/한반도.png'
import styled from 'styled-components'
import shortid from 'shortid';

export function ResortMap() {
    const selectResort = e => {
        console.log(e.target);
    }
    return (
        <Map>
            <Grid container className="home-grid">
                {
                    resortData.map(resort => (
                        <Grid item xs={6} className="home-grid-item" key={shortid.generate()}>
                            {resort.id ? 
                            <Button>
                                <Region>{resort.region}</Region>
                                <Resort id={resort.id} onClick={selectResort}>{resort.name}</Resort>
                            </Button>
                            : <Empty></Empty>}
                        </Grid>
                    ))
                }
            </Grid>
        </Map>
    )
}
const Button = styled.div`
display: flex;
//background-color:#6B89A5;
//border-radius: 15px;
width: 100%;
padding: 7px;
font-size: 13px;
`
const Region = styled.div`
padding: 7px;
background-color: rgba(0, 0, 0, 0.6);
border-radius: 6px;
color: #FAFAFA;
`
const Resort = styled.div`
font-weight: 400;
padding: 10px;
background-color: #FAFAFA;
box-shadow: 4px 6px 6px -2px rgba(17, 20, 24, 0.15);
border-radius: 6px;
padding-right: 9px;
`
const Map = styled.div`
//background-image: url(${img});
background:url(${img}) no-repeat center;
background-size: fill;
width: 100%;
height: 490px;
.home-grid-item{
    height: 55px;
}
`
const Empty = styled.div`
width: 10px;
`