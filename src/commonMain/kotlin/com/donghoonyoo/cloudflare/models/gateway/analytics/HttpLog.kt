package com.donghoonyoo.cloudflare.models.gateway.analytics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpLog(
    @SerialName("session_id")
    val sessionId: String,

    val datetime: Long,

    @SerialName("request_id")
    val requestId: String,

    @SerialName("account_id")
    val accountId: String,

    @SerialName("user_id")
    val userId: String,

    val email: String,

    @SerialName("device_id")
    val deviceId: String,

    @SerialName("rule_id")
    val ruleId: String,

    val action: Int,

    @SerialName("action_name")
    val actionName: String,

    @SerialName("is_isolated")
    val isIsolated: Boolean,

    @SerialName("http_host")
    val httpHost: String,

    @SerialName("http_method")
    val httpMethod: Int,

    @SerialName("http_method_name")
    val httpMethodName: String,

    @SerialName("http_version")
    val httpVersion: Int,

    @SerialName("http_version_name")
    val httpVersionName: String,

    @SerialName("http_status_code")
    val httpStatusCode: Int,

    val url: String,

    val referer: String,

    @SerialName("user_agent")
    val userAgent: String,

    @SerialName("source_ip")
    val sourceIp: String,

    @SerialName("source_internal_ip")
    val sourceInternalIp: String,

    @SerialName("source_port")
    val sourcePort: Int,

    @SerialName("destination_ip")
    val destinationIp: String,

    @SerialName("destination_port")
    val destinationPort: Int,

    // ...

    @SerialName("src_country")
    val sourceCountry: String,

    @SerialName("dst_country")
    val destinationCountry: String,

    @SerialName("proxy_endpoint")
    val proxyEndpoint: String,

    @SerialName("untrusted_cert_action")
    val untrustedCertAction: String,
)
