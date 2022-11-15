package com.example.nbcutest

import android.app.Application
import com.example.myioc.Module
import com.example.myioc.NbcInjector
import java.lang.StringBuilder

class NbcuApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        NbcInjector.initializeInjector(
            Module(
                listOf(
                    ServiceCreator::class
                )
            )
        )
    }
}