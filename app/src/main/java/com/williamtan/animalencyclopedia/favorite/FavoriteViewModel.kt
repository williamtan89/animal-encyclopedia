package com.williamtan.animalencyclopedia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.usecase.favorite.GetFavoriteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteList: GetFavoriteList
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)

    init {
        viewModelScope.launch {
            loadFavoriteList()
        }
    }

    private suspend fun loadFavoriteList() {
        getFavoriteList()
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error("Try again later"))
            }.collect { favoriteList ->
                if (favoriteList.isEmpty()) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(ScreenState.Success(favoriteList))
                }
            }
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        data class Success(val favoriteList: List<BreedEntity>) : ScreenState()
    }
}