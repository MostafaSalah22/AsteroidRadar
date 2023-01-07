package com.example.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AsteroidDatabaseModel::class] , version = 13)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun roomDao(): RoomDao?

    companion object{
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase? {

            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext , LocalDatabase::class.java , "finalDatabase")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}