import React,{useEffect, useState} from 'react'

import './TimebarComponent.css'
//import Box from '@mui/material/Box';
import Slider from '@mui/material-next/Slider';
import {makeStyles} from '@mui/styles';
import axios from 'axios'
import { convertTimeDataToReadable } from '../util/Converter';

const useStyles = makeStyles((theme) => ({
    sliderRoot:{
        color: '#282c34',
        size: "medium",
        height: 10,
    },
}));


interface WeatherData{
  id: number ;
  temperature: number | undefined;
  windSpeed:number | undefined;
  windDirection: number | undefined;
  sunMin: number | undefined;
  cloudCoverage: number | undefined;
  humidity: number | undefined;
  rain: number | undefined;
  solarRad: number | undefined;
  timestamp: string;
}

interface TimeStamps{
  timestamps: string[]
}
interface TimebarComponentProps{
  setWeatherData: React.Dispatch<React.SetStateAction<WeatherData | undefined>>;
  timestampMarks: { value: number; label: string }[];
  setTimestampMarks: React.Dispatch<React.SetStateAction<{ value: number; label: string }[]>>;
}
const TimebarComponent = ( {setWeatherData,timestampMarks, setTimestampMarks}:TimebarComponentProps) => {
  const classes = useStyles();
  const [timestamps,setTimestamps] = useState<TimeStamps>()

  function valChanged(event: Event, value: number){
    if(!timestampMarks || !timestamps){
      return;
    }
      const mark = timestampMarks?.find(mark=> mark.value === value);
      
      if(mark){ 
        let timestamp =  mark.label;
        if (timestamp ==="") {
          const index = Math.round((value / 100) * (timestampMarks.length - 1));
          timestamp = timestamps.timestamps[index];
          timestamp = convertTimeDataToReadable(timestamp);
        }
        fetchSpecificTimeData(timestamp)
      }
      else{
        console.error("error while looking determining timestamp in timebar")
      }
  }
  const fetchSpecificTimeData = async(timestamp: string)=>{
    try{
      const response = await axios.get<WeatherData>('http://localhost:8020/api/weather/time/' + timestamp)
      const formattedTime = convertTimeDataToReadable(response.data.timestamp);
      response.data.timestamp = formattedTime;
      setWeatherData(response.data);
    } catch (error){
        console.error("Error while fetching weather data: ", error);
    }
  }
  useEffect(() => {
    const fetchInitialWeatherData = async () => {
        try {
            const response = await axios.get('http://localhost:8020/api/weather/')
            const formattedTime = convertTimeDataToReadable(response.data.timestamp);
            response.data.timestamp = formattedTime;
            setWeatherData(response.data);
        } catch (error) {
            console.error("Error while fetching weather data: ", error);
        }
    }
    fetchInitialWeatherData();
}, [setWeatherData]);
  useEffect(()=>{
    const fetchAllTimeData = async()=>{
      try{
        const response = await axios.get<TimeStamps>('http://localhost:8020/api/weather/time/all')
        setTimestamps(response.data);
        setTimestampMarks(response.data.timestamps.map((timestamp, index) => {
          let label = ""; 
          if (index === 0 || index === response.data.timestamps.length - 1) {
            const formattedTime = convertTimeDataToReadable(timestamp);
            label = formattedTime; 
          }
          return { label, value: index / (response.data.timestamps.length - 1) * 100 };
        }));
      } catch (error){
          console.error("Error while fetching weather data: ", error);
      }
    }
    fetchAllTimeData();
  },  [timestampMarks,setWeatherData, setTimestampMarks]);
  return (
    <div className='barBackground'>
        <Slider className='Slider'
            defaultValue={100}
            step={null}
            size="medium"
            marks={timestampMarks}
            classes={{root: classes.sliderRoot}}
            onChange={valChanged}
        />
    </div>
  )
}


export default TimebarComponent