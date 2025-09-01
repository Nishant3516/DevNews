package com.example.devnews.presentation.viewmodels.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.usecases.GetNewsUseCase
import com.example.devnews.domain.usecases.ToggleLikeUseCase
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase, private val toggleLikeUseCase: ToggleLikeUseCase,
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

    fun toggleLike(newsId: Int) {
        viewModelScope.launch {
            when (val result = toggleLikeUseCase(newsId)) {
                is ApiResult.Success -> {
                    val currentState = _uiState.value
                    if (currentState is UiState.Success) {
                        val updatedList = currentState.data.map { news ->
                            if (news.id == newsId) {
                                news.copy(likes = result.data.likes)
                            } else news
                        }
                        _uiState.value = UiState.Success(updatedList)
                    }
                }

                is ApiResult.Failure -> {
                    _uiState.value = UiState.Failure(result.message)
                }
            }
        }
    }
}