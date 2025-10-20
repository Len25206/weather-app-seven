# Weather App - README

## Overview
Weather App provides real-time weather updates and history tracking based on user location. It features local user authentication using Room database and dynamic UI changes with weather-based icons and time-based background gradients.

---

## Modules and Functions

### Dashboard
- **Dashboard**
  - Requests location permissions using Accompanist Permissions.
  - Fetches GPS location with `LocationHelper`.
  - Calls weather API through `WeatherViewModel` with current coordinates.
  - Collects weather state and displays: location, temperature (in Celsius), weather description, country, sunrise/sunset, wind speed, pressure, humidity, visibility, and weather icon dynamically loaded based on icon code.
  - Background gradient dynamically switches between morning and night colors based on system time (6 PM to 6 AM considered night).
  - Inserts weather data into local `WeatherHistory` database every 60 seconds using reactive state and fresh data reading to avoid null values.
  - Provides navigation to Weather History screen.

- **LogoutHeader**  
  Displays a logout text button navigating to Login screen.

- **LocationDateCard**  
  Displays location and current date with real-time time update every second.

- **MainTemperatureCard**  
  Shows weather temperature and description along with corresponding weather icon in a styled card.

- **SunRiseSunSet**  
  Displays formatted sunrise and sunset times.

- **WeatherDetailsGrid**  
  Shows wind speed, pressure, humidity, and visibility in a clean card layout.

### WeatherHistory
- Displays a list of previously saved weather data from local database.
- Background changes between day and night gradients based on system time.
- Includes back navigation and a button to add current weather data.
- Each item shows weather icon, location, timestamp, and description in cards.

- **WeatherItem**  
  Displays one weather history record with dynamic icon loading and styled info.

### Login
- Local user login screen using Room database (no external auth).
- Email input has regex-based real-time validation with visual error feedback.
- Background changes dynamically based on system time for day/night theme.
- Shows dialogs for login success or failure.
- Navigates to Registration or Dashboard accordingly.

### Registration
- Local user registration screen stored entirely on the device using Room.
- Inputs: username, email, password, confirm password.
- Real-time email format validation with error messages.
- Dynamic time-based backgrounds matching other screens.
- Shows dialogs for success or failure and clears inputs on success.
- Navigates back to Login screen.

### Utility Functions
- **Email Validation**  
  Regex pattern ensuring valid email format.
- **LocationHelper**  
  Fetches device GPS coordinates.
- **DateTimeHelper**  
  Converts epoch timestamps to human-readable date/time formats.
- **Background Gradient**  
  Time-based gradient switching using Compose coroutines.
- **Dynamic Weather Icons**  
  Load icons from OpenWeatherMap API icon code dynamically.

---

## Architecture Notes
- Authentication is implemented locally via Room database, ensuring privacy and offline support.
- Weather icon selection is dynamic based on codes fetched from weather API.
- Background changes provide contextual day/night theme for consistent UX across app.
- Weather data insertion into history database is managed reactively and carefully avoids stale or null data by capturing fresh state inside coroutines.

---

## Usage
- The app requests location permissions on launch to show weather.
- Users can register and login locally.
- Dashboard updates weather hourly and saves history every minute.
- Users can view history with dynamic visuals.

---

This document summarizes key components and detailed functions of your Weather App, highlighting the local authentication architecture, UI dynamics, and data handling for clarity and maintainability.
