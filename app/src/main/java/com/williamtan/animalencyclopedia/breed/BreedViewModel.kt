package com.williamtan.animalencyclopedia.breed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.breed.GetBreeds
import com.williamtan.domain.usecase.breed.SearchBreedsByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
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
    val searchQuery = MutableStateFlow("")

    private var currentPage = 0
    private var reachedEnd = false
    private var isSearching = false

    init {
        val animalType = (savedStateHandle.get("animalType") as? AnimalType)

        if (animalType != null) {
            viewModelScope.launch {
                loadBreeds(animalType)
            }

            searchQuery
                .onEach {
                    isSearching = it.isNotBlank()
                }
                .onEach {
                    if (it.isBlank()) {
                        breedEntityData.value = emptyList()
                        loadBreeds(animalType)
                    } else {
                        searchBreedByName(animalType, it)
                    }
                }
                .debounce(500)
                .shareIn(viewModelScope, SharingStarted.Eagerly)
        } else {
            viewModelScope.launch {
                uiState.emit(ScreenState.Error("Invalid arguments"))
            }
        }
    }

    private suspend fun searchBreedByName(animalType: AnimalType, query: String) {
        currentPage = 0
        reachedEnd = false

        searchBreedsByName(animalType, query)
            .onStart { uiState.emit(ScreenState.Loading) }
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
        if (reachedEnd) return
        if (uiState.value is ScreenState.Loading) return
        if (isSearching) return

        getBreeds(animalType, currentPage)
            .onStart { uiState.emit(ScreenState.Loading) }
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }.collect { breeds ->
                if (breedEntityData.value.isEmpty() && breeds.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    reachedEnd = breeds.isEmpty()
                    uiState.emit(ScreenState.Success)
                    breedEntityData.emit(breedEntityData.value + breeds)
                    currentPage++
                }
            }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}