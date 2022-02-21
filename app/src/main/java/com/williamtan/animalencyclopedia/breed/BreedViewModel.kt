package com.williamtan.animalencyclopedia.breed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.animalencyclopedia.breed.mapper.BreedMapper
import com.williamtan.animalencyclopedia.breed.model.Breed
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedByName
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedList
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedByName
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedList
import com.williamtan.common.enumtype.AnimalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getCatBreedByName: GetCatBreedByName,
    private val getCatBreedList: GetCatBreedList,
    private val getDogBreedByName: GetDogBreedByName,
    private val getDogBreedList: GetDogBreedList,
    private val breedMapper: BreedMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val breedData = MutableStateFlow<List<Breed>>(emptyList())
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
                        breedData.value = emptyList()
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

        when (animalType) {
            AnimalType.Cat -> getCatBreedByName(query)
            AnimalType.Dog -> getDogBreedByName(query)
        }
            .onStart { uiState.emit(ScreenState.Loading) }
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }
            .map(breedMapper::map)
            .collect { breeds ->
                if (breedData.value.isEmpty() && breeds.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(ScreenState.Success)
                    breedData.emit(breeds)
                }
            }
    }

    suspend fun loadBreeds(animalType: AnimalType) {
        if (reachedEnd) return
        if (uiState.value is ScreenState.Loading) return
        if (isSearching) return

        when (animalType) {
            AnimalType.Cat -> getCatBreedList(null, 10, currentPage)
            AnimalType.Dog -> getDogBreedList(null, 10, currentPage)
        }
            .onStart { uiState.emit(ScreenState.Loading) }
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }
            .map(breedMapper::map)
            .collect { breeds ->
                if (breedData.value.isEmpty() && breeds.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    reachedEnd = breeds.isEmpty()
                    uiState.emit(ScreenState.Success)
                    breedData.emit(breedData.value + breeds)
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