package com.example.myioc

import kotlin.reflect.KClass

data class Module<T: Any>(
    val dependenciesToRegister: List<KClass<out T>>
)