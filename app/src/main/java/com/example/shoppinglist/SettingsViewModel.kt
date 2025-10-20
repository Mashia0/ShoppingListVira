package com.example.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val themePreferenceManager = ThemePreferenceManager(application)

    // StateFlow untuk menyimpan status dark mode yang bisa diobservasi
    val isDarkMode: StateFlow<Boolean> = themePreferenceManager.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mulai observasi saat UI aktif
            initialValue = false // Nilai awal sebelum DataStore dibaca
        )

    // Fungsi untuk mengubah tema
    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            themePreferenceManager.setDarkMode(isDarkMode)
        }
    }
}