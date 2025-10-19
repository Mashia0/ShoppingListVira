// app/src/main/java/com/example/shoppinglist/components/ShoppingList.kt

package com.example.shoppinglist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ShoppingItem // Import data class baru
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.delay

@Composable
fun ShoppingList(items: List<ShoppingItem>, onItemClick: (String) -> Unit) { // Terima List<ShoppingItem> dan ID (String)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { item -> // Key sekarang adalah item.id
            AnimatedShoppingListItem(item = item, onItemClick = onItemClick) // Kirim seluruh item
        }
    }
}

@Composable // <-- TAMBAHAN YANG HILANG
fun AnimatedShoppingListItem(item: ShoppingItem, onItemClick: (String) -> Unit) { // Terima ShoppingItem
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(item.id) { // Gunakan item.id sebagai key
        delay(100)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) +
                expandVertically(animationSpec = tween(600))
    ) {
        ShoppingListItem(item = item, onItemClick = onItemClick) // Kirim seluruh item
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onItemClick: (String) -> Unit) { // Terima ShoppingItem
    var isSelected by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        animationSpec = tween(300),
        label = "backgroundColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(300),
        label = "contentColor"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 4.dp,
        animationSpec = tween(200),
        label = "elevation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clickable { onItemClick(item.id) }, // Kirim item.id saat diklik
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(50))
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.name.firstOrNull()?.uppercase() ?: "?", // Gunakan item.name
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name, // Tampilkan nama
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor
                )
                if (item.details.isNotBlank()) {
                    Text(
                        text = item.details, // Tampilkan detail
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor.copy(alpha = 0.7f)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { isSelected = !isSelected }, // Toggle status selected
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = if (isSelected) "Selected" else "Add to cart",
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    },
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
} // <-- KURUNG KURAWAL YANG HILANG KEMUNGKINAN DI SINI

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ShoppingListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val dummyItems = listOf(
                ShoppingItem(name = "Susu Segar", details = "1 Liter"),
                ShoppingItem(name = "Roti Tawar", details = " "),
                ShoppingItem(name = "Telur Ayam", details = "1 pack (10 buah)")
            )
            ShoppingList(items = dummyItems, onItemClick = {})
        }
    }
}