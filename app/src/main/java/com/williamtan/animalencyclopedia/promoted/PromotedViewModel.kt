package com.williamtan.animalencyclopedia.promoted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.common.entity.PromotedBreedsEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetAnimalTypeList
import com.williamtan.domain.usecase.GetAnimalTypeWithPromotedBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotedViewModel @Inject constructor(
    private val getAnimalTypeList: GetAnimalTypeList,
    private val getAnimalTypeWithPromotedBreeds: GetAnimalTypeWithPromotedBreeds
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val dataMap = MutableStateFlow<Map<AnimalType, PromotedBreedsEntity>>(emptyMap())

    init {
        viewModelScope.launch {
            loadAnimalType()
        }
    }

    private suspend fun loadAnimalType() = getAnimalTypeList()
        .catch {
            it.printStackTrace()
            uiState.emit(ScreenState.Error(it.stackTraceToString()))
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
                    getAnimalTypeWithPromotedBreeds(animalType).collect { recentBreeds ->
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