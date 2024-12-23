## Worldwide Windsurfer’s Weather Service

This application provides weather forecasts for several popular windsurfing locations around the world, using the Weatherbit Forecast API. The goal is to determine which location offers the best windsurfing conditions on a given day based on wind speed and temperature, and return the location name along with weather details.

### Features

Uses the Weatherbit Forecast API to fetch 16-day weather data.

Supports the following locations:
* Jastarnia (Poland)
* Bridgetown (Barbados)
* Fortaleza (Brazil)
* Pissouri (Cyprus)
* Le Morne (Mauritius)

The API checks windsurfing conditions on a specified day based on:
Wind speed: Should be between 5 m/s and 18 m/s.
Temperature: Should be between 5°C and 35°C.
The best location is selected using the formula: `v * 3 + temp` (where v is the wind speed in m/s and temp is the average temperature in °C).
If none of the locations meet the criteria, the application returns no result.

## Requirements
1. Java 17 or higher.
2. Maven build tool.
3. Valid weatherbit api key put inside `application.yaml` into `feign.weatherbit.api-key`


## Build the Application
1. via mvn:

   to build project: `mvn clean install`

   to run project`mvn spring-boot:run`

2. via docker:

   to build image `docker build -t app .`
   to run image `docker run -p 8080:8080 app`

## API Usage
The application exposes a REST endpoint that you can use to get the best windsurfing location based on the date.

Endpoint:

`GET /windsurfing-location?date=yyyy-mm-dd`

#### Parameters:
date (required): A date in the format `yyyy-mm-dd` for which you want to check the best windsurfing location.