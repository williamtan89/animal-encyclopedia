package com.williamtan.animalencyclopedia.cat.domain.di

import com.williamtan.animalencyclopedia.cat.domain.animaltype.usecase.GetAnimalTypeList
import com.williamtan.animalencyclopedia.cat.domain.animaltype.usecase.GetAnimalTypeWithPromotedBreeds
import com.williamtan.animalencyclopedia.cat.domain.breed.usecase.GetBreedById
import com.williamtan.animalencyclopedia.cat.domain.breed.usecase.GetBreeds
import com.williamtan.animalencyclopedia.cat.domain.breed.usecase.SearchBreedsByName
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedById
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedByName
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedList
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedById
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedByName
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedList
import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
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
        getCatBreedList: GetCatBreedList,
        getDogBreedList: GetDogBreedList
    ) = GetAnimalTypeWithPromotedBreeds(getCatBreedList, getDogBreedList)

    @Provides
    @Singleton
    fun provideSearchBreedsByName(
        getCatBreedByName: GetCatBreedByName,
        getDogBreedByName: GetDogBreedByName
    ) = SearchBreedsByName(getCatBreedByName, getDogBreedByName)

    @Provides
    @Singleton
    fun provideGetAnimalTypeList() = GetAnimalTypeList()

    @Provides
    @Singleton
    fun provideGetBreeds(
        getCatBreedList: GetCatBreedList,
        getDogBreedList: GetDogBreedList
    ) = GetBreeds(getCatBreedList, getDogBreedList)

    @Provides
    @Singleton
    fun provideGetBreedById(
        getCatBreedById: GetCatBreedById,
        getDogBreedById: GetDogBreedById,
        favoriteRepository: FavoriteRepository
    ) = GetBreedById(getCatBreedById, getDogBreedById, favoriteRepository)
}