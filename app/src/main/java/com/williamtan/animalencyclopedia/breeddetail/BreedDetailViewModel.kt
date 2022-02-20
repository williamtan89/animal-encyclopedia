package com.williamtan.animalencyclopedia.breeddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamtan.animalencyclopedia.breeddetail.mapper.BreedDetailMapper
import com.williamtan.animalencyclopedia.breeddetail.model.BreedDetail
import com.williamtan.animalencyclopedia.cat.domain.breed.usecase.GetBreedById
import com.williamtan.animalencyclopedia.favorite.domain.usecase.AddBreedToFavorite
import com.williamtan.animalencyclopedia.favorite.domain.usecase.RemoveBreedFromFavorite
import com.williamtan.common.enumtype.AnimalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getBreedById: GetBreedById,
    private val addBreedToFavorite: AddBreedToFavorite,
    private val removeBreedFromFavorite: RemoveBreedFromFavorite,
    private val breedDetailMapper: BreedDetailMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    lateinit var animalType: AnimalType

    init {
        val breedIdArg = savedStateHandle.get("breedId") as? String
        val animalTypeArg = savedStateHandle.get("animalType") as? AnimalType

        if (breedIdArg != null && animalTypeArg != null) {
            animalType = animalTypeArg

            viewModelScope.launch {
                loadBreedDetail(animalType, breedIdArg)
            }
        } else {
            viewModelScope.launch {
                uiState.emit(ScreenState.Error("Invalid arguments"))
            }
        }
    }

    suspend fun onFavoriteButtonClick(breedDetail: BreedDetail) {
        if (breedDetail.isFavorite) {
            removeBreedFromFavorite.invoke(breedDetail.id)
        } else {
            /*addBreedToFavorite.invoke(
                BreedEntity(
                    id = breedDetail.id,
                    name = breedDetail.name,
                    imageUrl = breedDetail.imageUrl,
                    animalType = animalType,
                    temperamentList = breedDetail.temperamentList,
                    wikipediaUrl = breedDetail.wikipediaUrl,
                    energyLevel = breedDetail.energyLevel,
                    description = breedDetail.description
                )
            )*/
        }
    }

    private suspend fun loadBreedDetail(animalType: AnimalType, breedId: String) {
        getBreedById(animalType, breedId)
            .onStart { uiState.emit(ScreenState.Loading) }
            .catch {
                it.printStackTrace()
                uiState.emit(ScreenState.Error(it.stackTraceToString()))
            }
            .collect { breed ->
                if (breed == null) {
                    uiState.emit(ScreenState.Empty)
                } else {
                    uiState.emit(
                        ScreenState.Success(
                            breedDetailMapper.map(breed)
                        )
                    )
                }
            }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        object Empty : ScreenState()
        data class Error(val error: String) : ScreenState()
        data class Success(val data: BreedDetail) : ScreenState()
    }
}