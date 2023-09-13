package com.donghoonyoo.cloudflare.models.gateway.analytics

import kotlinx.serialization.Serializable

@Serializable
data class ApiResult<T>(
    val time: Long,
    val logs: List<T>,
)
