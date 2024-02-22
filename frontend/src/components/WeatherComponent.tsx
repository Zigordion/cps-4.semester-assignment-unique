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
                const response = await axios.get('http://localhost:8020/api/weather/1')
                console.log(response);
                setWeatherData(response.data);
            } catch (error){
                console.error("Error while fetching weather data: ", error);
            }
        }
        fetchWeatherData();
    },  []);
    console.log(weatherData);
    return (
        <div>
            <h1>hello</h1> 
        </div>
    )
}

export default WeatherComponent