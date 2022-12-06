package com.example.monthly.data.repository

import androidx.lifecycle.LiveData
import com.example.monthly.data.dao.UserDAO
import com.example.monthly.data.dataclass.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao : UserDAO) {

    val users = dao.getAllUsers()
    val getUserData : LiveData<List<User>> = dao.getAllUsers()

    suspend fun insert(user: User): Long{
        return dao.insertUser(user)
    }

    suspend fun update(user: User): Int{
        return dao.updateUser(user)
    }

    suspend fun delete(user: User): Int{
        return dao.deleteUser(user)
    }

    suspend fun deleteAll(): Int{
        return dao.deleteAll()
    }
}