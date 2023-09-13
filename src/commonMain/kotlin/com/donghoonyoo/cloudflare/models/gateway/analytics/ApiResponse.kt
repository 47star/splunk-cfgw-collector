package com.donghoonyoo.cloudflare.models.gateway.analytics

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val result: ApiResult<T>?,
    val success: Boolean,
    val errors: List<ApiError>,
    val messages: List<String>?,
)
