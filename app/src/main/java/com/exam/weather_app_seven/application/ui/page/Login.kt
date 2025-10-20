package com.exam.weather_app_seven.application.ui.page

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.UserLoginViewModel
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import java.util.regex.Pattern

@Composable
fun Login(
    navController: NavController? = null,
    userViewModel: UserViewModel,
    userLoginViewModel: UserLoginViewModel,
    userData: (User?) -> Unit
) {
    BackHandler {
        // Optionally handle back press
    }
    val userLogin by userLoginViewModel.userLogin.collectAsState()
    val user by userViewModel.user.collectAsState()

    // Email validation state
    var isEmailValid by remember { mutableStateOf(true) }

    if (user != null) {
        userData(user)
        navController?.navigate(Screen.DashboardPage.route)
        userViewModel.resetUser()
    }

    LaunchedEffect(userLogin.showDialog) {
        if (userLogin.showDialog!!) {
            kotlinx.coroutines.delay(2000)
            userLoginViewModel.setShowDialog(false)
            userLoginViewModel.setDialogStatus(false)
            userLoginViewModel.setMessageDialog("")
            userLoginViewModel.setEmail("")
            userLoginViewModel.setPassword("")
        }
    }

    // Email validation regex function
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Weather Icon Header
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
                Text(
                    text = "🌤️",
                    style = TextStyle(fontSize = 60.sp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title and subtitle
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

            // Login Card
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
                        text = "Sign In",
                        style = TextStyle(
                            fontSize = 28.sp,
                            color = Color(0xFF2C3E50),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Welcome back!",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF7F8C8D)
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email Input with validation
                    OutlinedTextField(
                        value = userLogin.email ?: "",
                        onValueChange = {
                            userLoginViewModel.setEmail(it)
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
                            focusedBorderColor = if (isEmailValid) Color(0xFF4A90E2) else Color(0xFFE74C3C),
                            unfocusedBorderColor = if (isEmailValid) Color(0xFFBDC3C7) else Color(0xFFE74C3C),
                            focusedLabelColor = if (isEmailValid) Color(0xFF4A90E2) else Color(0xFFE74C3C),
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

                    // Password Input
                    OutlinedTextField(
                        value = userLogin.password ?: "",
                        onValueChange = { userLoginViewModel.setPassword(it) },
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

                    Spacer(modifier = Modifier.height(32.dp))

                    // Login Button with success/failure handling
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            if (!isEmailValid || userLogin.email.isNullOrBlank() || userLogin.password.isNullOrBlank()) {
                                userLoginViewModel.setDialogStatus(false)
                                userLoginViewModel.setMessageDialog("Please enter valid email and password")
                                userLoginViewModel.setShowDialog(true)
                                return@Button
                            }
                            userLoginViewModel.validateLogin(
                                onSuccess = {
                                    userViewModel.loginUser(
                                        email = userLogin.email ?: "",
                                        password = userLogin.password ?: "",
                                        onResult = { success ->
                                            if (!success) {
                                                userLoginViewModel.setDialogStatus(false)
                                                userLoginViewModel.setMessageDialog("Account does not exist or incorrect credentials")
                                                userLoginViewModel.setShowDialog(true)
                                            }
                                        }
                                    )
                                },
                                onFailure = { message ->
                                    userLoginViewModel.setDialogStatus(false)
                                    userLoginViewModel.setMessageDialog(message)
                                    userLoginViewModel.setShowDialog(true)
                                }
                            )
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A90E2),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFBDC3C7),
                            disabledContentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        enabled = isEmailValid &&
                                !userLogin.email.isNullOrEmpty() &&
                                !userLogin.password.isNullOrEmpty()
                    ) {
                        Text(
                            "Login",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Navigation to registration screen
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Don't have an account?",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF7F8C8D)
                            )
                        )
                        TextButton(
                            onClick = { navController?.navigate(Screen.RegisterPage.route) }
                        ) {
                            Text(
                                "Register here",
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

        // Dialog for login messages
        androidx.compose.animation.AnimatedVisibility(
            visible = userLogin.showDialog == true,
            enter = androidx.compose.animation.slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = androidx.compose.animation.core.tween(500)
            ),
            exit = androidx.compose.animation.slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = androidx.compose.animation.core.tween(500)
            )
        ) {
            com.exam.weather_app_seven.application.ui.dialog.CustomMessageDialog(
                message = userLogin.messageDialog ?: "",
                isSuccess = userLogin.dialogStatus == true,
            )
        }
    }
}

