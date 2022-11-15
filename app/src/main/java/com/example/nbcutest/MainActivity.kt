package com.example.nbcutest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myioc.getDependency

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var service: ServiceCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = getDependency()

        findViewById<TextView>(R.id.txt_view).text = service.data

        Log.d(TAG, "onCreate: ${service.data}")
    }
}