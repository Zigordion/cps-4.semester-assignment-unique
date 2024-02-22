import React from 'react'

interface CityComponentProps{
    cityName: string
}
const CityComponent = ({cityName}:CityComponentProps) => {
  return (
    <div>{cityName}</div>
  )
}

export default CityComponent