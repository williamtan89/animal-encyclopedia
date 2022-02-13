package com.williamtan.animalencyclopedia.breeddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetBreedById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getBreedById: GetBreedById,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)

    init {
        viewModelScope.launch {
            val breedId = savedStateHandle.get("breedId") as? String
            val animalType = savedStateHandle.get("animalType") as? AnimalType

            if (breedId != null && animalType != null) {
                loadBreedDetail(animalType, breedId)
            }
        }
    }

    private suspend fun loadBreedDetail(animalType: AnimalType, breedId: String) {
        getBreedById(animalType, breedId)
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error("Try again later"))
            }.collect { breed ->
                if (breed == null) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(ScreenState.Success(breed))
                }
            }
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        data class Success(val breed: BreedEntity) : ScreenState()
    }
}