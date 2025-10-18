package com.exam.weather_app_seven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.exam.weather_app_seven.application.MainScreen
import com.exam.weather_app_seven.application.ui.WeatherHistory
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

val supabase = createSupabaseClient(
    supabaseUrl = "https://your-project.supabase.co",
    supabaseKey = "your-anon-key"
) {
    install(Postgrest)  // For database operations
    install(Auth)        // For authentication
    install(Storage)     // For file storage
    install(Realtime)    // For real-time subscriptions
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}