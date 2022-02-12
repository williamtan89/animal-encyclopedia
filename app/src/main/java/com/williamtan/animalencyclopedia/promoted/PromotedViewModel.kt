package com.williamtan.animalencyclopedia.promoted

import androidx.lifecycle.ViewModel
import com.williamtan.common.entity.PromotedBreedsEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetAnimalTypeList
import com.williamtan.domain.usecase.GetAnimalTypeWithRecentBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class PromotedViewModel @Inject constructor(
    private val getAnimalTypeList: GetAnimalTypeList,
    private val getAnimalTypeWithRecentBreeds: GetAnimalTypeWithRecentBreeds
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val dataMap = MutableStateFlow<Map<AnimalType, PromotedBreedsEntity>>(emptyMap())

    suspend fun loadAnimalType() = getAnimalTypeList()
        .catch {
            uiState.emit(ScreenState.Error("Try again later"))
        }
        .collect { animalTypeList ->
            if (animalTypeList.isEmpty()) {
                uiState.emit(ScreenState.Empty)
            } else {
                uiState.emit(ScreenState.Success)

                // set initial dataMap value
                dataMap.emit(
                    animalTypeList.map {
                        it to PromotedBreedsEntity(it, emptyList())
                    }.toMap()
                )

                // fetch recent breeds for current animal type and update them
                animalTypeList.forEach { animalType ->
                    getAnimalTypeWithRecentBreeds(animalType).collect { recentBreeds ->
                        dataMap.tryEmit(
                            dataMap.value + mapOf(
                                animalType to PromotedBreedsEntity(
                                    animalType,
                                    recentBreeds
                                )
                            )
                        )
                    }
                }
            }
        }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}