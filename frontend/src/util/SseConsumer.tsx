
const createEventSource = (endpoint: string) => {
return new EventSource(`http://localhost:8020/api/weather/${endpoint}`);
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