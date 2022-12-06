package com.example.monthly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monthly.data.dataclass.User


@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User) : Long

    @Update
    suspend fun updateUser(user: User) : Int

    @Delete
    suspend fun deleteUser(user: User) : Int

    @Query("DELETE FROM user_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM user_data_table")
    fun getAllUsers(): LiveData<List<User>>

//    @Query("SELECT * FROM member where id >= 10 ")

}