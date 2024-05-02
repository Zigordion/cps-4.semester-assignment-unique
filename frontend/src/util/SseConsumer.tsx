
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

const createEventSource = (endpoint: string) => {
return new EventSource(`http://localhost:8080/api/weather/${endpoint}`);
};
export const weatherData = {
    subscribeWeatherData: function <ReturnDataType>(
        onMessage: (data: ReturnDataType) => void
      ) {
        const eventSource = createEventSource("subscribe");
    
        eventSource.onmessage = (event) => {
          const eventData = JSON.parse(event.data);
          onMessage(eventData);
        };
        return eventSource;
      }
}