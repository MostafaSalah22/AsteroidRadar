package com.example.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(asteroids: List<AsteroidDatabaseModel>)

    @Query("Delete From finalTable")
    fun clearData()

    @Query("Select * From finalTable")
    fun getAllData(): LiveData<List<AsteroidDatabaseModel>>

    @Query("Select * From finalTable Where closeApproachDate = :todayDate")
    fun getTodayData(todayDate:String):LiveData<List<AsteroidDatabaseModel>>

    @Query("Select * From finalTable Where closeApproachDate >= :todayDate And closeApproachDate < :afterWeekDate")
    fun getWeekData(todayDate: String, afterWeekDate:String):LiveData<List<AsteroidDatabaseModel>>
}