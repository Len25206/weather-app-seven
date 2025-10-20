# Weather App - README

## Overview
Weather App provides real-time weather updates and history tracking based on user location. It features local user authentication using Room database and dynamic UI changes with weather-based icons and time-based background gradients.

---

## API Key Information
This app requires an API key from OpenWeather to retrieve weather data.

- **How to get an API Key:**  
  Register for a free account and obtain your API key here: [https://home.openweathermap.org/](https://home.openweathermap.org/)

- **Pre-registered Working API Keys (for testing/demo):**
  - `bb1f7e5036ab3b59255d9fc1caa97792`
  - `b7a9e2c6b41bcbfae851a9d28b3cae6f`

- **Where to put the API Key:**  
  The API key must be inserted during **account registration** in the app. The registration screen includes a required API key input field where users provide their key to enable weather API integration.

---

## Modules and Functions

### Dashboard
- **Dashboard**
  - Requests location permissions using Accompanist Permissions.
  - Fetches GPS location with `LocationHelper`.
  - Calls weather API through `WeatherViewModel` with current coordinates.
  - Collects weather state and displays location, temperature (in Celsius), weather description, country, sunrise/sunset, wind speed, pressure, humidity, visibility, and weather icon loaded dynamically based on icon code.
  - Background gradient switches between morning and night colors based on system time (6 PM to 6 AM considered night).
  - Inserts weather data into local `WeatherHistory` database every 60 seconds using fresh reactive state to avoid null data.
  - Provides navigation to Weather History screen.

- **LogoutHeader**  
  Displays a logout button navigating to Login screen.

- **LocationDateCard**  
  Shows location and current real-time date with second updates.

- **MainTemperatureCard**  
  Displays temperature, weather description, and icon in a styled card.

- **SunRiseSunSet**  
  Shows formatted sunrise and sunset times.

- **WeatherDetailsGrid**  
  Displays wind speed, pressure, humidity, and visibility.

### WeatherHistory
- Displays saved weather history in a scrollable list with dynamic backgrounds.
- Supports back navigation and add current weather button.
- Each item shows weather icon, location, timestamp, and description.

- **WeatherItem**  
  Styled card for individual weather history entry.

### Login
- Local user login with Room database storage (no external auth).
- Includes real-time email validation with error feedback.
- Background adapts dynamically to day/night theme.
- Shows dialogs for login success or failure including "account does not exist".
- Navigates to Registration or Dashboard upon login state change.

- **API Key Requirement Added:**  
  An API key input field is mandatory and validated before login.

### Registration
- Local user registration stored entirely on device using Room.
- Includes username, email, password, confirm password, and **required API key** inputs.
- Real-time validation for email format and API key presence with error messages.
- Dynamic day/night backgrounds consistent with app theme.
- Shows dialogs for success/failure and clears form after successful registration.
- Navigates to Login page after successful account creation.

### Utility Functions
- **Email Validation:** Regex pattern validation.
- **LocationHelper:** Fetches device GPS location.
- **DateTimeHelper:** Converts timestamps to readable date/time.
- **Background Gradient:** Coroutine-based time-driven gradient switching.
- **Dynamic Weather Icons:** Loads icons from OpenWeatherMap API codes.

---

## Architecture Notes
- Authentication is completely local via Room database.
- Weather icons dynamically selected based on API data.
- Backgrounds switch contextually for day/night for consistent UX.
- History data insertions use reactive fresh state to avoid stale or null inserts.

---

## Usage
- API key must be entered when registering a user account.
- App requests location permission at launch to fetch weather data.
- Login requires valid email, password, and API key.
- Dashboard fetches weather based on location and user’s API key, saving history.
- History tab shows records saved during app usage with matching time-based backgrounds.

---

This documentation clarifies where and how to insert the API key (on registration), describes major modules, and details the app flow integrating local authentication and weather API usage.
