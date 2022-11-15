package com.example.myioc.registration

import com.example.myioc.NbcInjector
import com.example.myioc.exceptions.NoArgumentsInConstructorSupported
import com.example.myioc.exceptions.NoDependencyProvided
import com.example.myioc.exceptions.NullDependencyNotSupported
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import kotlin.jvm.Throws
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.javaType

/**
 * This [Register] is the main core of the library that will add dependencies and get dependencies from the storage
 *
 * @param T - This can be cast as generic object
 */
internal class Register<T: Any> : RegisterPlatform {

    private val dependenciesStorage = ConcurrentHashMap<String, Entry<T>>()

    override fun add(entry: KClass<*>) {
        if (entry.simpleName == null) throw NullDependencyNotSupported("Null or Anonymous classes dependencies not supported")
        if (!entry.primaryConstructor?.parameters.isNullOrEmpty())
            throw NoArgumentsInConstructorSupported("Registering a dependency with parameters is not supported: $entry")

        dependenciesStorage[entry::simpleName.get()!!] =
            Entry(entry::primaryConstructor.get()!!.call() as T)
    }

    override fun <T: Any> find(name: Class<*>): Entry<T>? =
        dependenciesStorage[name.simpleName] as? Entry<T>
}

internal interface RegisterPlatform {
    /**
     * This method allows to add any dependency that needs to be provided
     *
     * @param entry - This is the object or dependency that needs to be created and injected
     *
     * @throws NullDependencyNotSupported when trying to register a null dependency or an anonymous class.
     * @throws NoArgumentsInConstructorSupported when dependency has no empty constructor
     */
    @Throws(NullDependencyNotSupported::class, NoArgumentsInConstructorSupported::class)
    fun add(entry: KClass<*>)

    /**
     * This method allows to get any dependency needed to be injected
     *
     * @param name - This is the Type of object or dependency that will retrieve from the storage
     * @return [Entry] - This is the entry wrapper for the dependency, if it null means that no dependency has been registered
     */
    fun <T: Any> find(name: Class<*>): Entry<T>?
}