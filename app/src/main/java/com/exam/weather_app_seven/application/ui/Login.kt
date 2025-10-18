package com.exam.weather_app_seven.application.ui

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.exam.weather_app_seven.application.Screen
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import com.exam.weather_app_seven.retrofit.service.weatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
fun Login(
    navController: NavController? = null,
    userViewModel: UserViewModel? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                vertical = 50.dp,
                horizontal = 20.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .size(
                    width = 350.dp,
                    height = 400.dp
                )
                .shadow(
                    10.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "☁️Weather App",
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "Get current weather and history",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Text(
                text = "Sign in",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {

                },
                label = {
                    Text("Username")
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = "",
                onValueChange = {

                },
                label = {
                    Text("Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Button(
                modifier = Modifier
                    .size(
                        height = 50.dp,
                        width = 350.dp
                    ),
                onClick = {
                    navController?.navigate(Screen.DashboardPage.route)
                },
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    "Login",
                    style = TextStyle(
                        color = Color.White
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                Text(
                    "Don't have an account?",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                )
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                )
                TextButton(
                    onClick = {
                        navController?.navigate(Screen.RegisterPage.route)
                    }

                ) {
                    Text(
                        "Register here",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Blue
                        )
                    )
                }
            }
        }

    }
}