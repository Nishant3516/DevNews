package com.example.devnews.presentation.viewmodels.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.usecases.GetNewsFromSlugUseCase
import com.example.devnews.domain.usecases.GetNewsUseCase
import com.example.devnews.domain.usecases.ToggleLikeUseCase
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.DeepLinkBuilder
import com.example.devnews.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getNewsFromSlugUseCase: GetNewsFromSlugUseCase,
    private val deepLinkBuilder: DeepLinkBuilder
) : ViewModel() {
    private val _newsListState = MutableStateFlow<UiState<List<TaggedNews>>>(UiState.Loading)
    val newsListState: StateFlow<UiState<List<TaggedNews>>> = _newsListState


    private val _newsDetailState = MutableStateFlow<UiState<TaggedNews>>(UiState.Loading)
    val newsDetailState: StateFlow<UiState<TaggedNews>> = _newsDetailState

    fun buildDeepLink(slug: String): String = deepLinkBuilder.newsLink(slug)

    fun fetchNews(categories: List<Int>) {
        viewModelScope.launch {
            _newsListState.value = UiState.Loading
            when (val result = getNewsUseCase(categories)) {
                is ApiResult.Success -> _newsListState.value = UiState.Success(result.data)
                is ApiResult.Failure -> _newsListState.value = UiState.Failure(result.message)
            }
        }
    }

    fun getNewsFromSlug(slug: String) {
        viewModelScope.launch {
            _newsDetailState.value = UiState.Loading
            when (val result = getNewsFromSlugUseCase(slug)) {
                is ApiResult.Success -> _newsDetailState.value = UiState.Success(result.data)
                is ApiResult.Failure -> _newsDetailState.value = UiState.Failure(result.message)
            }
        }
    }

    fun toggleLike(newsId: Int) {
        viewModelScope.launch {
            when (val result = toggleLikeUseCase(newsId)) {
                is ApiResult.Success -> {
                    val currentState = _newsListState.value
                    if (currentState is UiState.Success) {
                        val updatedList = currentState.data.map { news ->
                            if (news.id == newsId) {
                                news.copy(likes = result.data.likes)
                            } else news
                        }
                        _newsListState.value = UiState.Success(updatedList)
                    }
                }

                is ApiResult.Failure -> {
                    _newsListState.value = UiState.Failure(result.message)
                }
            }
        }
    }
}