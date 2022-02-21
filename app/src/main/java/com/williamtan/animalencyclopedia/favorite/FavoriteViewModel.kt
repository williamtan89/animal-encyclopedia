package com.williamtan.animalencyclopedia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.animalencyclopedia.breed.mapper.BreedMapper
import com.williamtan.animalencyclopedia.breed.model.Breed
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedList
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedList
import com.williamtan.animalencyclopedia.favorite.domain.usecase.GetFavoriteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteList: GetFavoriteList,
    private val getCatBreedList: GetCatBreedList,
    private val getDogBreedList: GetDogBreedList,
    private val breedMapper: BreedMapper
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)

    init {
        viewModelScope.launch {
            loadFavoriteList()
        }
    }

    private suspend fun loadFavoriteList() {
        getFavoriteList()
            .onStart { uiState.emit(ScreenState.Loading) }
            .map(breedMapper::mapFavoriteBreedEntityList)
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }
            .collect { favoriteList ->
                if (favoriteList.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(ScreenState.Success(favoriteList))
                }
            }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        data class Success(val favoriteList: List<Breed>) : ScreenState()
    }
}