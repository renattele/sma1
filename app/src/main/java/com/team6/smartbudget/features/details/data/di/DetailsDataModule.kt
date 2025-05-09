package com.team6.smartbudget.features.details.data.di

import com.team6.smartbudget.features.details.data.TrackDetailsRepositoryImpl
import com.team6.smartbudget.features.details.data.remote.RemoteTrackDetailsDataSource
import com.team6.smartbudget.features.details.data.remote.mapper.RemoteTrackDetailsMapper
import com.team6.smartbudget.features.details.data.remote.mapper.RemoteTrackDetailsMapperImpl
import com.team6.smartbudget.features.details.domain.TrackDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [DetailsDataProvidesModule::class])
interface DetailsDataModule {
    @Binds
    @Singleton
    fun bindRemoteTrackDetailsMapper(mapper: RemoteTrackDetailsMapperImpl): RemoteTrackDetailsMapper

    @Binds
    @Singleton
    fun bindTrackDetailsRepository(repository: TrackDetailsRepositoryImpl): TrackDetailsRepository
}

@Module
open class DetailsDataProvidesModule {
    @Provides
    @Singleton
    fun provideTrackDetailsDataSource(retrofit: Retrofit): RemoteTrackDetailsDataSource =
        retrofit.create()
}
