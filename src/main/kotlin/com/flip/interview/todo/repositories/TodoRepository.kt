package com.flip.interview.todo.repositories

import com.flip.interview.todo.entities.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByCategory(category: String): List<Todo>
}