import React,{useEffect, useState} from 'react'
import axios from 'axios'
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
            <h3>{valueName} {value!=null ? value : '0.0'}</h3>
            <img height={50} className='weather-icon' src={imagePath} alt={altText}></img>
        </div>
    )
}

export default WeatherComponent