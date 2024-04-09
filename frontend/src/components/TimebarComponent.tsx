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
interface TimebarComponentProps{
  setWeatherData: React.Dispatch<React.SetStateAction<WeatherData | undefined>>;
}
const TimebarComponent = ( {setWeatherData}:TimebarComponentProps) => {
  const classes = useStyles();
  function valuetext(value: number) {
    return `${value}`;
  }
  const [timestamps,setTimestamps] = useState<Mark[]>()
  function valChanged(event: Event, value: number){
      const mark = timestamps?.find(mark=> mark.value === value);
      console.log(timestamps)
      console.log(mark , value)
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
      console.log(response.data, timestamp)
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
            console.log(response.data);
            const formattedTime = format(parseISO(response.data.timestamp), "dd MMM yyyy HH:mm:ss");
            response.data.timestamp = formattedTime;
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
          const formattedTime = format(parseISO(timestamp), "dd MMM yyyy HH:mm:ss");
          return {label:formattedTime, value: index/(response.data.length-1)*100}
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