package com.exam.weather_app_seven.application.ui.page

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.exam.weather_app_seven.Utils.DateTimeHelper
import com.exam.weather_app_seven.Utils.LocationHelper
import com.exam.weather_app_seven.application.Screen
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.model.WeatherHistory
import com.exam.weather_app_seven.mvvm.viewModel.UserLoginViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherHistoryViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherViewModel
import com.exam.weather_app_seven.ui.theme.WhiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Dashboard(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    user: User? = null,
    userLoginViewModel: UserLoginViewModel,
    weatherHistoryViewModel: WeatherHistoryViewModel
) {

    val isError by weatherViewModel.isError.collectAsState()
    BackHandler {

    }
    val context = LocalContext.current
    val locationProvider = remember { LocationHelper(context) }
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    LaunchedEffect(true) {
        locationPermissionsState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(isError) {
        if (isError) {
            Toast.makeText(context, "Invalid API key", Toast.LENGTH_SHORT).show()
            delay(1000)
            userLoginViewModel.setEmail("")
            userLoginViewModel.setPassword("")
            navController.navigate(Screen.LoginPage.route) {
                popUpTo(Screen.DashboardPage.route) { inclusive = true }
            }
            weatherViewModel.resetHandlerError()
        }
    }

    when {
        locationPermissionsState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                locationProvider.getCurrentLocation { location ->
                    if (location != null) {
                        weatherViewModel.weatherService(
                            lat = location.latitude.toString(),
                            lon = location.longitude.toString(),
                            appid = user?.apiKey ?: ""
                        )
                    } else {
                        Log.e("Dashboard", "Location is null")
                    }
                }
            }
        }

        locationPermissionsState.shouldShowRationale -> {
            Text("Location permission is needed to show weather.")
        }

        !locationPermissionsState.allPermissionsGranted -> {
            Text("Permission denied. Please enable it from settings.")
        }
    }

    LaunchedEffect(true) {
        locationProvider.getCurrentLocation { location ->
            if (location != null) {
                weatherViewModel.weatherService(
                    lat = location.latitude.toString(),
                    lon = location.longitude.toString(),
                    appid = user?.apiKey ?: ""
                )
            } else {
                Log.e("Dashboard", "Location not available or permission denied")
            }
        }
    }

    val weatherState = weatherViewModel.weather.collectAsState()
    val location = weatherState.value?.locationName
    val currentTemp = weatherState.value?.mainTemp
    val weatherDescription = weatherState.value?.weatherDescription
    val country = weatherState.value?.sysCountry
    val sunrise = weatherState.value?.sysSunrise
    val sunset = weatherState.value?.sysSunset
    val windSpeed = weatherState.value?.windSpeed
    val pressure = weatherState.value?.pressure
    val humidity = weatherState.value?.humidity
    val visibility = weatherState.value?.visibility
    val weatherIcon = weatherState.value?.iconCode
    var currentTime by mutableLongStateOf(0L)


    // Time-based background state
    var isNightTime by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            // Night time: 6 PM (18:00) to 6 AM (6:00)
            isNightTime = hour >= 18 || hour < 6
            Log.e("Dashboard", "isNightTime: $weatherIcon")
            delay(60000L) // Check every minute
        }
    }
    LaunchedEffect(currentTemp, location, weatherDescription, weatherIcon, user) {
        while (true) {
            if (location != null && weatherDescription != null && weatherIcon != null && user?.id != null) {
                val now = System.currentTimeMillis()  // Get current time here
                weatherHistoryViewModel.insertWeatherHistory(
                    WeatherHistory(
                        userId = user.id,
                        location = "$location, ${country?.uppercase()}",
                        dateAndTime = now,
                        weatherDescription = weatherDescription,
                        iconCode = weatherIcon
                    )
                )
            }
            delay(60000L)
        }
    }

    val morningGradientColors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF5FA3E8),
        Color(0xFF7BB8EF),
        Color(0xFFA8D5F5)
    )

    val nightGradientColors = listOf(
        Color(0xFF0F2027),
        Color(0xFF203A43),
        Color(0xFF2C5364)
    )

    val backgroundBrush = Brush.verticalGradient(
        colors = if (isNightTime) nightGradientColors else morningGradientColors,
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header with logout
            LogoutHeader(
                navController = navController,
                userLoginViewModel
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Location and Date
            LocationDateCard(
                location = location,
                country = country,
                navController = navController,
                currentTime = {
                    currentTime = it
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Main Temperature Display
            MainTemperatureCard(
                temperature = currentTemp,
                weatherDescription = weatherDescription,
                iconCode = weatherIcon
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sun Times Card
            SunTimesCard(
                sunrise = sunrise,
                sunset = sunset
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Weather Details Grid
            WeatherDetailsGrid(
                windSpeed = windSpeed,
                pressure = pressure,
                humidity = humidity,
                visibility = visibility
            )

            Spacer(modifier = Modifier.height(20.dp))

            // History Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(15.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                TextButton(
                    onClick = { navController.navigate(Screen.HistoryPage.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "📊 VIEW WEATHER HISTORY",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xFF4A90E2),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (location == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isNightTime) Color(0xFF0F2027) else Color(0xFF4A90E2)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isNightTime) "🌙" else "🌤️",
                        style = TextStyle(fontSize = 60.sp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Fetching weather data...",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LogoutHeader(
    navController: NavController,
    userLoginViewModel: UserLoginViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = {
                userLoginViewModel.setEmail("")
                userLoginViewModel.setPassword("")
                navController.navigate(Screen.LoginPage.route) {
                    popUpTo(Screen.DashboardPage.route) { inclusive = true }
                }
            }
        ) {
            Text(
                text = "🚪 Logout",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun LocationDateCard(
    location: String?,
    country: String?,
    navController: NavController,
    currentTime: (Long) -> Unit
) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000L)
        }
    }

    val uiCurrentDate = DateTimeHelper.toReadableDate(currentTime)
    val week = DateTimeHelper.toWeek(currentTime)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFE74C3C),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$location, ${country?.uppercase()}",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color(0xFF2C3E50),
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = week.uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF7F8C8D),
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = uiCurrentDate,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF95A5A6)
                )
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MainTemperatureCard(
    temperature: Double?,
    weatherDescription: String?,
    iconCode: String?
) {
    val kelvin = 273.15
    val temperatureDouble = temperature ?: 0.0
    val celsius = temperatureDouble - kelvin
    val formattedTemp = String.format("%.1f", celsius)
    val icon = "https://openweathermap.org/img/wn/$iconCode@4x.png"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(25.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    modifier = Modifier.size(140.dp)
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "$formattedTemp°",
                style = TextStyle(
                    fontSize = 72.sp,
                    color = Color(0xFF2C3E50),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weatherDescription?.uppercase() ?: "",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF7F8C8D),
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Composable
fun SunTimesCard(
    sunrise: String?,
    sunset: String?
) {
    val sunriseTimeFormatted = sunrise?.let { DateTimeHelper.toReadableTime(it.toLong()) }
    val sunSetTimeFormatted = sunset?.let { DateTimeHelper.toReadableTime(it.toLong()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "☀️ Sun Times",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color(0xFF2C3E50),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SunTimeItem(
                    icon = "🌅",
                    label = "Sunrise",
                    time = sunriseTimeFormatted ?: "--:--"
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(60.dp)
                        .background(WhiteBackground)
                )

                SunTimeItem(
                    icon = "🌇",
                    label = "Sunset",
                    time = sunSetTimeFormatted ?: "--:--"
                )
            }
        }
    }
}

@Composable
fun SunTimeItem(icon: String, label: String, time: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            style = TextStyle(fontSize = 32.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF7F8C8D)
            )
        )
        Text(
            text = time,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color(0xFF2C3E50),
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun WeatherDetailsGrid(
    windSpeed: Double?,
    pressure: Int?,
    humidity: Int?,
    visibility: Int?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "🌬️ Weather Details",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color(0xFF2C3E50),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(
                    icon = "💨",
                    label = "Wind Speed",
                    value = "${windSpeed ?: 0.0} km/h",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailItem(
                    icon = "🌡️",
                    label = "Pressure",
                    value = "${pressure ?: 0} hPa",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(
                    icon = "💧",
                    label = "Humidity",
                    value = "${humidity ?: 0}%",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailItem(
                    icon = "👁️",
                    label = "Visibility",
                    value = "${visibility ?: 0} km",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(WhiteBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                style = TextStyle(fontSize = 24.sp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF7F8C8D)
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF2C3E50),
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}
