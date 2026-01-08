package com.devhjs.getphonenumber.di

import com.devhjs.getphonenumber.data.datasource.ContactDataSource
import com.devhjs.getphonenumber.data.datasource.SystemContactDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindContactDataSource(
        systemContactDataSource: SystemContactDataSource
    ): ContactDataSource
}
