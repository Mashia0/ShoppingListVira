package com.example.shoppinglist

import android.app.Application // Import Application untuk preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Import viewModel untuk preview jika diperlukan
import com.example.shoppinglist.ui.theme.ShoppingListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    shoppingViewModel: ShoppingViewModel,
    settingsViewModel: SettingsViewModel // Terima SettingsViewModel
) {
    var showClearDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) } // State baru untuk dialog About

    // Observasi state dark mode dari SettingsViewModel
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Preferensi",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // ListItem untuk Mode Gelap
        ListItem(
            headlineContent = { Text("Mode Gelap") },
            supportingContent = { Text("Aktifkan tema gelap") },
            trailingContent = {
                Switch(
                    checked = isDarkMode, // Gunakan state dari ViewModel
                    onCheckedChange = { newValue ->
                        settingsViewModel.setDarkMode(newValue) // Panggil fungsi ViewModel
                    }
                )
            }
        )

        // ListItem untuk Tentang Aplikasi (dibuat clickable)
        ListItem(
            modifier = Modifier.clickable { showAboutDialog = true }, // Tambahkan clickable
            headlineContent = { Text("Tentang Aplikasi") },
            leadingContent = {
                Icon(Icons.Default.Info, contentDescription = "Tentang")
            }
        )

        HorizontalDivider()

        Text(
            text = "Data",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Tombol Hapus Semua Item
        Button(
            onClick = { showClearDialog = true }, // Tampilkan dialog konfirmasi
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth() // Pastikan ada kurung tutup di sini
        ) { // Kurung kurawal buka untuk konten Button
            Icon(Icons.Default.Delete, contentDescription = "Hapus")
            Spacer(modifier = Modifier.width(8.dp)) // Tambahkan jarak antara ikon dan teks
            Text("Hapus Semua Item Belanjaan")
        } // Kurung kurawal tutup untuk konten Button
    }

    // Dialog konfirmasi hapus data
    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus semua item? Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        shoppingViewModel.clearAllItems() // Panggil fungsi ViewModel
                        showClearDialog = false
                    }
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // Dialog untuk Tentang Aplikasi
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("Tentang ShoppingList") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Aplikasi sederhana untuk mencatat daftar belanjaan.")
                    Text("Dibuat dengan Jetpack Compose.")
                    Text("Oleh: Mashia Zavira Septyana") // Ganti dengan nama Anda
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}


