package com.example.themoviedbapp.di.modules

import com.example.themoviedbapp.base.repository.GeneralDataSource
import com.example.themoviedbapp.base.repository.GeneralRepository
import com.example.themoviedbapp.di.auth.AuthApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeneralRepositoryModule {

    @Singleton
    @Provides
    fun provideGeneralDataSource(authApi: AuthApi): GeneralDataSource {
        return GeneralRepository(authApi)
    }

}