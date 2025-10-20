package com.example.shoppinglist

import java.util.UUID

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val brand: String,
    val size: String,
    val details: String
)