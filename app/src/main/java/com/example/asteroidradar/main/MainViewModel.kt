package com.example.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.asteroidradar.api.ApiInterface
import com.example.asteroidradar.api.ImageModel
import com.example.asteroidradar.database.AsteroidDatabaseModel
import com.example.asteroidradar.database.LocalDatabase
import com.example.asteroidradar.repository.Repository
import com.example.asteroidradar.worker.MyWorker
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class MainViewModel(app:Application): AndroidViewModel(app) {
    private val API_KEY = "svkoBIoY84wwOgbxOHe5FGuLwa9jxJKEqyS34oio"
    private val BASE_URL = "https://api.nasa.gov/"

    private val db = LocalDatabase.getInstance(getApplication())
    private val repo = db?.let { Repository(it) }

    init {
        viewModelScope.launch {
            if(checkConnectionType(getApplication())) {
                var job = repo?.refresh()
                while (job == null) {
                    job = repo?.refresh()
                }
            }
        }
    }

    val asteroids = repo?.asteroids
    //var mutableAsteroids = MutableLiveData<List<AsteroidDatabaseModel>>()


    private fun checkConnectionType(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileDataConnection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return if (wifiConnection?.isConnectedOrConnecting == true) {
            true
        } else {
            mobileDataConnection?.isConnectedOrConnecting == true
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayData(): LiveData<List<AsteroidDatabaseModel>>? {
        return LocalDatabase.getInstance(getApplication())?.roomDao()?.getTodayData(LocalDate.now().toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekData(): LiveData<List<AsteroidDatabaseModel>>? {
        return LocalDatabase.getInstance(getApplication())?.roomDao()?.getWeekData(LocalDate.now().toString(),LocalDate.now().plusDays(7).toString())
    }

    private var _dayImage = MutableLiveData<ImageModel>()
    val dayImage: LiveData<ImageModel>
        get() = _dayImage


    fun getDayImage() {
        if(checkConnectionType(getApplication())) {
            val retrofit =
                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface::class.java)

            retrofit.getDayImage(API_KEY).enqueue(object :
                Callback<ImageModel?> {
                override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                    _dayImage.value = response.body()
                }

                override fun onFailure(call: Call<ImageModel?>, t: Throwable) {
                    return
                }
            })
        }
    }

}