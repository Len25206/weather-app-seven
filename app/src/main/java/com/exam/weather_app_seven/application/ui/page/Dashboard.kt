package com.exam.weather_app_seven.application.ui.page

import android.Manifest
import android.annotation.SuppressLint
import android.location.LocationProvider
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.exam.weather_app_seven.Utils.DateTimeHelper
import com.exam.weather_app_seven.Utils.LocationHelper
import com.exam.weather_app_seven.mvvm.viewModel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Dashboard(
    navController: NavController,
    weatherViewModel: WeatherViewModel
) {
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

    when {
        locationPermissionsState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                locationProvider.getCurrentLocation { location ->
                    if (location != null) {
                        weatherViewModel.weatherService(
                            lat = location.latitude.toString(),
                            lon = location.longitude.toString()
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

        !locationPermissionsState.allPermissionsGranted  -> {
            Text("Permission denied. Please enable it from settings.")
        }
    }

    LaunchedEffect(true) {
        locationProvider.getCurrentLocation { location ->
            if (location != null) {
                weatherViewModel.weatherService(
                    lat = location.latitude.toString(),
                    lon = location.longitude.toString()
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

    val morningGradientColors = listOf(
        Color(0xFFF3C9DA), // Dawn Pink
        Color(0xFFF7E7C5), // Morning Gold
        Color(0xFFCDE1E5), // Light Blue
        Color(0xFF89B8E1), // Sky Blue
        Color(0xFF3A7D9C)  // Deep Blue
    )

    val nightGradientColors = listOf(
        Color(0xFF1A0F3D), // Dark Purple
        Color(0xFF2B2F77), // Deep Indigo
        Color(0xFF483475), // Royal Blue
        Color(0xFF141852), // Midnight Blue
        Color(0xFF070B34)  // Navy Blue
    )
    val backgroundBrush = Brush.verticalGradient(
        colors = morningGradientColors,
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                vertical = 50.dp,
                horizontal = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        MainBoard(
            backgroundBrush,
            location = location,
            temperature = currentTemp,
            weatherDescription = weatherDescription,
            country = country,
            iconCode = weatherIcon
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        SunRiseSunSet(
            brush = backgroundBrush,
            sunrise = sunrise,
            sunset = sunset
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        WindAndAir(
            brush = backgroundBrush,
            windSpeed = windSpeed,
            pressure = pressure,
            humidity = humidity,
            visibility = visibility
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = "SHOW WEATHER HISTORY",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )
        }
    }
}


@Composable
fun CurrentDate() {
    val currentDate = System.currentTimeMillis()
    val uiCurrentDate = DateTimeHelper.toReadableDate(currentDate)
    val week = DateTimeHelper.toWeek(currentDate)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = week.uppercase(),
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.Black
            )
        )
        Text(
            text = uiCurrentDate,
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.Black
            )
        )
    }
}

@Composable
fun MainBoard(
    brush: Brush,
    location: String? = "",
    temperature: Double? = 0.0,
    weatherDescription: String? = "",
    country: String? = "",
    iconCode: String? = ""
) {
    Column(
        modifier = Modifier
            .shadow(
                10.dp,
                shape = RoundedCornerShape(
                    30.dp
                )
            )
            .fillMaxWidth()
            .height(270.dp)
            .background(
                brush = brush,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
            .padding(
                horizontal = 15.dp,
                vertical = 15.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Red,
            )

            Text(
                text = "$location, ${country?.uppercase()}",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color.Black
                )
            )

        }
        Spacer(modifier = Modifier.height(15.dp))
        CurrentDate()
        Spacer(modifier = Modifier.height(15.dp))
        CurrentTemperature(
            temperature = temperature,
            weatherDescription = weatherDescription,
            iconCode = iconCode
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CurrentTemperature(
    temperature: Double? = 0.0,
    weatherDescription: String? = "",
    iconCode: String? = ""
) {
    val kelvin = 273.15
    val temperatureDouble = temperature ?: 0.0
    val celsius = temperatureDouble - kelvin
    val formattedTemp = String.format("%.1f", celsius)
    val icon = "https://openweathermap.org/img/wn/$iconCode@2x.png"
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$formattedTemp°C",
                style = TextStyle(
                    fontSize = 50.sp,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        weatherDescription?.uppercase()?.let {
            Text(
                text = it,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun SunRiseSunSet(
    brush: Brush,
    sunrise: String? = "",
    sunset: String? = ""
) {
    val sunriseTimeFormatted = sunrise?.let { DateTimeHelper.toReadableTime(it.toLong()) }
    val sunSetTimeFormatted = sunset?.let { DateTimeHelper.toReadableTime(it.toLong()) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                brush = brush,
                shape = RoundedCornerShape(10.dp)
            ),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🌞Sun Times",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sunrise",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )

                sunriseTimeFormatted?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sunset",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                    )
                )
                sunSetTimeFormatted?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun WindAndAir(
    brush: Brush,
    windSpeed: Double? = 0.0,
    pressure: Int? = 0,
    humidity: Int? = 0,
    visibility: Int? = 0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                brush = brush,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 15.dp,
                vertical = 15.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "🍃Wind and Air",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            ),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Wind speed",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "$windSpeed km/h",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Pressure",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "$pressure hpa",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Humidity",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "$humidity%",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Visibility",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "$visibility km",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}