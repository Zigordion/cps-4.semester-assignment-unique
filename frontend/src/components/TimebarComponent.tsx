import React,{useEffect, useState} from 'react'

import './TimebarComponent.css'
//import Box from '@mui/material/Box';
import Slider from '@mui/material-next/Slider';
import {makeStyles} from '@mui/styles';
import axios from 'axios'

const useStyles = makeStyles((theme) => ({
    sliderRoot:{
        color: '#282c34',
        size: "medium",
        height: 10,
    },
}));


interface WeatherData{
  id: number;
  temperature: number;
  windSpeed:number;
  windDirection: number;
  sunMin: number;
  cloudCoverage: number;
  humidity: number;
  rain: number;
  solarRad: number;
  timestamp: string;
}
interface Mark{
  value: number;
  label: string;
}
interface TimebarComponent{
  setWeatherData: React.Dispatch<React.SetStateAction<WeatherData | undefined>>;
}
const TimebarComponent = ( {setWeatherData}:TimebarComponent) => {
  const classes = useStyles();
  function valuetext(value: number) {
    return `${value}`;
  }
  const [timestamps,setTimestamps] = useState<Mark[]>()

  function valChanged(event: Event, value: number){
      fetchSpecificTimeData(value)
  }

  const fetchSpecificTimeData = async(timestamp: number)=>{
    try{
        const response = await axios.get('http://localhost:8020/api/weather/' + timestamp)
        console.log(response.data);
        setWeatherData(response.data);
    } catch (error){
        console.error("Error while fetching weather data: ", error);
    }
  }

  useEffect(()=>{
    const fetchInitialWeatherData = async()=>{
        try{
            const response = await axios.get('http://localhost:8020/api/weather/')
            console.log(response.data);
            setWeatherData(response.data);
        } catch (error){
            console.error("Error while fetching weather data: ", error);
        }
    }
    const fetchAllTimeData = async()=>{
      try{
        const response = await axios.get<string[]>('http://localhost:8020/api/weather/time/all')
        console.log(response.data);
        setTimestamps(response.data.map((timestamp, index) =>{
          return {label:timestamp, value: index/(response.data.length-1)*100}
        }));
      } catch (error){
          console.error("Error while fetching weather data: ", error);
      }
    }
    fetchAllTimeData();
    fetchInitialWeatherData();
  },  []);

  return (
    <div className='barBackground'>
        <Slider className='Slider'
            aria-label="Small steps"
            defaultValue={100}
            getAriaValueText={valuetext}
            step={null}
            size="medium"
            marks={timestamps}
            valueLabelDisplay="auto"
            classes={{root: classes.sliderRoot}}
            onChange={valChanged}
        />
    </div>
  )
}


export default TimebarComponent