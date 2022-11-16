package com.example.monthly.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monthly.dao.StampDAO
import com.example.monthly.dao.UserDAO
import com.example.monthly.dataclass.User

@Database(entities = [User::class], version = 1)
abstract class StampDatabase : RoomDatabase() {

    abstract val stampDAO : StampDAO

    companion object{
        @Volatile
        private var INSTANCE : StampDatabase? = null
        fun getInstance(context: Context):StampDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StampDatabase::class.java,
                        "stamp_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}