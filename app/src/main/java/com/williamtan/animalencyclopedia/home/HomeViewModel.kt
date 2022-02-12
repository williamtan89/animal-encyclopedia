package com.williamtan.animalencyclopedia.home

import androidx.lifecycle.ViewModel
import com.williamtan.common.entity.AnimalTypeWithPromotedBreedsEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetAnimalTypeList
import com.williamtan.domain.usecase.GetAnimalTypeWithRecentBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimalTypeList: GetAnimalTypeList,
    private val getAnimalTypeWithRecentBreeds: GetAnimalTypeWithRecentBreeds
) : ViewModel() {
    val uiState = MutableStateFlow<HomeState>(HomeState.Empty)
    val dataMap = MutableStateFlow<Map<AnimalType, AnimalTypeWithPromotedBreedsEntity>>(emptyMap())

    suspend fun loadAnimalType() = getAnimalTypeList()
        .catch {
            uiState.emit(HomeState.Error("Try again later"))
        }
        .collect { animalTypeList ->
            if (animalTypeList.isEmpty()) {
                uiState.emit(HomeState.Empty)
            } else {
                uiState.emit(HomeState.Success)

                // set initial dataMap value
                dataMap.emit(
                    animalTypeList.map {
                        it to AnimalTypeWithPromotedBreedsEntity(it, emptyList())
                    }.toMap()
                )

                // fetch recent breeds for current animal type and update them
                animalTypeList.forEach { animalType ->
                    getAnimalTypeWithRecentBreeds(animalType).collect { recentBreeds ->
                        dataMap.tryEmit(
                            dataMap.value + mapOf(
                                animalType to AnimalTypeWithPromotedBreedsEntity(
                                    animalType,
                                    recentBreeds
                                )
                            )
                        )
                    }
                }
            }
        }

    sealed class HomeState {
        object Empty : HomeState()
        data class Error(val error: String) : HomeState()
        object Success : HomeState()
    }
}