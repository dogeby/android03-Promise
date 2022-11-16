package com.boosters.promise.data.user.source.remote

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRemoteDataSourceImplTest {

    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)
        userRemoteDataSource = UserRemoteDataSourceImpl(Firebase.database.getReference(USERS_PATH))
    }

    @Test
    fun requestSignUp_SignUpSuccess() = runBlocking {
        val userName = "yang"

        val userCode = userRemoteDataSource.requestSignUp(userName).getOrNull()
        val userFlow = userRemoteDataSource.getUserFlow(userCode ?: "")

        Assert.assertNotNull(userCode)
        Assert.assertEquals(userName, userFlow.first().userName)
    }

    companion object {
        private const val USERS_PATH = "users"
    }

}