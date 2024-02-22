import React from 'react';
import logo from './logo.svg';
import './App.css';
import WeatherComponent from './components/WeatherComponent';
import CityComponent from './components/CityComponent';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <WeatherComponent/>
        <CityComponent cityName="test"/>
        <CityComponent cityName="odense"/>
        
      </header>
    </div>
  );
}

export default App;
