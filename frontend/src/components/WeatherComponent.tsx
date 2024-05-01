import React from 'react'
import { useNavigate } from 'react-router-dom';
import './WeatherComponent.css'
interface WeatherComponentProps{
    value: number | string | undefined;
    valueName: string;
    imagePath:string;
    altText: string;
    unit:string;
    page:string
}
const WeatherComponent = ({value, valueName, imagePath, altText,unit, page} : WeatherComponentProps ) => {
    const navigate = useNavigate();
    const handleClick = ()=>{
        navigate(page)
    }
    return (
        <div className='weather-component' onClick={handleClick}>
            <span className='icon-name'>
                <img height={50} className='weather-icon' src={imagePath} alt={altText}></img>
                <h3>{valueName}</h3>
            </span>
            <h3>{value!=null ? value : 'N/A'} {unit}</h3>
        </div>
    )
}

export default WeatherComponent