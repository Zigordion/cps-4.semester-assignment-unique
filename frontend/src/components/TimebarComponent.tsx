import React from 'react'
import './TimebarComponent.css'
//import Box from '@mui/material/Box';
import Slider from '@mui/material-next/Slider';
import {makeStyles} from '@mui/styles';

const useStyles = makeStyles((theme) => ({
    sliderRoot:{
        color: '#282c34',
        size: "medium",
        height: 10,
    },
}));

const marks = [

    {
        value: 0,
        label: 'Timestamp 0',
      },
      {
        value: 50,
        label: 'timestamp 1',
      },
      {
        value: 100,
        label: 'timestamp X',
      },

]


const TimebarComponent = () => {
    const classes = useStyles();
    function valuetext(value: number) {
        return `${value}°C`;
      }

  return (
    <div className='barBackground'>
            
        <Slider className='Slider'
            aria-label="Small steps"
            defaultValue={0.00000005}
            getAriaValueText={valuetext}
            step={null}
            size="medium"
            marks={marks}
            valueLabelDisplay="auto"
            classes={{root: classes.sliderRoot}}
        />
    </div>
  )
}


export default TimebarComponent