package com.williamtan.animalencyclopedia.favorite.domain.di

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.animalencyclopedia.favorite.domain.usecase.AddBreedToFavorite
import com.williamtan.animalencyclopedia.favorite.domain.usecase.GetFavoriteList
import com.williamtan.animalencyclopedia.favorite.domain.usecase.RemoveBreedFromFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FavoriteUseCaseModule {
    @Provides
    fun provideGetFavoriteList(
        repository: FavoriteRepository
    ) = GetFavoriteList(repository)

    @Provides
    fun provideAddBreedToFavorite(
        repository: FavoriteRepository
    ) = AddBreedToFavorite(repository)

    @Provides
    fun provideRemoveBreedFromFavorite(
        repository: FavoriteRepository
    ) = RemoveBreedFromFavorite(repository)
}