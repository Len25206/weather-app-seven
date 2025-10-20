# Weather App - README

## Overview
Weather App delivers real-time weather updates and history tracking tailored to the user's current location. It features local user authentication via Room database and adaptive UI with dynamic backgrounds and weather icons based on time and conditions.

---

## Modules and Functions

### Dashboard
- **Dashboard**:  
  Handles location permissions and retrieves GPS location. Fetches weather data from ViewModel based on location. Displays weather info including temperature (converted from Kelvin), weather description, sunrise/sunset, wind speed, pressure, humidity, visibility, and dynamic weather icons from API weather codes. Background gradient changes between morning and night based on device time via coroutine.

- **CurrentDate**:  
  Shows real-time date and weekday with a logout button. Updates every second using coroutine.

- **MainBoard**:  
  Stylized card displaying location, temperature with icon, and current date.

- **SunRiseSunSet**:  
  Displays sunrise and sunset times formatted from epoch values.

- **WindAndAir**:  
  Shows wind speed, pressure, humidity, and visibility in a clean card layout.

---

### WeatherHistory
- Displays saved weather records in a scrollable list.
- Applies morning/night dynamic gradient background by time.
- Includes back navigation and “Add Current Weather” button.
- Each weather item shows icon, location, datetime, and condition in a card.

---

### Login
- Local login screen using Room for user authentication.
- Email validation with regex and real-time visual feedback.
- Dynamic background changes between day and night gradients.
- Shows login success/error dialogs and handles navigation to Dashboard or Registration.

---

### Registration
- User registration stored locally using Room.
- Inputs for username, email, password, and confirm password.
- Real-time email format validation with error messages.
- Dynamic background matching night/day like other screens.
- Handles user creation success/failure with dialogs and form reset.
- Provides navigation to Login.

---

## Utility Functions
- **Email Validation**: Regex pattern ensures proper format before submission.
- **LocationHelper**: Abstracts obtaining current GPS coordinates.
- **DateTimeHelper**: Formats epoch times for display.
- **Background Dynamic Gradient**: Utilizes coroutine to check hour every minute and toggle background between morning and night gradients.
- **Weather Icon Loading**: Icons loaded dynamically from weather API icon codes.

---

## Notes
- Authentication is completely local with Room database; no external authentication services are used.
- Dynamic weather icons reflect actual weather conditions based on API data.
- Time-adaptive backgrounds provide a seamless day/night user experience throughout the app.

---
