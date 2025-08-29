package com.example.devnews.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.devnews.data.remote.dto.StoredCategoryDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CategoryLocalStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val SELECTED_CATEGORIES = stringSetPreferencesKey("selected_categories")
    }

    suspend fun saveSelectedCategories(categories: List<StoredCategoryDTO>) {
        dataStore.edit { prefs ->
            prefs[SELECTED_CATEGORIES] = categories.map { "${it.id}:${it.name}" }.toSet()
        }
    }

    fun getSavedCategories(): Flow<List<StoredCategoryDTO>> {
        return dataStore.data.map { prefs ->
            prefs[SELECTED_CATEGORIES]?.mapNotNull { entry ->
                val parts = entry.split(":")
                if (parts.size == 2) {
                    StoredCategoryDTO(id = parts[0].toInt(), name = parts[1])
                } else null
            } ?: emptyList()
        }
    }
}