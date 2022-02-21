package com.williamtan.animalencyclopedia.cat.domain.di

import com.williamtan.animalencyclopedia.cat.domain.animaltype.usecase.GetAnimalTypeList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetAnimalTypeList() = GetAnimalTypeList()
}