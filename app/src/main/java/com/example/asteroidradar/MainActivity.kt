package com.example.asteroidradar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asteroidradar.api.ApiInterface
import com.example.asteroidradar.api.ImageModel
import com.example.asteroidradar.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}