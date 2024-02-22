import React,{useEffect, useState} from 'react'
import axios from 'axios'

const WeatherComponent: React.FC = () => {
    const [weatherData, setWeatherData] = useState<any>(null);
    useEffect(()=>{
        const fetchWeatherData = async()=>{
            try{
                const response = await axios.get('http://localhost:8020/api/weather/1')
                setWeatherData(response.data);
            } catch (error){
                console.error("Error while fetching weather data: ", error);
            }
        }
        fetchWeatherData();
    },  []);
    console.log(weatherData);
    return (
        <div>WeatherComponent</div>
    )
}

export default WeatherComponent