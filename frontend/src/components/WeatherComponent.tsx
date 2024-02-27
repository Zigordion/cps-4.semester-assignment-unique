import React,{useEffect, useState} from 'react'
import axios from 'axios'
interface WeatherComponentProps{
    name: string;
}
const WeatherComponent: React.FC = (props) => {
    
    const [weatherData, setWeatherData] = useState<any>(null);
    useEffect(()=>{
        const fetchWeatherData = async()=>{
            try{
                const response = await axios.get('http://localhost:8020/api/weather/temp')
                console.log(response);
                setWeatherData(response.data);
            } catch (error){
                console.error("Error while fetching weather data: ", error);
            }
        }
        fetchWeatherData();
    },  []);
    return (
        <div>
            <h3>Temperature {weatherData}</h3> 
        </div>
    )
}

export default WeatherComponent