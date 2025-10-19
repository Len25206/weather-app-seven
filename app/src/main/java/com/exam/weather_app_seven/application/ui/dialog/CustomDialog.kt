package com.exam.weather_app_seven.application.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exam.weather_app_seven.ui.theme.WhiteBackground

@Preview
@Composable
fun CustomMessageDialogPreview() {
    CustomMessageDialog(
        message = "Success",
        isSuccess = true
    )
}


@Composable
fun CustomMessageDialog(
    message: String? = null,
    isSuccess: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier
                .padding(
                    vertical = 50.dp,
                ),
            shape = RoundedCornerShape(16.dp),
            color = WhiteBackground
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 30.dp,
                        horizontal = 10.dp
                    )
                    .width(300.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isSuccess) Color.Green else Color.Red,
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    message ?: "Success",
                )
            }
        }
    }
}