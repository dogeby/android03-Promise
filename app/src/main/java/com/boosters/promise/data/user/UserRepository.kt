package com.boosters.promise.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun requestSignUp(userName: String): Result<String>

    fun getUser(userCode: String): Flow<User>

}