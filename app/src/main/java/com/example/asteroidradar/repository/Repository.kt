package com.example.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.asteroidradar.api.ApiInterface
import com.example.asteroidradar.api.parseAsteroidsJsonResult
import com.example.asteroidradar.database.AsteroidDatabaseModel
import com.example.asteroidradar.database.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URL
import java.time.LocalDate

class Repository(private val localDatabase: LocalDatabase) {

    private val API_KEY = "5xXjQfrWUBd6pDnHsyEgYagFhLc4Biohk7YAcuKB"
    private val BASE_URL = "https://api.nasa.gov/"

    val asteroids: LiveData<List<AsteroidDatabaseModel>>? = localDatabase.roomDao()?.getAllData()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refresh(): Unit? {

            val currentDate = LocalDate.now().toString()
            val afterSevenDaysDate = LocalDate.now().plusDays(7).toString()

            val retrofit =
                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface::class.java)
            val job = withTimeoutOrNull(2500){
                val retrofitData = retrofit.getNearAsteroids(API_KEY,afterSevenDaysDate, currentDate).await()

                val json = JSONObject(retrofitData)

                val test = parseAsteroidsJsonResult(json)

                localDatabase.roomDao()?.insertData(test)
            }
        return job


    }
}