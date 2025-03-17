package com.flip.interview.todo.entities

import jakarta.persistence.*

@Entity
@Table(name = "todos")
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val category: String,

    @Column(nullable = true)
    val description: String?,

    @Column(nullable = false)
    val done: Boolean = false
)