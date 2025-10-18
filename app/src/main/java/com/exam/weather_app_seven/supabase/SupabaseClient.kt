// SupabaseClient.kt
package com.exam.weather_app_seven.supabase

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object SupabaseClient {
    private const val BASE_URL = "https://ixuunxnzvuewaaabryyy.supabase.co"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Iml4dXVueG56dnVld2FhYWJyeXl5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA3MjAwMTMsImV4cCI6MjA3NjI5NjAxM30.tAt5OF9d9puzppjD2pK0iVcu6CEZbcaz0NK44QlaLSc"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        defaultRequest {
            url(BASE_URL)
            header("apikey", API_KEY)
            header("Authorization", "Bearer $API_KEY")
            contentType(ContentType.Application.Json)
        }
    }
}
