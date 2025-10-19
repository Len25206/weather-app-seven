package com.exam.weather_app_seven.application.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.exam.weather_app_seven.application.Screen
import com.exam.weather_app_seven.application.ui.dialog.CustomMessageDialog
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@Composable
fun Registration(
    navController: NavController? = null,
    userViewModel: UserViewModel
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogStatus by remember { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            delay(2000)
            showDialog = false
            dialogStatus = false
            dialogMessage = ""
            userName = ""
            email = ""
            password = ""
            confirmPassword = ""

        }
    }

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
                    height = 500.dp
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
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
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
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Email")
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Password")
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = {
                    Text("Confirm Password")
                },
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
                    if (userName.isEmpty()) {
                        dialogMessage = "Username cannot be empty"
                        showDialog = true
                        return@Button
                    }
                    if (email.isEmpty()) {
                        dialogMessage = "Email cannot be empty"
                        showDialog = true
                        return@Button
                    }
                    if (password.isEmpty()) {
                        dialogMessage = "Password cannot be empty"
                        showDialog = true
                        return@Button
                    }
                    if (confirmPassword.isEmpty()) {
                        dialogMessage = "Confirm password cannot be empty"
                        showDialog = true
                        return@Button
                    }
                    if (password != confirmPassword) {
                        dialogMessage = "Passwords do not match"
                        showDialog = true
                        return@Button
                    }

                    userViewModel.insertUser(
                        user = User(
                            userName = userName,
                            password = password,
                            email = email
                        )
                    )

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
                    "Create Account",
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
                    "Already have an account?",
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
                        navController?.navigate(Screen.LoginPage.route)
                    }
                ) {
                    Text(
                        "Sign in here",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Blue
                        )
                    )
                }

            }
        }

        AnimatedVisibility(
            visible = showDialog,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(500)
            )
        ) {
            CustomMessageDialog(
                message = dialogMessage,
                isSuccess = dialogStatus,
            )
        }
    }
}