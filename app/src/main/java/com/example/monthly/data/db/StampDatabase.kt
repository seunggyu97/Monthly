package com.example.monthly.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.monthly.data.dao.StampDAO
import com.example.monthly.data.dataclass.Stamp

@Database(entities = [Stamp::class], version = 1)
@TypeConverters( Converters::class)
abstract class StampDatabase : RoomDatabase() {

    abstract val stampDAO : StampDAO

    companion object{
        @Volatile
        private var INSTANCE : StampDatabase? = null
        fun getInstance(context: Context): StampDatabase {
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