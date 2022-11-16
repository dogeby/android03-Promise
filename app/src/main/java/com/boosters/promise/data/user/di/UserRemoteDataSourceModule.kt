package com.boosters.promise.data.user.di

import com.boosters.promise.data.user.source.remote.UserRemoteDataSourceImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRemoteDataSourceModule {

    private const val USERS_PATH = "users"

    @Singleton
    @Provides
    fun provideUserRemoteDataSource() =
        UserRemoteDataSourceImpl(Firebase.firestore.collection(USERS_PATH))

}