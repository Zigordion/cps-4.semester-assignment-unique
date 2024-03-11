import React,{useEffect, useState} from 'react'
import axios from 'axios'
import './WeatherComponent.css'
interface WeatherComponentProps{
    apiEndpoint: string;
    valueName: string;
    imagePath:string;
    altText: string;
}
const WeatherComponent = ({apiEndpoint, valueName, imagePath, altText} : WeatherComponentProps ) => {
    
    const [weatherData, setWeatherData] = useState<any>(null);
    useEffect(()=>{
        const fetchWeatherData = async()=>{
            try{
                const response = await axios.get('http://localhost:8020/api/weather/' + apiEndpoint)
                console.log(response);
                setWeatherData(response.data);
            } catch (error){
                console.error("Error while fetching weather data: ", error);
            }
        }
        fetchWeatherData();
    },  []);
    return (
        <div className='weather-component'>
            <h3>{valueName} {weatherData}</h3>
            <img height={50} className='weather-icon' src={imagePath} alt={altText}></img>
        </div>
    )
}

export default WeatherComponent