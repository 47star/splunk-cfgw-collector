package com.donghoonyoo.cloudflare.models.gateway.analytics

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,
    val message: String,
)
