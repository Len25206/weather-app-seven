package com.exam.weather_app_seven.application.ui.page

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.exam.weather_app_seven.R
import com.exam.weather_app_seven.Utils.DateTimeHelper
import com.exam.weather_app_seven.database.entity.WeatherHistoryEntity
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.WeatherHistoryViewModel
import kotlinx.coroutines.delay
import java.util.Calendar


@Composable
fun WeatherHistory(
    navController: NavController,
    weatherHistoryViewModel: WeatherHistoryViewModel,
    user: User? = null,
) {
    val weatherHistoryList by weatherHistoryViewModel.weatherHistory.collectAsState()
    LaunchedEffect(Unit) {
        weatherHistoryViewModel.setWeatherHistory(user?.id ?: "")
    }

    // Time-based background state
    var isNightTime by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            isNightTime = hour >= 18 || hour < 6
            delay(60000L) // check every minute
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Weather History",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.width(48.dp)) // Balance the layout
            }

            Spacer(modifier = Modifier.height(20.dp))

            // History List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = weatherHistoryList,
                    key = { weatherHistory -> weatherHistory.id }
                ) {
                    WeatherItem(it)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun WeatherItem(
    weatherHistoryEntity: WeatherHistoryEntity
) {
    val icon = "https://openweathermap.org/img/wn/${weatherHistoryEntity.iconCode}@4x.png"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Weather Icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFECF0F1),
                                Color(0xFFBDC3C7)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Weather Info
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFFE74C3C),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = weatherHistoryEntity.location,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(0xFF2C3E50),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🕒",
                        style = TextStyle(fontSize = 14.sp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = DateTimeHelper.toReadableDate(weatherHistoryEntity.dateAndTime),
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Color(0xFF7F8C8D)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFECF0F1))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = weatherHistoryEntity.weatherDescription,
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Color(0xFF34495E),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}
