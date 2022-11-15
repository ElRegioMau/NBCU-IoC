package com.example.myioc.registration

import com.example.myioc.exceptions.NoArgumentsInConstructorSupported
import com.example.myioc.exceptions.NullDependencyNotSupported
import com.example.myioc.provider.TestDependency
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.Serializable

class RegisterTest {

    private lateinit var testRegisterPlatform: RegisterPlatform

    @Before
    fun setUp() {
        testRegisterPlatform = Register<Any>()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `add and find a non null dependency`() {
        testRegisterPlatform.add(TestDependency::class)

        val result = testRegisterPlatform.find<TestDependency>(TestDependency::class.java)

        assertThat(result?.reference).isInstanceOf(TestDependency::class.java)
    }

    @Test(expected = NullDependencyNotSupported::class)
    fun `add an anonymous dependency throws an exception`() {
        testRegisterPlatform.add(object : Serializable { }::class)
    }

    @Test(expected = NoArgumentsInConstructorSupported::class)
    fun `add a dependency with arguments throws an exception`() {
        testRegisterPlatform.add(TestArguments::class)
    }
}

class TestArguments(val test: String = "test")