import React from 'react';
import './App.css';
import WeatherComponent from './components/WeatherComponent';
import temperatureImage from "./images/temperature.png";
import CenterComponent from './components/CenterComponent';

function App() {
  return (
    <div className="App">
      <div className='display-overview'>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Temperature' apiEndpoint='temp' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
            </div>
            <CenterComponent/>
            <div className='weather-component-container'>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
              <WeatherComponent valueName='Temperature' apiEndpoint='te' imagePath={temperatureImage} altText='Temperature icon shown here'/>
            </div>
      </div>
      
    </div>
  );
}

export default App;
