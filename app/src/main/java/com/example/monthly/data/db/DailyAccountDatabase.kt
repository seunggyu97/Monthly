package com.example.monthly.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.monthly.data.dao.DailyAccountDAO
import com.example.monthly.data.dataclass.DailyAccount

@Database(entities = [DailyAccount::class], version = 1)
@TypeConverters( Converters::class)
abstract class DailyAccountDatabase : RoomDatabase() {

    abstract val dailyAccountDAO : DailyAccountDAO

    companion object{
        @Volatile
        private var INSTANCE : DailyAccountDatabase? = null
        fun getInstance(context: Context): DailyAccountDatabase {
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