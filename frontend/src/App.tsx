import React,{useEffect, useState} from 'react'
import axios from 'axios'
import './App.css';
import WeatherComponent from './components/WeatherComponent';
import temperatureImage from "./images/sideIcons/temperature.png";
import humidityImage from "./images/sideIcons/Luftfugtighed.png";
import rainImage from "./images/sideIcons/Nedboer.png";
import cloudcoverImage from "./images/sideIcons/Skydaekke.png";
import radiationImage from "./images/sideIcons/Straaling.png";
import directionImage from "./images/sideIcons/Vindretning.png";
import windpowerImage from "./images/sideIcons/Vindstyrke.png";
import sunshineImage from "./images/sideIcons/Solskin.png";
import CenterComponent from './components/CenterComponent';
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
function App() {
  const [weatherData, setTimeData] = useState<WeatherData>();
  useEffect(()=>{
      const fetchTimeData = async()=>{
          try{
              const response = await axios.get('http://localhost:8020/api/weather/')
              console.log(response.data);
              setTimeData(response.data);
          } catch (error){
              console.error("Error while fetching time data: ", error);
          }
      }
      fetchTimeData();
  },  []);
  return (
    <div className="App">
      <div className='display-overview'>
            <div className='weather-component-container'>

              <WeatherComponent valueName='Temperatur' value={weatherData?.temperature} imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Vindstyrke' value={weatherData?.windSpeed} imagePath={windpowerImage} altText='Wind power icon shown here'/>
              <WeatherComponent valueName='Vindretning' value={weatherData?.windDirection} imagePath={directionImage} altText='Wind direction icon shown here'/>
              <WeatherComponent valueName='Solskin' value={weatherData?.sunMin} imagePath={sunshineImage} altText='Sunshine icon shown here'/>
            </div>
            <CenterComponent/>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Skydække' value= {weatherData?.cloudCoverage} imagePath={cloudcoverImage} altText='Cloud cover icon shown here'/>
              <WeatherComponent valueName='Luftfugtighed' value={weatherData?.humidity} imagePath={humidityImage} altText='Humidity icon shown here'/>
              <WeatherComponent valueName='Nedbør' value={weatherData?.rain} imagePath={rainImage} altText='Rain icon shown here'/>
              <WeatherComponent valueName='Stråling' value={weatherData?.solarRad} imagePath={radiationImage} altText='Radiation icon shown here'/>
            </div>
      </div>
      
    </div>
  );
}

export default App;
