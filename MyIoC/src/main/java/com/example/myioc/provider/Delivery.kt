package com.example.myioc.provider

import com.example.myioc.exceptions.NoDependencyProvided
import com.example.myioc.exceptions.NullDependencyNotSupported
import com.example.myioc.registration.Entry
import com.example.myioc.registration.RegisterPlatform
import kotlin.reflect.KClass

internal class DeliveryImpl(
    private val platformRegistration: RegisterPlatform
) : Delivery {

    override fun <T : Any> getDependency(type: Class<*>): T =
        platformRegistration.find<T>(type)?.reference
            ?: throw NoDependencyProvided("This dependency has not been registered: $type")
}

internal interface Delivery {
    /**
     * This method allows you to get any dependency that has been registered before
     *
     * @param type - This is thee type that need to be query to inject the dependency
     * @return [T] - This the dependency instance of the objects that were registered
     *
     * @throws NoDependencyProvided if the dependency type has not been registered correctly
     */
    @Throws(exceptionClasses = [NoDependencyProvided::class])
    fun <T: Any> getDependency(type: Class<*>): T
}