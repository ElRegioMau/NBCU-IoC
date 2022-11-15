package com.example.myioc.provider

import com.example.myioc.exceptions.NoDependencyProvided
import com.example.myioc.registration.RegisterPlatform
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeliveryImplTest {

    private lateinit var testDelivery: Delivery

    private val mockRegister = mockk<RegisterPlatform>(relaxed = true)

    @Before
    fun setUp() {
        testDelivery = DeliveryImpl(mockRegister)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get a registered dependency`() {
        val testDependency = TestDependency()
        every { mockRegister.find<TestDependency>(TestDependency::class.java) } returns mockk {
            every { reference } returns testDependency
        }

        val result = testDelivery.getDependency<TestDependency>(TestDependency::class.java)

        assertThat(result).isInstanceOf(TestDependency::class.java)
        assertThat(result.test).isEqualTo("Testing")
    }

    @Test(expected = NoDependencyProvided::class)
    fun `get a no registered dependency throws an exception`() {
        every { mockRegister.find<TestDependency>(TestDependency::class.java) } returns null

        testDelivery.getDependency<TestDependency>(TestDependency::class.java)
    }
}

class TestDependency {
    val test = "Testing"
}