package com.example.play_store_clone

data class AppItem(
    val id: Int,
    val title: String,
    val category: String,
    val rating: Double,
    val size: String,
    val imageUrl: String,
    val type: Int // 0 for vertical list item, 1 for horizontal card
)

data class SectionItem(
    val title: String,
    val items: List<AppItem>,
    val type: Int // 0 for Vertical List (Suggested), 1 for Horizontal List (Recommended)
)
