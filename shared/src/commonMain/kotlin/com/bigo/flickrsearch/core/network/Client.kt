package com.bigo.flickrsearch.core.network

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ClientFactory {
    fun createKtorHttpClient(): HttpClient = HttpClient {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message)
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })

        }
        Napier.base(DebugAntilog())
    }
}

interface FlickrApiSettings {
    val baseUrl: String
    val apiKey: String
}

object DefaultFlickrApiSettings : FlickrApiSettings {
    override val baseUrl: String = "https://api.flickr.com/services/rest"
    override val apiKey: String = "a35f693a94c047351f3ef344ab79075d"

}