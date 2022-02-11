package io.goooler.demoapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.goooler.demoapp.common.router.IRouterManager

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {

  @Provides
  fun provideRouterManagerImpl(): IRouterManager = RouterManagerImpl
}
