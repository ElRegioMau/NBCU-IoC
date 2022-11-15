package com.example.myioc

import com.example.myioc.exceptions.NoDependencyProvided
import com.example.myioc.provider.TestDependency
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NbcInjectorTest {

    @Test
    fun `initialize dependencies and get all dependencies registered`() {
        val modules = Module(
            listOf(
                TestDependency::class,
                AnotherTestDependency::class,
                ThirdTest::class
            )
        )

        NbcInjector.initializeInjector(modules)

        val result1 = getDependency<TestDependency>()
        val result2 = getDependency<AnotherTestDependency>()
        val result3 = getDependency<ThirdTest>()

        assertThat(result1).isInstanceOf(TestDependency::class.java)
        assertThat(result2).isInstanceOf(AnotherTestDependency::class.java)
        assertThat(result3).isInstanceOf(ThirdTest::class.java)

        assertThat(result1.test).isEqualTo("Testing")
        assertThat(result2.test).isEqualTo("Another testing")
        assertThat(result3.test).isEqualTo("third test")
    }

    @Test(expected = NoDependencyProvided::class)
    fun `get dependencies that where not registered`() {
        val modules = Module(
            listOf(
                TestDependency::class,
                ThirdTest::class
            )
        )

        NbcInjector.initializeInjector(modules)

        getDependency<TestDependency>()
        getDependency<AnotherTestDependency>()
        getDependency<ThirdTest>()
    }

    @Test
    fun `get dependencies that where double registered`() {
        val modules = Module(
            listOf(
                TestDependency::class,
                TestDependency::class
            )
        )

        NbcInjector.initializeInjector(modules)

        val result1 = getDependency<TestDependency>()
        val result2 = getDependency<TestDependency>()

        assertThat(result1.hashCode()).isEqualTo(result2.hashCode())
    }
}

class AnotherTestDependency {
    val test = "Another testing"
}

class ThirdTest {
    val test = "third test"
}