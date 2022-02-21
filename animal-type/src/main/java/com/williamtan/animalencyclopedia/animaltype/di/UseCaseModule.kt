package com.williamtan.animalencyclopedia.animaltype.di

import com.williamtan.animalencyclopedia.animaltype.usecase.GetAnimalTypeList
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