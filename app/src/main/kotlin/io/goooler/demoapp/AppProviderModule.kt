package io.goooler.demoapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.goooler.demoapp.common.router.RouterManager

@Module(includes = [RouterManager::class])
@InstallIn(SingletonComponent::class)
object AppProviderModule {

  @Provides
  fun provideRouterManagerImpl(): RouterManager = RouterManagerImpl
}
