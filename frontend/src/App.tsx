import React,{useEffect, useState} from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
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
import TimebarComponent from './components/TimebarComponent';
import { convertWindDirection, convertSunToPercent } from './util/Converter';
import NavBarComponent from './components/NavBarComponent';
import GraphComponent from './components/GraphComponent';
import { weatherData as weatherDataSSE } from './util/SseConsumer';
import { convertTimeDataToReadable } from './util/Converter';

interface WeatherData{
  id: number;
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
function App() {
  const [timestampMarks, setTimestampMarks] = useState<{ value: number; label: string }[]>([]);

  useEffect(()=>{
    const weatherDataEventSource = weatherDataSSE.subscribeWeatherData(
      (eventData: WeatherData) => {
        let {id,temperature, windDirection, sunMin, cloudCoverage, rain, solarRad, timestamp, humidity,windSpeed } = eventData;
        timestamp = convertTimeDataToReadable(timestamp);
        
        setWeatherData({
          id,
          temperature,
          windSpeed,
          windDirection,
          sunMin,
          cloudCoverage,
          humidity,
          rain,
          solarRad,
          timestamp
        } as WeatherData);
        setTimestampMarks(prevMarks => {
          const newMarks = [...prevMarks];
          newMarks.push({ value: newMarks.length, label: timestamp });
          return newMarks;
        });
      }
    );
    return()=>{
      weatherDataEventSource.close();
    }
  }, [])
  const [weatherData, setWeatherData] = useState<WeatherData>();
  return (
    <Router>
      <div className="App">
        <NavBarComponent/>
        <Routes>
          <Route path='/' element={
            <div>
              <div className='display-overview'>
                <div className='weather-component-container'>
                  <WeatherComponent page='temperature' valueName='Temperatur' unit='°C' value={weatherData?.temperature} imagePath={temperatureImage} altText='Temperature icon shown here'/>
                  <WeatherComponent page='wind-speed' valueName='Vindstyrke' unit='m/s' value={weatherData?.windSpeed} imagePath={windpowerImage} altText='Wind power icon shown here'/>
                  <WeatherComponent page='' valueName='Vindretning' unit='' value={convertWindDirection(weatherData?.windDirection)} imagePath={directionImage} altText='Wind direction icon shown here'/>
                  <WeatherComponent page='sunshine' valueName='Solskin' unit='%' value={convertSunToPercent(weatherData?.sunMin)} imagePath={sunshineImage} altText='Sunshine icon shown here'/>
                </div>
                <CenterComponent timeData={weatherData?.timestamp}/>
                <div className='weather-component-container'>
                  <WeatherComponent page='cloud-coverage' valueName='Skydække' unit='%' value= {weatherData?.cloudCoverage} imagePath={cloudcoverImage} altText='Cloud cover icon shown here'/>
                  <WeatherComponent page='humidity' valueName='Luftfugtighed' unit='%' value={weatherData?.humidity} imagePath={humidityImage} altText='Humidity icon shown here'/>
                  <WeatherComponent page='rain' valueName='Nedbør' unit='mm' value={weatherData?.rain} imagePath={rainImage} altText='Rain icon shown here'/>
                  <WeatherComponent page='solar-radiation' valueName='Stråling' unit='W/m2' value={weatherData?.solarRad} imagePath={radiationImage} altText='Radiation icon shown here'/>
                </div>
              </div>
              <TimebarComponent setWeatherData={setWeatherData} timestampMarks={timestampMarks} setTimestampMarks={setTimestampMarks}/>
            </div>
          }/>
          <Route path='/:relevantData' element={
            <GraphComponent/>
          }/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
