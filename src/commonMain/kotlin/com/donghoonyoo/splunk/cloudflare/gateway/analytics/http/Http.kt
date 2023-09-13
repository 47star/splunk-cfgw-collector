package com.donghoonyoo.splunk.cloudflare.gateway.analytics.http

import com.donghoonyoo.cloudflare.models.gateway.analytics.ApiResponse
import com.donghoonyoo.cloudflare.models.gateway.analytics.HttpLog
import com.donghoonyoo.splunk.cloudflare.client
import com.donghoonyoo.splunk.cloudflare.gateway.analytics.durationMinutes
import com.donghoonyoo.splunk.cloudflare.gateway.analytics.normalize
import com.donghoonyoo.splunk.cloudflare.gateway.analytics.offsetMinutes
import com.donghoonyoo.splunk.cloudflare.gateway.analytics.zoneId
import com.donghoonyoo.splunk.cloudflare.json
import com.donghoonyoo.splunk.cloudflare.token
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlin.experimental.ExperimentalNativeApi

fun main() = runBlocking {
    try {
        var to = Clock.System.now().epochSeconds - offsetMinutes * 60
        if (normalize) {
            to /= 60
            to *= 60
        }

        val from = to - durationMinutes * 60

        if (normalize) {
            to -= 1
        }


        val response = client.get(
            "/client/v4/accounts/$zoneId/gateway-analytics/activities/http"
        ) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)

            parameter("from", from)
            parameter("to", to)

            parameter("limit", 10000)
        }

        val body = response.body<ApiResponse<HttpLog>>()

        if (body.success && body.result != null) {
            for (result in body.result.logs) {
                println(json.encodeToString(result))
            }
        }
    } catch (throwable: Throwable) {
        @OptIn(ExperimentalNativeApi::class)
        println(throwable.getStackTrace().joinToString(", "))
    }
}
