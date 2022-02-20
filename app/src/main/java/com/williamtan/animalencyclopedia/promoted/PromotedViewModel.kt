package com.williamtan.animalencyclopedia.promoted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.animalencyclopedia.adapter.PromotedBreedsAdapter
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.animaltype.GetAnimalTypeList
import com.williamtan.domain.usecase.animaltype.GetAnimalTypeWithPromotedBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotedViewModel @Inject constructor(
    private val getAnimalTypeList: GetAnimalTypeList,
    private val getAnimalTypeWithPromotedBreeds: GetAnimalTypeWithPromotedBreeds
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val dataMap = MutableStateFlow<Map<AnimalType, PromotedBreeds>>(emptyMap())

    init {
        viewModelScope.launch {
            loadAnimalType()
        }
    }

    /**
     * Fetch a list of supported animal type and display them in the list. Then fetch recent
     * promoted list of breeds of the animal type and update them in the list.
     */
    suspend fun loadAnimalType() = getAnimalTypeList()
        .onStart { uiState.emit(ScreenState.Loading) }
        .catch {
            it.printStackTrace()
            uiState.emit(ScreenState.Error(it.stackTraceToString()))
        }
        .collect { animalTypeList ->
            // start displaying list of supported animal type
            if (animalTypeList.isEmpty()) {
                uiState.emit(ScreenState.Empty)
            } else {
                uiState.emit(ScreenState.Success)

                // set initial dataMap value
                dataMap.emit(
                    animalTypeList.map {
                        it to PromotedBreeds(
                            it,
                            PromotedBreedsAdapter.PromotedBreedsItemState.Loading
                        )
                    }.toMap()
                )

                // fetch promoted breeds for each supported animal type
                animalTypeList.forEach {
                    loadPromotedAnimalType(it)
                }
            }
        }

    private suspend fun loadPromotedAnimalType(animalType: AnimalType) =
        getAnimalTypeWithPromotedBreeds(animalType)
            .onStart {
                val promotedBreedWithLoadingState = dataMap.value[animalType]?.copy(
                    promotedBreedsItemState = PromotedBreedsAdapter.PromotedBreedsItemState.Loading
                ) ?: PromotedBreeds(
                    animalType,
                    PromotedBreedsAdapter.PromotedBreedsItemState.Loading
                )

                dataMap.emit(
                    dataMap.value + mapOf(
                        animalType to promotedBreedWithLoadingState
                    )
                )
            }
            .catch {
                dataMap.emit(
                    dataMap.value + mapOf(
                        animalType to PromotedBreeds(
                            animalType,
                            PromotedBreedsAdapter.PromotedBreedsItemState.Error
                        )
                    )
                )
            }
            .collect { recentBreeds ->
                // update dataMap with updated recent breeds
                dataMap.emit(
                    dataMap.value + mapOf(
                        animalType to PromotedBreeds(
                            animalType,
                            PromotedBreedsAdapter.PromotedBreedsItemState.Ready(
                                recentBreeds
                            )
                        )
                    )
                )
            }

    sealed class ScreenState {
        object Loading : ScreenState()
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}