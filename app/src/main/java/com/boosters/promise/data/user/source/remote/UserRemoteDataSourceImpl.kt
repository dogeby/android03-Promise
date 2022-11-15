package com.boosters.promise.data.user.source.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
    private val userDatabaseReference: DatabaseReference
) : UserRemoteDataSource {

    override suspend fun requestSignUp(userName: String): Result<String> = runCatching {
        userDatabaseReference.push().run {
            val task = setValue(UserBody(key, userName, null))
            task.await()

            key ?: throw task.exception ?: NullPointerException()
        }
    }

    override fun getUserFlow(userCode: String): Flow<UserBody> =
        userDatabaseReference.child(userCode).snapshots.mapNotNull {
            it.getValue(UserBody::class.java)
        }

}