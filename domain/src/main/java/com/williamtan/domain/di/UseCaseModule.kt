package com.williamtan.domain.di

import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.repository.FavoriteRepository
import com.williamtan.domain.usecase.animaltype.GetAnimalTypeList
import com.williamtan.domain.usecase.animaltype.GetAnimalTypeWithPromotedBreeds
import com.williamtan.domain.usecase.breed.GetBreedById
import com.williamtan.domain.usecase.breed.GetBreeds
import com.williamtan.domain.usecase.breed.SearchBreedsByName
import com.williamtan.domain.usecase.favorite.AddBreedToFavorite
import com.williamtan.domain.usecase.favorite.GetFavoriteList
import com.williamtan.domain.usecase.favorite.RemoveBreedFromFavorite
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
    fun provideGetAnimalTypeWithPromotedBreeds(
        catRepository: CatRepository
    ) = GetAnimalTypeWithPromotedBreeds(catRepository)

    @Provides
    @Singleton
    fun provideSearchBreedsByName(
        catRepository: CatRepository
    ) = SearchBreedsByName(catRepository)

    @Provides
    @Singleton
    fun provideGetAnimalTypeList() = GetAnimalTypeList()

    @Provides
    @Singleton
    fun provideGetBreeds(
        catRepository: CatRepository
    ) = GetBreeds(catRepository)

    @Provides
    @Singleton
    fun provideGetBreedById(
        catRepository: CatRepository,
        favoriteRepository: FavoriteRepository
    ) = GetBreedById(catRepository, favoriteRepository)

    @Provides
    @Singleton
    fun provideGetFavoriteList(
        repository: FavoriteRepository
    ) = GetFavoriteList(repository)

    @Provides
    @Singleton
    fun provideAddBreedToFavorite(
        repository: FavoriteRepository
    ) = AddBreedToFavorite(repository)

    @Provides
    @Singleton
    fun provideRemoveBreedFromFavorite(
        repository: FavoriteRepository
    ) = RemoveBreedFromFavorite(repository)
}