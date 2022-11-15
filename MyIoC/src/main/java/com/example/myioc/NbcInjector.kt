package com.example.myioc

import com.example.myioc.exceptions.NoDependencyProvided
import com.example.myioc.exceptions.NullDependencyNotSupported
import com.example.myioc.provider.Delivery
import com.example.myioc.provider.DeliveryImpl
import com.example.myioc.registration.Register
import com.example.myioc.registration.RegisterPlatform
import kotlin.reflect.KClass

/**
 * This is the main and entry point for the library to start and initialized the required dependencies
 *
 */
object NbcInjector {

    private val register: RegisterPlatform by lazy { Register<Any>() }
    private val delivery: Delivery by lazy { DeliveryImpl(register) }

    /**
     * This method allows to register all the dependencies needed
     *
     * This method needs to be called from an application class
     *
     * @param modules - These are all the modules that contains your dependencies
     *
     * @throws NullDependencyNotSupported This library does not support anonymous classes to be registered
     */
    @Throws(exceptionClasses = [NullDependencyNotSupported::class])
    fun initializeInjector(modules: Module<*>) {
        modules.dependenciesToRegister.map { registerDependency(it) }
    }

    /**
     * This method allows to get any dependency that the client needs to inject
     */
    @Throws(exceptionClasses = [NoDependencyProvided::class])
    fun <T> getDependency(type: Class<T>): T =
        delivery.getDependency(type)

    private fun <T: Any> registerDependency(entry: KClass<T>) {
        register.add(entry)
    }
}

@Throws(exceptionClasses = [NoDependencyProvided::class])
inline fun <reified T> getDependency(): T = NbcInjector.getDependency(T::class.java)