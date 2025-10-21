package com.exam.weather_app_seven.application.ui.page

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.exam.weather_app_seven.application.Screen
import com.exam.weather_app_seven.application.ui.dialog.CustomMessageDialog
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.RegistrationViewModel
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherViewModel
import java.util.regex.Pattern

@Composable
fun Registration(
    navController: NavController? = null,
    userViewModel: UserViewModel,
    registrationViewModel: RegistrationViewModel,
    weatherViewModel: WeatherViewModel
) {
    BackHandler {
        // Optional back press handling
    }
    val userName by registrationViewModel.userName.collectAsState()
    val email by registrationViewModel.email.collectAsState()
    val password by registrationViewModel.password.collectAsState()
    val confirmPassword by registrationViewModel.confirmPassword.collectAsState()
    val apiKey by registrationViewModel.apiKey.collectAsState()
    val showDialog by registrationViewModel.showDialog.collectAsState()
    val dialogMessage by registrationViewModel.dialogMessage.collectAsState()
    val dialogStatus by registrationViewModel.dialogStatus.collectAsState()
    val isError by weatherViewModel.isError.collectAsState()

    var isEmailValid by remember { mutableStateOf(true) }
    var isApiKeyValid by remember { mutableStateOf(true) }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            kotlinx.coroutines.delay(2000)
            registrationViewModel.showDialog(false)
            registrationViewModel.setDialogStatus(false)
            registrationViewModel.setDialogMessage("")
            registrationViewModel.clearForm()
            registrationViewModel.setApiKey("")
        }
    }
    LaunchedEffect(isError) {
        if (isError) {
            isApiKeyValid = false
            weatherViewModel.resetHandlerError()
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
        return emailPattern.matcher(email).matches()
    }

    val gradientColors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF5FA3E8),
        Color(0xFF7BB8EF),
        Color(0xFFA8D5F5)
    )

    val backgroundBrush = Brush.verticalGradient(
        colors = gradientColors,
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.7f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌤️", style = TextStyle(fontSize = 60.sp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Weather App",
                style = TextStyle(
                    fontSize = 36.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Get current weather and history",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(25.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.98f)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create Account",
                        style = TextStyle(
                            fontSize = 28.sp,
                            color = Color(0xFF2C3E50),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Join the weather community!",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF7F8C8D)
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // API Key Field (Required)
                    OutlinedTextField(
                        value = apiKey,
                        onValueChange = {
                            registrationViewModel.setApiKey(it)
                            isApiKeyValid = it.isNotBlank()
                            if (isApiKeyValid) {
                                weatherViewModel.weatherService(
                                    lat = "12.879721",
                                    lon = "121.774017",
                                    appid = it
                                )

                            }
                        },
                        label = { Text("API Key (required)") },
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isApiKeyValid) Color(0xFF4A90E2) else Color(
                                0xFFE74C3C
                            ),
                            unfocusedBorderColor = if (isApiKeyValid) Color(0xFFBDC3C7) else Color(
                                0xFFE74C3C
                            ),
                            focusedLabelColor = if (isApiKeyValid) Color(0xFF4A90E2) else Color(
                                0xFFE74C3C
                            ),
                            cursorColor = Color(0xFF4A90E2)
                        ),
                        isError = !isApiKeyValid,
                        supportingText = {
                            if (!isApiKeyValid) {
                                Text(
                                    text = "API Key is invalid",
                                    color = Color(0xFFE74C3C),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Username Field
                    OutlinedTextField(
                        value = userName,
                        onValueChange = { registrationViewModel.setUserName(it) },
                        label = { Text("Username") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username",
                                tint = Color(0xFF4A90E2)
                            )
                        },
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFBDC3C7),
                            focusedLabelColor = Color(0xFF4A90E2),
                            cursorColor = Color(0xFF4A90E2)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Field with validation
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            registrationViewModel.setEmail(it)
                            isEmailValid = if (it.isEmpty()) true else isValidEmail(it)
                        },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = if (isEmailValid) Color(0xFF4A90E2) else Color(0xFFE74C3C)
                            )
                        },
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isEmailValid) Color(0xFF4A90E2) else Color(
                                0xFFE74C3C
                            ),
                            unfocusedBorderColor = if (isEmailValid) Color(0xFFBDC3C7) else Color(
                                0xFFE74C3C
                            ),
                            focusedLabelColor = if (isEmailValid) Color(0xFF4A90E2) else Color(
                                0xFFE74C3C
                            ),
                            cursorColor = Color(0xFF4A90E2)
                        ),
                        isError = !isEmailValid,
                        supportingText = {
                            if (!isEmailValid) {
                                Text(
                                    text = "Please enter a valid email address",
                                    color = Color(0xFFE74C3C),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { registrationViewModel.setPassword(it) },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = Color(0xFF4A90E2)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFBDC3C7),
                            focusedLabelColor = Color(0xFF4A90E2),
                            cursorColor = Color(0xFF4A90E2)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password Field
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { registrationViewModel.setConfirmPassword(it) },
                        label = { Text("Confirm Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Confirm Password",
                                tint = Color(0xFF4A90E2)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFBDC3C7),
                            focusedLabelColor = Color(0xFF4A90E2),
                            cursorColor = Color(0xFF4A90E2)
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Create Account Button with API key and email validation
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A90E2),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        onClick = {
                            if (!isApiKeyValid || apiKey.isBlank()) {
                                registrationViewModel.setDialogStatus(false)
                                registrationViewModel.setDialogMessage("API Key is required")
                                registrationViewModel.showDialog(true)
                                return@Button
                            }
                            if (!isEmailValid) {
                                registrationViewModel.setDialogStatus(false)
                                registrationViewModel.setDialogMessage("Please enter a valid email address")
                                registrationViewModel.showDialog(true)
                                return@Button
                            }
                            registrationViewModel.validateAndRegister(
                                onSuccess = {
                                    userViewModel.insertUser(
                                        User(
                                            userName = userName,
                                            password = password,
                                            email = email,
                                            apiKey = apiKey
                                        )
                                    )
                                    registrationViewModel.setDialogStatus(true)
                                    registrationViewModel.setDialogMessage("Account created successfully")
                                    registrationViewModel.showDialog(true)
                                },
                                onFailure = { message ->
                                    registrationViewModel.setDialogStatus(false)
                                    registrationViewModel.setDialogMessage(message)
                                    registrationViewModel.showDialog(true)
                                }
                            )
                        },
                        enabled = isEmailValid && isApiKeyValid &&
                                apiKey.isNotBlank() && email.isNotEmpty() &&
                                userName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
                    ) {
                        Text(
                            "Create Account",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Link
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Already have an account?",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF7F8C8D)
                            )
                        )
                        TextButton(
                            onClick = { navController?.navigate(Screen.LoginPage.route) }
                        ) {
                            Text(
                                "Sign in here",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF4A90E2),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
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

