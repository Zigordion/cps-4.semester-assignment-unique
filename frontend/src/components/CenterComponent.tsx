import React,{useEffect, useState} from 'react'
import axios from 'axios'
import './CenterComponent.css'
import TimeComponent from './WeatherComponent'
import cloudyImage from "../images/mainIcons/Cloudy.png";
import rainyImage from "../images/mainIcons/Rainy.png";
import sunnyImage from "../images/mainIcons/Sunny.png";
import windyImage from "../images/mainIcons/Windy.png";


const CenterComponent = () => {

  const [timeData, setTimeData] = useState<any>();
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


const [overallText, setOverallText] = useState<string>();

const [overallImage, setOverallImage] = useState<string>();
useEffect(()=>{
    let overallData: number;
    const fetchOverall = async()=>{
        try{
            const response = await axios.get('http://localhost:8020/api/weather/overall')
            console.log(response.data);
            overallData = response.data;
        } catch (error){
            console.error("Error while fetching overall weather data", error);
        }
        if (overallData == 1) {
            setOverallImage(rainyImage);
            setOverallText("Regn")
        } else if (overallData == 2) {
            setOverallImage(windyImage);
            setOverallText("Blæsende")
        } else if (overallData == 3) {
            setOverallImage(cloudyImage);
            setOverallText("Skyet")
        } else {
            setOverallImage(sunnyImage);
            setOverallText("Klart")
        }
    }
    fetchOverall();

    
}, [])
  return (
    <div className='container'>
        <h1 className='title'>Odense<br/>Vejrudsigt</h1>
        <p className='timedata'>{timeData}</p>
        <img className='oImage' src={overallImage} width={250}/>
        <p className='overalltext'>{overallText}</p>
    </div>
  )
}


export default CenterComponent