package com.example.footballstandings.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballstandings.core.FsRepository
import com.example.footballstandings.core.models.Competition
import com.example.footballstandings.core.models.League
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FsViewModel @Inject constructor(
    private val fsRepository: FsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FsUiState>(FsUiState.Loading)
    val uiState: StateFlow<FsUiState> = _uiState.asStateFlow()

    init {
        getCompetitions()
    }

    private fun getCompetitions() {
        viewModelScope.launch {
            try {
                val leagues: List<Competition> = fsRepository.getCompetitions()
                _uiState.update {
                    FsUiState.Success(leagues)
                }
            } catch (e: Exception) {
                _uiState.update {
                    FsUiState.Error(e.localizedMessage)
                }
            }
        }
    }
}

sealed interface FsUiState {
    object Loading: FsUiState
    data class Success(
        val leagues: List<Competition>
    ): FsUiState
    data class Error(
        val message: String
    ): FsUiState
}
