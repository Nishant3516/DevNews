package com.example.devnews.presentation.viewmodels.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devnews.data.remote.dto.StoredCategoryDTO
import com.example.devnews.domain.entities.Category
import com.example.devnews.domain.usecases.GetCategoryUseCase
import com.example.devnews.storage.CategoryLocalStorage
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val categoryLocalStorage: CategoryLocalStorage
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Category>>> = _uiState

    private val _selectedCategories = MutableStateFlow<List<StoredCategoryDTO>>(emptyList())
    val selectedCategories: StateFlow<List<StoredCategoryDTO>> = _selectedCategories

    fun fetchCategories() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = withContext(Dispatchers.IO) { getCategoryUseCase() }
            when (result) {
                is ApiResult.Success -> _uiState.value = UiState.Success(result.data)
                is ApiResult.Failure -> _uiState.value = UiState.Failure(result.message)
            }
        }
    }

    fun toggleCategorySelection(category: Category) {
        viewModelScope.launch {
            // Convert stored categories back to Category list for saving
            val current = _selectedCategories.value.toMutableList()
            if (current.any { it.id == category.id }) {
                current.removeAll { it.id == category.id }
            } else {
                current.add(StoredCategoryDTO(category.id, category.name))
            }

            _selectedCategories.value = current
        }
    }

    fun saveSelectedCategories() {
        viewModelScope.launch {
            val current = _selectedCategories.value
            categoryLocalStorage.saveSelectedCategories(current)
            println("Saved categories: $current")
        }
    }


    fun loadSavedCategories() {
        viewModelScope.launch {
            categoryLocalStorage.getSavedCategories().collect { stored ->
                _selectedCategories.value = stored
            }
        }
    }
}