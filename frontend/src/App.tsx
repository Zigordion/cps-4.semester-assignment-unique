import React from 'react';
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

function App() {
  return (
    <div className="App">
      <div className='display-overview'>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Temperatur' apiEndpoint='temp' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Vindstyrke' apiEndpoint='1' imagePath={windpowerImage} altText='Wind power icon shown here'/>
              <WeatherComponent valueName='Vindretning' apiEndpoint='1' imagePath={directionImage} altText='Wind direction icon shown here'/>
              <WeatherComponent valueName='Solskin' apiEndpoint='1' imagePath={sunshineImage} altText='Sunshine icon shown here'/>
            </div>
            <CenterComponent/>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Skydække' apiEndpoint='1' imagePath={cloudcoverImage} altText='Cloud cover icon shown here'/>
              <WeatherComponent valueName='Luftfugtighed' apiEndpoint='1' imagePath={humidityImage} altText='Humidity icon shown here'/>
              <WeatherComponent valueName='Nedbør' apiEndpoint='1' imagePath={rainImage} altText='Rain icon shown here'/>
              <WeatherComponent valueName='Stråling' apiEndpoint='1' imagePath={radiationImage} altText='Radiation icon shown here'/>
            </div>
      </div>
      
    </div>
  );
}

export default App;
