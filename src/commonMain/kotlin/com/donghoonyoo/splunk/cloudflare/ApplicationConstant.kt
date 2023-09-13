package com.donghoonyoo.splunk.cloudflare

import com.donghoonyoo.kmp.Env
import com.donghoonyoo.kmp.TypedEnv
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal val timeoutMillis by TypedEnv(
    default = { 25_000L },
    convert = { it.toLong() }
)

internal val retries by TypedEnv(
    default = { 3 },
    convert = { it.toInt() }
)

internal val endpoint by Env {
    "https://api.cloudflare.com"
}

internal val token by Env {
    throw Exception("Cannot start application since the TOKEN does not exist.")
}


internal val json = Json {
    prettyPrint = false
    ignoreUnknownKeys = true
}

internal val client: HttpClient by lazy {
    HttpClient {
        expectSuccess = false

        defaultRequest {
            url(endpoint)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = timeoutMillis
        }

        install(HttpRequestRetry) {
            maxRetries = retries
            retryOnExceptionIf { _, throwable -> throwable is HttpRequestTimeoutException }
            delayMillis { _ -> 0 }
        }

        install(ContentNegotiation) {
            json(json)
        }
    }
}
