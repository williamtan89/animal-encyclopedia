package com.williamtan.animalencyclopedia.breed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetBreeds
import com.williamtan.domain.usecase.SearchBreedsByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getBreeds: GetBreeds,
    private val searchBreedsByName: SearchBreedsByName,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val breedEntityData = MutableStateFlow<List<BreedEntity>>(emptyList())
    val searchQuery = MutableSharedFlow<String?>()

    private var currentPage = 0

    init {
        viewModelScope.launch {
            (savedStateHandle.get("animalType") as? AnimalType)?.let {
                loadBreeds(it)
            }
        }
    }

    suspend fun searchBreedByName(animalType: AnimalType, query: String) {
        currentPage = 0

        searchBreedsByName(animalType, query)
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }.collect { breeds ->
                if (breedEntityData.value.isEmpty() && breeds.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(ScreenState.Success)
                    breedEntityData.emit(breeds)
                }
            }
    }

    suspend fun loadBreeds(animalType: AnimalType) {
        getBreeds(animalType, currentPage).catch {
            it.printStackTrace()
            uiState.emit(ScreenState.Error(it.stackTraceToString()))
        }.collect { breeds ->
            if (breedEntityData.value.isEmpty() && breeds.isEmpty()) {
                uiState.emit(ScreenState.Empty)
            } else {
                uiState.emit(ScreenState.Success)
                breedEntityData.emit(breedEntityData.value + breeds)
                currentPage++
            }
        }
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}