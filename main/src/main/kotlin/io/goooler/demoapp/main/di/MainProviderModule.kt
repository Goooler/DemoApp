package io.goooler.demoapp.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.common.util.RoomHelper
import io.goooler.demoapp.main.repository.MainCommonRepository

@Module
@InstallIn(SingletonComponent::class)
object MainProviderModule {

    @Provides
    fun provideMainCommonRepository(): MainCommonRepository {
        return MainCommonRepository(RetrofitHelper.create(), RoomHelper.create())
    }
}
