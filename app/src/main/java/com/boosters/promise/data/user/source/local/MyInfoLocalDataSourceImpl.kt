package com.boosters.promise.data.user.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.boosters.promise.data.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MyInfoLocalDataSourceImpl(
    private val myInfoPreferencesDataStore: DataStore<Preferences>
) : MyInfoLocalDataSource {

    override suspend fun saveMyInfo(user: User) {
        myInfoPreferencesDataStore.edit { preferences ->
            preferences[USER_CODE_KEY] = user.userCode
            preferences[USER_NAME_KEY] = user.userName
        }
    }

    override suspend fun getMyInfo(): Flow<Result<User>> =
        myInfoPreferencesDataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
        }.map { preferences ->
            runCatching {
                val userCode = preferences[USER_CODE_KEY] ?: throw NullPointerException()
                val userName = preferences[USER_NAME_KEY] ?: throw NullPointerException()
                User(userCode, userName)
            }
        }

    companion object {
        private val USER_CODE_KEY = stringPreferencesKey("userCode")
        private val USER_NAME_KEY = stringPreferencesKey("userName")
    }

}