package com.goodwy.commons.models

data class Note(
    val text: String,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
