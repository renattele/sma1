package com.team6.smartbudget.features.overview.data.di

import com.team6.smartbudget.features.overview.data.TopTrackRepositoryImpl
import com.team6.smartbudget.features.overview.data.remote.RemoteTopTracksDataSource
import com.team6.smartbudget.features.overview.data.remote.mapper.RemoteTrackResponseMapper
import com.team6.smartbudget.features.overview.data.remote.mapper.RemoteTrackResponseMapperImpl
import com.team6.smartbudget.features.overview.domain.TopTracksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [OverviewDataProvidesModule::class])
interface OverviewDataModule {
    @Binds
    @Singleton
    fun bindRemoteTrackResponseMapper(
        mapper: RemoteTrackResponseMapperImpl,
    ): RemoteTrackResponseMapper

    @Binds
    @Singleton
    fun bindTopTrackRepository(repository: TopTrackRepositoryImpl): TopTracksRepository
}

@Module
open class OverviewDataProvidesModule {
    @Provides
    @Singleton
    fun provideRemoteTopTracksDataSource(retrofit: Retrofit) =
        retrofit.create<RemoteTopTracksDataSource>()
}
