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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.exam.weather_app_seven.application.Screen
import com.exam.weather_app_seven.application.ui.dialog.CustomMessageDialog
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.RegistrationViewModel
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel

@Composable
fun Registration(
    navController: NavController? = null,
    userViewModel: UserViewModel,
    registrationViewModel: RegistrationViewModel
) {
    val userName by registrationViewModel.userName.collectAsState()
    val email by registrationViewModel.email.collectAsState()
    val password by registrationViewModel.password.collectAsState()
    val confirmPassword by registrationViewModel.confirmPassword.collectAsState()
    val showDialog by registrationViewModel.showDialog.collectAsState()
    val dialogMessage by registrationViewModel.dialogMessage.collectAsState()
    val dialogStatus by registrationViewModel.dialogStatus.collectAsState()

    LaunchedEffect(showDialog) {
        if (showDialog) {
            kotlinx.coroutines.delay(2000)
            registrationViewModel.showDialog(false)
            registrationViewModel.setDialogStatus(false)
            registrationViewModel.setDialogMessage("")
            registrationViewModel.clearForm()
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
                onValueChange = { registrationViewModel.setUserName(it) },
                label = { Text("Username") },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { registrationViewModel.setEmail(it) },
                label = { Text("Email") },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { registrationViewModel.setPassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { registrationViewModel.setConfirmPassword(it) },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.size(height = 50.dp, width = 350.dp),
                onClick = {
                    registrationViewModel.validateAndRegister(
                        onSuccess = {
                            userViewModel.insertUser(
                                User(
                                    userName = userName,
                                    password = password,
                                    email = email
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
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    "Create Account",
                    style = TextStyle(color = Color.White)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Already have an account?",
                    style = TextStyle(fontSize = 15.sp, color = Color.Black)
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(
                    onClick = { navController?.navigate(Screen.LoginPage.route) }
                ) {
                    Text(
                        "Sign in here",
                        style = TextStyle(fontSize = 15.sp, color = Color.Blue)
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
