import React,{useEffect, useState} from 'react'

import './TimebarComponent.css'
//import Box from '@mui/material/Box';
import Slider from '@mui/material-next/Slider';
import {makeStyles} from '@mui/styles';
import axios from 'axios'
import {format, parseISO} from "date-fns";

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
interface Mark{
  value: number;
  label: string;
}
interface TimeStamps{
  timestamps: string[]
}
interface TimebarComponentProps{
  setWeatherData: React.Dispatch<React.SetStateAction<WeatherData | undefined>>;
}
const TimebarComponent = ( {setWeatherData}:TimebarComponentProps) => {
  const classes = useStyles();
  const [timestamps,setTimestamps] = useState<Mark[]>()
  function valChanged(event: Event, value: number){
      const mark = timestamps?.find(mark=> mark.value === value);
      if(mark){ //error when there's only 1 datapoint
        const timestamp = mark.label;
        fetchSpecificTimeData(timestamp)
      }
      else{
        console.error("error while looking determining timestamp in timebar")
      }
  }
  const fetchSpecificTimeData = async(timestamp: string)=>{
    try{
      const response = await axios.get<WeatherData>('http://localhost:8020/api/weather/time/' + timestamp)
      const formattedTime = format(parseISO(response.data.timestamp), "dd MMM yyyy HH:mm:ss");
      response.data.timestamp = formattedTime;
      setWeatherData(response.data);
    } catch (error){
        console.error("Error while fetching weather data: ", error);
    }
  }

  useEffect(()=>{
    const fetchInitialWeatherData = async()=>{
        try{
            const response = await axios.get('http://localhost:8020/api/weather/')
            const formattedTime = format(parseISO(response.data.timestamp), "dd MMM yyyy HH:mm:ss");
            response.data.timestamp = formattedTime;
            setWeatherData(response.data);
        } catch (error){
            console.error("Error while fetching weather data: ", error);
        }
    }
    const fetchAllTimeData = async()=>{
      try{
        const response = await axios.get<TimeStamps>('http://localhost:8020/api/weather/time/all')
        setTimestamps(response.data.timestamps.map((timestamp, index) =>{
          const formattedTime = format(parseISO(timestamp), "dd MMM yyyy HH:mm:ss");
          return {label:formattedTime, value: index/(response.data.timestamps.length-1)*100}
        }));
      } catch (error){
          console.error("Error while fetching weather data: ", error);
      }
    }
    fetchAllTimeData();
    fetchInitialWeatherData();
  },  [setWeatherData]);
  return (
    <div className='barBackground'>
        <Slider className='Slider'
            defaultValue={100}
            step={null}
            size="medium"
            marks={timestamps}
            classes={{root: classes.sliderRoot}}
            onChange={valChanged}
        />
    </div>
  )
}


export default TimebarComponent