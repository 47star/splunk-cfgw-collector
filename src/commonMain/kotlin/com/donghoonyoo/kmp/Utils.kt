package com.donghoonyoo.kmp

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv
import kotlin.reflect.KProperty

const val envPrefix = "RVL_"

class Env(
    override val postProcessor: (String) -> String = { it },
    override val default: () -> String,
) : AbstractTypedEnv<String>() {
    override val convert: (String) -> String = { it }
}


class TypedEnv<T>(
    override val postProcessor: (T) -> T = { it },
    override val default: () -> T,
    override val convert: (String) -> T,
) : AbstractTypedEnv<T>()


@OptIn(ExperimentalForeignApi::class)
abstract class AbstractTypedEnv<T> internal constructor() {
    protected abstract val default: () -> T
    protected abstract val convert: (String) -> T

    protected open val postProcessor: (T) -> T = { it }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return postProcessor(
            getenv(envPrefix + property.name.uppercase())?.toKString()?.let(convert)
                ?: default()
        )
    }
}
