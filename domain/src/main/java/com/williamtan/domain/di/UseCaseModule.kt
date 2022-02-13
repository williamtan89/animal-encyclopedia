package com.williamtan.domain.di

import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.repository.FavoriteRepository
import com.williamtan.domain.usecase.GetAnimalTypeList
import com.williamtan.domain.usecase.GetAnimalTypeListImpl
import com.williamtan.domain.usecase.GetAnimalTypeWithPromotedBreeds
import com.williamtan.domain.usecase.GetAnimalTypeWithPromotedBreedsImpl
import com.williamtan.domain.usecase.GetBreedById
import com.williamtan.domain.usecase.GetBreedByIdImpl
import com.williamtan.domain.usecase.GetBreeds
import com.williamtan.domain.usecase.GetBreedsImpl
import com.williamtan.domain.usecase.SearchBreedsByName
import com.williamtan.domain.usecase.SearchBreedsByNameImpl
import com.williamtan.domain.usecase.favorite.AddBreedToFavorite
import com.williamtan.domain.usecase.favorite.AddBreedToFavoriteImpl
import com.williamtan.domain.usecase.favorite.GetFavoriteList
import com.williamtan.domain.usecase.favorite.GetFavoriteListImpl
import com.williamtan.domain.usecase.favorite.RemoveBreedFromFavorite
import com.williamtan.domain.usecase.favorite.RemoveBreedFromFavoriteImpl
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
    ): GetAnimalTypeWithPromotedBreeds = GetAnimalTypeWithPromotedBreedsImpl(catRepository)

    @Provides
    @Singleton
    fun provideSearchBreedsByName(
        catRepository: CatRepository
    ): SearchBreedsByName = SearchBreedsByNameImpl(catRepository)

    @Provides
    @Singleton
    fun provideGetAnimalTypeList(): GetAnimalTypeList = GetAnimalTypeListImpl()

    @Provides
    @Singleton
    fun provideGetBreeds(
        catRepository: CatRepository
    ): GetBreeds = GetBreedsImpl(catRepository)

    @Provides
    @Singleton
    fun provideGetBreedById(
        catRepository: CatRepository,
        favoriteRepository: FavoriteRepository
    ): GetBreedById = GetBreedByIdImpl(catRepository, favoriteRepository)

    @Provides
    @Singleton
    fun provideGetFavoriteList(
        repository: FavoriteRepository
    ): GetFavoriteList = GetFavoriteListImpl(repository)

    @Provides
    @Singleton
    fun provideAddBreedToFavorite(
        repository: FavoriteRepository
    ): AddBreedToFavorite = AddBreedToFavoriteImpl(repository)

    @Provides
    @Singleton
    fun provideRemoveBreedFromFavorite(
        repository: FavoriteRepository
    ): RemoveBreedFromFavorite = RemoveBreedFromFavoriteImpl(repository)
}