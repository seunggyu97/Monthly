package com.example.monthly.repository

import com.example.monthly.dao.UserDAO
import com.example.monthly.dataclass.User

class UserRepository(private val dao : UserDAO) {

    val subscribers = dao.getAllUsers()

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