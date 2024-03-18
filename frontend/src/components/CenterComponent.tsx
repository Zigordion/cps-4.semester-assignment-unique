import React,{useEffect, useState} from 'react'
import axios from 'axios'
import './CenterComponent.css'
import TimeComponent from './WeatherComponent'
import cloudyImage from "../images/mainIcons/Cloudy.png";
import rainyImage from "../images/mainIcons/Rainy.png";
import sunnyImage from "../images/mainIcons/Sunny.png";
import windyImage from "../images/mainIcons/Windy.png";


const CenterComponent = () => {

let overallImage;

  const [timeData, setTimeData] = useState<number>();
  useEffect(()=>{
      const fetchTimeData = async()=>{
          try{
              const response = await axios.get('http://localhost:8020/api/weather/time')
              console.log(response.data);
              setTimeData(response.data);
          } catch (error){
              console.error("Error while fetching time data: ", error);
          }
      }
      fetchTimeData();
  },  []);



const [overallData, setOverallData] = useState<number>();
useEffect(()=>{
    const fetchOverall = async()=>{
        try{
            const response = await axios.get('http://localhost:8020/api/weather/overall')
            console.log(response.data);
            setOverallData(response.data);
        } catch (error){
            console.error("Error while fetching overall weather data", error);
        }
    }
        fetchOverall();

        if (overallData == 1) {
            overallImage = rainyImage;
        } else if (overallData == 2) {
            overallImage = windyImage;
        } else if (overallData == 3) {
            overallImage = cloudyImage;
        } else {
            overallImage = sunnyImage;
        }
        
       
}, [])



  return (
    <div className='container'>
        <h1>Odense<br/>Vejrudsigt</h1>
        <h1>{timeData}</h1>
        <img src={overallImage}/>
    </div>
  )
}


export default CenterComponent