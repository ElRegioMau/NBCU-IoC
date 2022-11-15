package com.example.myioc.exceptions

/**
 * This exception is threw when no dependency was registered at the beginning
 */
class NoDependencyProvided(message: String) : Exception(message)

/**
 * This exception is threw when trying to register an anonymous class
 */
class NullDependencyNotSupported(message: String) : IllegalStateException(message)

/**
 * This exception is threw when trying to register a dependency with arguments
 */
class NoArgumentsInConstructorSupported(message: String) : IllegalArgumentException(message)