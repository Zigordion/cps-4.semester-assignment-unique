import React, { useEffect, useState } from 'react'
import './GraphComponent.css'
import axios from 'axios';
import { useParams, useNavigate} from 'react-router-dom';
import { LineChart } from '@mui/x-charts';
interface Data{
    timeStamp: string
    value: number
}
const GraphComponent = () => {
    const navigate = useNavigate();
    const [data,setData] = useState<Data[]>()
    const validDataValues = ['temperature', 'wind-speed','sunshine','cloud-coverage','humidity','rain','solar-radiation']
    const valueMappings = {
        'temperature': 'Temperatur',
        'wind-speed': 'Vindstyrke',
        'sunshine': 'Solskin',
        'cloud-coverage': 'Skydække',
        'humidity': 'Luftfugtighed',
        'rain': 'Nedbør',
        'solar-radiation': 'Stråling'
    };
    const {relevantData} = useParams<{relevantData:keyof typeof valueMappings}>();
    
    useEffect(()=>{
        const fetchData = async()=>{
            try{
                console.log(`http://localhost:8020/api/graph/${relevantData}`)
                const response = await axios.get<Data[]>(`http://localhost:8020/api/weather/graph/${relevantData}`)
                console.log(response.data);
                console.log("value:",response.data.at(0)?.value)
                console.log("time:",response.data.at(0)?.timeStamp)

                setData(response.data);
                
            } catch (error){
                console.error("Error while fetching weather data: ", error);
            }
        }
        fetchData();

    },[relevantData])
    if (relevantData === undefined || !validDataValues.includes(relevantData)) {
        navigate("/");
        return null;
    }
    const seriesData = data ? [{ data: data.map(dataPoint => dataPoint.value) }] : [];
    const numericalTimeData = data ? data.map((dataPoint, index) => index + 1) : [];
    const timeLabels = data ? data.map(dataPoint => dataPoint.timeStamp) : [];
    console.log("series data:",seriesData)
    console.log("numericalTime: ", numericalTimeData)
    return (
        <div className='graph-container'>
            <h1>{valueMappings[relevantData]}</h1>
            <LineChart 
                sx={{
                    "& .MuiChartsAxis-left .MuiChartsAxis-tickLabel":{
                        strokeWidth:"0.4",
                        fill:"white"
                    },
                    "& .MuiChartsAxis-bottom .MuiChartsAxis-tickLabel":{
                        strokeWidth:"0.5",
                        fill:"white"
                    },
                    "& .MuiChartsAxis-bottom .MuiChartsAxis-line":{
                        stroke:"white",
                        strokeWidth:0.4
                    },
                    "& .MuiChartsAxis-left .MuiChartsAxis-line":{
                        stroke:"white",
                        strokeWidth:0.4
                    }
                  }}
                xAxis={[
                    {
                        data: numericalTimeData,
                        valueFormatter(value, index) {
                            const intValue = Math.floor(value); // Ensure integer value
                            return timeLabels[intValue - 1]; // Adjust index to match array indexing
                        }
                    },
                ]}
                series={seriesData}
                width={500}
                height={300}
            />
        </div>
    )
}

export default GraphComponent