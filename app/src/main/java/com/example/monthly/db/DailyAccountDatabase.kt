package com.example.monthly.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monthly.dao.DailyAccountDAO
import com.example.monthly.dao.StampDAO
import com.example.monthly.dao.UserDAO
import com.example.monthly.dataclass.User

@Database(entities = [User::class], version = 1)
abstract class DailyAccountDatabase : RoomDatabase() {

    abstract val dailyAccountDAO : DailyAccountDAO

    companion object{
        @Volatile
        private var INSTANCE : DailyAccountDatabase? = null
        fun getInstance(context: Context):DailyAccountDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DailyAccountDatabase::class.java,
                        "daily_account_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}