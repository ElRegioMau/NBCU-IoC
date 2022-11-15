package com.example.myioc.registration

/**
 * This is the dependency that can be register into our graph, so it can be provided to client
 *
 * @param reference - This is the actual instance that has been initialized and provided by client
 */
internal class Entry<T: Any>(val reference: T)
