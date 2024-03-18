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
              <WeatherComponent valueName='Vindstyrke' apiEndpoint='te' imagePath={windpowerImage} altText='Wind power icon shown here'/>
              <WeatherComponent valueName='Vindretning' apiEndpoint='te' imagePath={directionImage} altText='Wind direction icon shown here'/>
              <WeatherComponent valueName='Solskin' apiEndpoint='te' imagePath={sunshineImage} altText='Sunshine icon shown here'/>
            </div>
            <CenterComponent/>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Skydække' apiEndpoint='te' imagePath={cloudcoverImage} altText='Cloud cover icon shown here'/>
              <WeatherComponent valueName='Luftfugtighed' apiEndpoint='te' imagePath={humidityImage} altText='Humidity icon shown here'/>
              <WeatherComponent valueName='Nedbør' apiEndpoint='te' imagePath={rainImage} altText='Rain icon shown here'/>
              <WeatherComponent valueName='Stråling' apiEndpoint='te' imagePath={radiationImage} altText='Radiation icon shown here'/>
            </div>
      </div>
      
    </div>
  );
}

export default App;
