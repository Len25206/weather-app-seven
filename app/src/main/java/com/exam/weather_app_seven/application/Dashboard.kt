package com.exam.weather_app_seven.application

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exam.weather_app_seven.R

@Preview
@Composable
fun Dashboard() {
    val morningGradientColors = listOf(
        Color(0xFFF3C9DA), // Dawn Pink
        Color(0xFFF7E7C5), // Morning Gold
        Color(0xFFCDE1E5), // Light Blue
        Color(0xFF89B8E1), // Sky Blue
        Color(0xFF3A7D9C)  // Deep Blue
    )

    val morningBrush = Brush.verticalGradient(
        colors = morningGradientColors,
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )


    val nightGradientColors = listOf(
        Color(0xFF1A0F3D), // Dark Purple
        Color(0xFF2B2F77), // Deep Indigo
        Color(0xFF483475), // Royal Blue
        Color(0xFF141852), // Midnight Blue
        Color(0xFF070B34)  // Navy Blue
    )

    val nightBrush = Brush.verticalGradient(
        colors = nightGradientColors,
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
        MainBoard(morningBrush)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        SunRiseSunSet()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        WindAndAir()
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
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Friday",
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.Black
            )
        )
        Text(
            text = "May 20, 2023 | 12:00 am",
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.Black
            )
        )
    }
}

@Composable
fun MainBoard(brush: Brush) {
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
                text = "Location",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color.Black
                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        CurrentDate()
        Spacer(modifier = Modifier.height(15.dp))
        CurrentTemperature()
    }
}

@Composable
fun CurrentTemperature() {

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
                text = "25°C",
                style = TextStyle(
                    fontSize = 50.sp,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.mipmap.night_thuder_rain),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
            )

        }

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sunny, Windy",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Raining",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun SunRiseSunSet() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                Color.White,
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
                Text(
                    text = "06:00 am",
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
                    text = "Sunset",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                    )
                )
                Text(
                    text = "06:00 pm",
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

@Composable
fun WindAndAir() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                Color.White,
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
                    text = "12 km/h",
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
                    text = "1024 hpa",
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
                    text = "80%",
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
                    text = "10 km",
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