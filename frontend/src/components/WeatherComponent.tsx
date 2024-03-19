import React from 'react'
import './WeatherComponent.css'
interface WeatherComponentProps{
    value: number | undefined;
    valueName: string;
    imagePath:string;
    altText: string;
}
const WeatherComponent = ({value, valueName, imagePath, altText} : WeatherComponentProps ) => {
    return (
        <div className='weather-component'>
            <img height={50} className='weather-icon' src={imagePath} alt={altText}></img>
            <h3>{valueName} </h3>
            <h3>{value!=null ? value : '0.0'}</h3>
        </div>
    )
}

export default WeatherComponent