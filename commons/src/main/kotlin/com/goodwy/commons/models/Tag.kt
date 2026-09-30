package com.goodwy.commons.models

data class Tag(
    val id: Long,
    val name: String,
    val color: Int,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
