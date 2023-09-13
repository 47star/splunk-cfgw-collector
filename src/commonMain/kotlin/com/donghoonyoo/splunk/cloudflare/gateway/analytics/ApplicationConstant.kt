package com.donghoonyoo.splunk.cloudflare.gateway.analytics

import com.donghoonyoo.kmp.Env
import com.donghoonyoo.kmp.TypedEnv

internal val zoneId by Env {
    throw Exception("Cannot start application since the ZONEID does not exist.")
}

internal val normalize by TypedEnv(
    default = { true },
    convert = { it == "1" },
)

internal val offsetMinutes by TypedEnv(
    default = { 1000 * 60 * 5 },
    convert = { it.toLong() },
)

internal val durationMinutes by TypedEnv(
    default = { 5 },
    convert = { it.toLong() },
)
