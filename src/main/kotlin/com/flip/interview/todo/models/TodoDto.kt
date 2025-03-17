package com.flip.interview.todo.models

data class TodoDto(
    val id: Long = 0,
    val title: String,
    val category: String,
    val description: String?,
    val done: Boolean = false
)