package com.example.devnews.presentation.viewmodels.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devnews.data.remote.dto.StoredCategoryDTO
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.usecases.GetNewsUseCase
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<TaggedNews>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<TaggedNews>>> = _uiState

    fun fetchNews(categories: List<Int>) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = getNewsUseCase(categories)) {
                is ApiResult.Success -> _uiState.value = UiState.Success(result.data)
                is ApiResult.Failure -> _uiState.value = UiState.Failure(result.message)
            }
        }
    }
}