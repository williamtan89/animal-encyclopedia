package com.williamtan.animalencyclopedia.breed

import androidx.lifecycle.ViewModel
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.usecase.GetBreeds
import com.williamtan.domain.usecase.SearchBreedsByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getBreeds: GetBreeds,
    private val searchBreedsByName: SearchBreedsByName
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val breedEntityData = MutableStateFlow<List<BreedEntity>>(emptyList())
    val searchQuery = MutableStateFlow<String?>(null)

    private var currentPage = 0

    suspend fun updateSearchQuery(query: String?) {
        searchQuery.emit(query?.trim())
        breedEntityData.emit(emptyList())
        currentPage = 0
    }

    suspend fun loadBreeds(animalType: AnimalType) {
        val query = searchQuery.value

        if (!query.isNullOrBlank()) {
            searchBreedsByName(animalType, query)
        } else {
            getBreeds(animalType, currentPage)
        }.catch {
            it.printStackTrace()
            uiState.emit(ScreenState.Error("Try again later"))
        }.collect { breeds ->
            if (breedEntityData.value.isEmpty() && breeds.isEmpty()) {
                uiState.emit(ScreenState.Empty)
            } else {
                val newEntityData = if (!query.isNullOrBlank()) {
                    breeds
                } else {
                    currentPage++
                    breedEntityData.value + breeds
                }

                uiState.emit(ScreenState.Success)
                breedEntityData.emit(newEntityData)
            }
        }
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        object Success : ScreenState()
    }
}