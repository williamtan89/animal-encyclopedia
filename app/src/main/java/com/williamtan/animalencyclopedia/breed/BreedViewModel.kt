package com.williamtan.animalencyclopedia.breed

import androidx.lifecycle.ViewModel
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getBreeds: GetBreeds
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val dataMap = MutableStateFlow<List<BreedEntity>>(emptyList())

    private var currentPage = 0

    suspend fun loadBreeds(animalType: AnimalType) = getBreeds(animalType, currentPage)
        .catch {
            uiState.emit(ScreenState.Error("Try again later"))
        }
        .collect { breeds ->
            if (breeds.isEmpty()) {
                uiState.emit(ScreenState.Empty)
            } else {
                uiState.emit(ScreenState.Success)
                dataMap.emit(dataMap.value + breeds)
            }
        }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}